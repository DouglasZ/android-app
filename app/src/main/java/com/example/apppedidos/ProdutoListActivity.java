package com.example.apppedidos;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apppedidos.dao.ProdutoBd;
import com.example.apppedidos.model.Produto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProdutoListActivity extends AppCompatActivity
{
    FloatingActionButton btnNovoProduto;
    ListView lista;
    ProdutoBd produtoBd;
    ArrayList<Produto> listaProdutos;
    Produto produto;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_list);

        lista = (ListView) findViewById(R.id.listview_Produtos);
        registerForContextMenu(lista);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Produto produto = (Produto) adapter.getItemAtPosition(position);

                Intent i = new Intent(ProdutoListActivity.this, ProdutoActivity.class);
                i.putExtra("editar-produto", produto);
                startActivity(i);
            }
        });

        btnNovoProduto = (FloatingActionButton) findViewById(R.id.btn_NovoProduto);
        btnNovoProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProdutoListActivity.this, ProdutoActivity.class);
                startActivity(intent);

            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                produto = (Produto) adapter.getItemAtPosition(position);
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuDelete = menu.add("Deseja remover este Produto?");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                produtoBd = new ProdutoBd(ProdutoListActivity.this);
                produtoBd.deletarProduto(produto);
                Toast.makeText(ProdutoListActivity.this,
                        "Produto removido com sucesso.", Toast.LENGTH_SHORT).show();
                carregarLista();
                return true;
            }
        });
    }

    protected void onResume()
    {
        super.onResume();
        carregarLista();
    }

    public void carregarLista()
    {
        produtoBd = new ProdutoBd(ProdutoListActivity.this);
        listaProdutos = produtoBd.getLista();

        if (listaProdutos != null && listaProdutos.size() > 0) {
            adapter = new ArrayAdapter<Produto>(ProdutoListActivity.this, android.R.layout.simple_list_item_1, listaProdutos);
            lista.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Sem produtos.", Toast.LENGTH_SHORT).show();
        }
    }
}