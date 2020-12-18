package com.example.apppedidos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.apppedidos.ProdutoActivity;
import com.example.apppedidos.model.Produto;

import java.util.ArrayList;

public class ProdutoBd
{
    private Context context;
    private SQLiteDatabase db;
    private final SQLiteHelper dbHelper;

    public ProdutoBd(Context context) {
        dbHelper = new SQLiteHelper(context);
        this.context = context;
    }

    public void salvarProduto(Produto produto)
    {
        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nome", produto.getNome());
        values.put("descricao", produto.getDescricao());
        values.put("valor", produto.getValor());

        long idProduto = db.insert("produto", null, values);

        if( idProduto == -1) {
            Toast.makeText(context,"Ocorreu um erro ao salvar o Produto.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,"Produto salvo com sucesso.", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void alterarProduto(Produto produto)
    {
        ContentValues values = new ContentValues();
        values.put("nome", produto.getNome());
        values.put("descricao", produto.getDescricao());
        values.put("valor", produto.getValor());

        String[] args = {produto.getId().toString()};
        dbHelper.getWritableDatabase().update("produto", values, "id=?", args);
        dbHelper.close();
    }

    public void deletarProduto(Produto produto)
    {
        String[] args = {produto.getId().toString()};
        dbHelper.getWritableDatabase().delete("produto", "id=?", args);
        dbHelper.close();
    }

    public ArrayList<Produto> getLista()
    {
        String[] columns = {"id", "nome", "descricao", "valor"};
        Cursor cursor = dbHelper.getWritableDatabase().query("produto", columns, null, null, null, null, null);

        ArrayList<Produto> produtos = new ArrayList<Produto>();
        while (cursor.moveToNext())
        {
            Produto produto = new Produto();
            produto.setId(cursor.getLong(0));
            produto.setNome(cursor.getString(1));
            produto.setDescricao(cursor.getString(2));
            produto.setValor(cursor.getDouble(3));

            produtos.add(produto);
        }

        dbHelper.close();
        return produtos;
    }
}
