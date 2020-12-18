package com.example.apppedidos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apppedidos.dao.PedidoBd;
import com.example.apppedidos.dao.ProdutoBd;
import com.example.apppedidos.model.ItemPedido;
import com.example.apppedidos.model.Pedido;
import com.example.apppedidos.model.Produto;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PedidoActivity extends AppCompatActivity {

    EditText edtNomeComprador;
    ArrayList<Produto> listaProdutos;
    ProdutoBd produtoBd;
    PedidoBd pedidoBd;
    Pedido pedido;
    Button btnFinalizarPedido;
    ListaProdutoAdapter produtoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        pedido = new Pedido();
        pedidoBd = new PedidoBd(PedidoActivity.this);

        edtNomeComprador = (EditText) findViewById(R.id.editText_NomeComprador);
        edtNomeComprador.requestFocus();

        listaProdutos = new ArrayList<>();
        ListView listView = (ListView) findViewById(R.id.lista_produto);

        carregarListaProdutos();

        produtoAdapter = new ListaProdutoAdapter(this, R.layout.lista_produtos, listaProdutos);
        listView.setAdapter(produtoAdapter);

        btnFinalizarPedido = (Button) findViewById(R.id.btn_FinalizarPedido);
        btnFinalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pedido.setNomeComprador(edtNomeComprador.getText().toString());

                List<ItemPedido> itens = new ArrayList<>();

                for (Produto produto : produtoAdapter.getProdutos()) {
                    ItemPedido item = new ItemPedido();
                    item.setProduto(produto);
                    item.setQuantidade(produto.getQuantidade());
                    item.setValorUnitario(produto.getValor());
                    itens.add(item);
                }
                pedido.setPedidos(itens);

                pedidoBd.salvarPedido(pedido);
            }
        });
    }

    private void carregarListaProdutos()
    {
        produtoBd = new ProdutoBd(PedidoActivity.this);
        listaProdutos = produtoBd.getLista();
    }
}