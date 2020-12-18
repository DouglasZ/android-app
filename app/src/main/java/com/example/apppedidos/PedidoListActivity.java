package com.example.apppedidos;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apppedidos.dao.PedidoBd;
import com.example.apppedidos.model.Pedido;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PedidoListActivity extends AppCompatActivity
{
    FloatingActionButton btnNovoPedido;
    PedidoBd pedidoBd;
    RecyclerView recyclerView;
    ArrayList<String> idPedido, nomeComprador, dataCompra;
    ListaPedidoAdapter listaPedidoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_list);

        recyclerView = findViewById(R.id.listaPedidos);
        pedidoBd = new PedidoBd(PedidoListActivity.this);
        idPedido = new ArrayList<>();
        nomeComprador = new ArrayList<>();
        dataCompra = new ArrayList<>();

        carregaListaPedidos();

        listaPedidoAdapter = new ListaPedidoAdapter(PedidoListActivity.this, idPedido, nomeComprador, dataCompra);
        recyclerView.setAdapter(listaPedidoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(PedidoListActivity.this));

        btnNovoPedido = (FloatingActionButton) findViewById(R.id.btn_NovoPedido);
        btnNovoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PedidoListActivity.this, PedidoActivity.class);
                startActivity(intent);
            }
        });
    }

    void carregaListaPedidos() {
        Cursor cursor = pedidoBd.listaPedidos();
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "Sem pedidos.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                idPedido.add(cursor.getString(0));
                nomeComprador.add(cursor.getString(1));

                Locale br = new Locale("pt", "BR");
                DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", br);
                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", br);

                String inputText = cursor.getString(2);
                Date date = null;
                try {
                    date = inputFormat.parse(inputText);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String outputText = outputFormat.format(date);


                dataCompra.add(outputText);
            }
        }
    }
}