package com.example.apppedidos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apppedidos.dao.PedidoBd;
import com.example.apppedidos.dao.ProdutoBd;
import com.example.apppedidos.model.ItemPedido;
import com.example.apppedidos.model.Produto;

import java.util.ArrayList;

public class PedidoVisualizarActivity extends AppCompatActivity {

    PedidoBd pedidoBd;
    TextView txtCod, txtNomeComprador, txtDataCompra, totalGeral;
    ArrayList<ItemPedido> listaItensPedido;
    ListaItemPedidoAdapter listaItemPedidoAdapter;

    String codigo, nomeComprador, dataCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_visualizar);

        pedidoBd = new PedidoBd(PedidoVisualizarActivity.this);

        listaItensPedido = new ArrayList<>();

        txtCod = findViewById(R.id.txt_Codigo);
        txtNomeComprador = findViewById(R.id.txt_NomeComprador);
        txtDataCompra = findViewById(R.id.txt_DataCompra);
        totalGeral = findViewById(R.id.totalGeral);

        getIntentPedido();

        ListView listView = (ListView) findViewById(R.id.lista_produto);
        listaItemPedidoAdapter = new ListaItemPedidoAdapter(this, R.layout.lista_produtos, listaItensPedido);
        listView.setAdapter(listaItemPedidoAdapter);

        totalGeral.setText(String.valueOf(listaItemPedidoAdapter.formatNumber(getTotalGeral())));
    }

    public void getIntentPedido() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("nome_comprador") && getIntent().hasExtra("data_compra")) {
            codigo = getIntent().getStringExtra("id");
            nomeComprador = getIntent().getStringExtra("nome_comprador");
            dataCompra = getIntent().getStringExtra("data_compra");

            txtCod.setText(codigo);
            txtNomeComprador.setText(nomeComprador);
            txtDataCompra.setText(dataCompra);

            carregarItensPedido(Long.parseLong(codigo));

        } else {
            Toast.makeText(this, "Objeto vazio.", Toast.LENGTH_SHORT).show();
        }
    }

    private void carregarItensPedido(long idPedido)
    {
        listaItensPedido = pedidoBd.listaItensPedido(idPedido);
    }

    private Double getTotalGeral()
    {
        Double totalGeral = 0.00;
        for (ItemPedido item : listaItensPedido)
        {
            totalGeral = totalGeral + (item.getQuantidade() * item.getValorUnitario());
        }
        return totalGeral;
    }
}