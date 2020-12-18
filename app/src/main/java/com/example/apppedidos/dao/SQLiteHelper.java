package com.example.apppedidos.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper
{
    private static final String DATABASE = "pedidobd";
    private static final int VERSION = 2;


    public SQLiteHelper(Context context)
    {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String produto = "CREATE TABLE produto (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "nome TEXT NOT NULL, " +
                "descricao TEXT, " +
                "valor REAL NOT NULL);";

        db.execSQL(produto);

        String pedido = "CREATE TABLE pedido (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "nome_comprador TEXT NOT NULL, " +
                "data_compra DATE NOT NULL);";
        db.execSQL(pedido);

        String itemPedido = "CREATE TABLE item_pedido (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "id_pedido int NOT NULL, " +
                "id_produto int NOT NULL, " +
                "quantidade int NOT NULL, " +
                "valor_unitario Real NOT NULL);";
        db.execSQL(itemPedido);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String produto = "DROP TABLE IF EXISTS produto;";
        db.execSQL(produto);

        String pedido = "DROP TABLE IF EXISTS pedido;";
        db.execSQL(pedido);

        String itemPedido = "DROP TABLE IF EXISTS item_pedido;";
        db.execSQL(itemPedido);

        onCreate(db);
    }
}