package com.example.apppedidos;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apppedidos.dao.ProdutoBd;
import com.example.apppedidos.model.Produto;

import java.text.NumberFormat;
import java.util.Locale;

public class ProdutoActivity extends AppCompatActivity {

    EditText editText_Nome, editText_Descricao, editText_Valor;
    Button btn_Salvar;
    Produto editarProduto, produto;
    ProdutoBd produtoBd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        produto = new Produto();
        produtoBd = new ProdutoBd(ProdutoActivity.this);

        Intent intent = getIntent();
        editarProduto = (Produto) intent.getSerializableExtra("editar-produto");

        editText_Nome = (EditText) findViewById(R.id.editText_Nome);
        editText_Nome.requestFocus();
        editText_Descricao = (EditText) findViewById(R.id.editText_Descricao);
        editText_Valor = (EditText) findViewById(R.id.editText_Valor);

        btn_Salvar = (Button) findViewById(R.id.btn_Salvar);

        if (editarProduto != null) {
            btn_Salvar.setText("Alterar");

            editText_Nome.setText(editarProduto.getNome());
            editText_Descricao.setText(editarProduto.getDescricao());
            editText_Valor.setText(editarProduto.getValor().toString());

            produto.setId(editarProduto.getId());
        } else {
            btn_Salvar.setText("Salvar");
        }

        btn_Salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editText_Nome.getText().toString().equals("")) {
                    Toast.makeText(ProdutoActivity.this,
                            "Preencha o nome.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(editText_Nome.getText().toString().equals("")) {
                    Toast.makeText(ProdutoActivity.this,
                            "Preencha o valor.", Toast.LENGTH_SHORT).show();
                    return;
                }

                produto.setNome(editText_Nome.getText().toString());
                produto.setDescricao(editText_Descricao.getText().toString());

                String valor = editText_Valor.getText().toString().substring(2);
                valor = valor.replace(".", "").replace(",", ".");
                produto.setValor(Double.parseDouble(valor));

                if (btn_Salvar.getText().toString().equals("Salvar")) {
                    produtoBd.salvarProduto(produto);
                    Toast.makeText(ProdutoActivity.this,
                            "Produto salvo com sucesso.", Toast.LENGTH_SHORT).show();

                    editText_Nome.setText("");
                    editText_Descricao.setText("");
                    editText_Valor.setText("R$0,00");
                    editText_Nome.requestFocus();

                } else {
                    produtoBd.alterarProduto(produto);
                    Toast.makeText(ProdutoActivity.this,
                            "Produto alterado com sucesso.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        editText_Valor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            boolean mEditing = false;;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!mEditing) {
                    mEditing = true;
                    Locale myLocale = new Locale("pt", "BR");
                    String digits = s.toString().replaceAll("\\D", "");
                    NumberFormat nf = NumberFormat.getCurrencyInstance(myLocale);
                    try{
                        String formatted = nf.format(Double.parseDouble(digits)/100);
                        s.replace(0, s.length(), formatted);
                    } catch (NumberFormatException nfe) {
                        s.clear();
                    }

                    mEditing = false;
                }
            }
        });
    }
}