package com.example.apppedidos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.widget.Toast;

import com.example.apppedidos.model.ItemPedido;
import com.example.apppedidos.model.Pedido;
import com.example.apppedidos.model.Produto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PedidoBd
{
    private Context context;
    private SQLiteDatabase db;
    private final SQLiteHelper dbHelper;

    public PedidoBd(Context context) {
        dbHelper = new SQLiteHelper(context);
        this.context = context;
    }

    public void salvarPedido(Pedido pedido)
    {
        ContentValues values = new ContentValues();
        values.put("nome_comprador", pedido.getNomeComprador());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            values.put("data_compra", String.valueOf(LocalDate.now()));
        }

        db = dbHelper.getWritableDatabase();
        long idPedido = db.insert("pedido", null, values);

        if( idPedido == -1) {
            Toast.makeText(context,
                    "Ocorreu um erro ao salvar o Pedido.", Toast.LENGTH_SHORT).show();
            db.close();
            return;
        }

        for (ItemPedido item : pedido.getPedidos()) {
            ContentValues valuesItem = new ContentValues();
            valuesItem.put("id_pedido", idPedido);
            valuesItem.put("id_produto", item.getProduto().getId());
            valuesItem.put("quantidade", item.getProduto().getQuantidade());
            valuesItem.put("valor_unitario", item.getProduto().getValor());

            long idItem = db.insert("item_pedido", null, valuesItem);

            if( idItem == -1) {
                Toast.makeText(context,"Ocorreu um erro ao salvar os Itens do Pedido.", Toast.LENGTH_SHORT).show();
                db.close();
                return;
            }
        }

        Toast.makeText(context,"Pedido finalizado com sucesso.", Toast.LENGTH_SHORT).show();
        db.close();
    }

    public Cursor listaPedidos() {
        String query = "SELECT * FROM pedido";
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public ArrayList<ItemPedido> listaItensPedido(long idPedido) {
        String query = "SELECT * FROM item_pedido WHERE id_pedido="+idPedido;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ArrayList<ItemPedido> itens = new ArrayList<>();
        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);

            while (cursor.moveToNext())
            {
                ItemPedido item = new ItemPedido();
                Cursor c = db.rawQuery("SELECT * FROM produto WHERE id="+cursor.getInt(2), null);
                Produto produto = new Produto();
                while (c.moveToNext())
                {
                    produto.setId(c.getLong(0));
                    produto.setNome(c.getString(1));
                    produto.setDescricao(c.getString(2));
                    produto.setValor(c.getDouble(3));
                }
                item.setProduto(produto);
                item.setQuantidade(cursor.getInt(3));
                item.setValorUnitario(cursor.getDouble(4));

                itens.add(item);
            }
            db.close();
        }
        return itens;
    }
}
