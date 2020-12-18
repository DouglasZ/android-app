package com.example.apppedidos;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.apppedidos.model.Produto;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListaProdutoAdapter extends ArrayAdapter<Produto> {

    private List<Produto> produtos;
    private Activity activity;
    private int selectedPosition = -1;
    ArrayList<Produto> produtosSelecionados;

    TextView tvTotal;


    public ListaProdutoAdapter(Activity context, int resource, List<Produto> objects) {
        super(context, resource, objects);

        this.activity = context;
        this.produtos = objects;

        produtosSelecionados = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        tvTotal = (TextView) activity.findViewById(R.id.total_produtos);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lista_produtos, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.checkBox.setTag(position); // This line is important.

        holder.nomeProduto.setText(getItem(position).getNome());
        if (position == selectedPosition) {
            holder.checkBox.setChecked(true);
        } else holder.checkBox.setChecked(false);

        holder.checkBox.setOnClickListener(onStateChangedListener(holder, position));


        holder.tvquantidade.setText(String.valueOf(getItem(position).getQuantidade()));
        holder.btn_mais.setTag(R.integer.btn_plus_view, convertView);
        holder.btn_mais.setTag(R.integer.btn_plus_pos, position);
        holder.btn_mais.setEnabled(false);
        holder.btn_mais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarQuantidade(holder, position);
            }
        });

        holder.btn_menos.setTag(R.integer.btn_minus_view, convertView);
        holder.btn_menos.setTag(R.integer.btn_minus_pos, position);
        holder.btn_menos.setEnabled(false);
        holder.btn_menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diminuirQuantidade(holder, position, null);
            }
        });

        return convertView;
    }

    private View.OnClickListener onStateChangedListener(final ViewHolder holder, final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    selectedPosition = position;

                    // Habilitamos os botões quando o Check for marcado
                    holder.btn_mais.setEnabled(true);
                    holder.btn_menos.setEnabled(true);

                    // Adicionamos 1 na quantidade quando o check for marcado
                    adicionarQuantidade(holder, position);

                    produtosSelecionados.add(getItem(position));

                    calculaTotal();
                } else {
                    selectedPosition = -1;

                    // Desabilitamos os botões quando o Check forma marcado
                    holder.btn_mais.setEnabled(false);
                    holder.btn_menos.setEnabled(false);

                    // Adicionamos 1 na quantidade quando o check for marcado
                    diminuirQuantidade(holder, position, 0);

                    produtosSelecionados.remove(getItem(position));

                    calculaTotal();
                }
            }
        };
    }

    private void adicionarQuantidade(ViewHolder holder, int position)
    {
        View tempview = (View) holder.btn_mais.getTag(R.integer.btn_plus_view);
        TextView tv = (TextView) tempview.findViewById(R.id.txt_quantidade);
        Integer pos = (Integer) holder.btn_mais.getTag(R.integer.btn_plus_pos);

        int number = Integer.parseInt(tv.getText().toString()) + 1;
        tv.setText(String.valueOf(number));

        getItem(pos).setQuantidade(number);

        calculaTotal();
    }

    private void diminuirQuantidade(ViewHolder holder, int position, Integer quantidade)
    {
        View tempview = (View) holder.btn_menos.getTag(R.integer.btn_minus_view);
        TextView tv = (TextView) tempview.findViewById(R.id.txt_quantidade);
        Integer pos = (Integer) holder.btn_menos.getTag(R.integer.btn_minus_pos);

        int number = quantidade == null ? Integer.parseInt(tv.getText().toString()) - 1 : quantidade;
        number = Math.max(number, 0);

        tv.setText(String.valueOf(number));

        getItem(pos).setQuantidade(number);

        calculaTotal();
    }

    private void calculaTotal()
    {
        double total = 0.00;
        for(Produto produto : getProdutos()){
            total = total + (produto.getValor() * produto.getQuantidade());
        }
        tvTotal.setText(formatNumber(total));
    }

    private String formatNumber(Double total) {
        Locale br = new Locale("pt", "BR");
        NumberFormat format = NumberFormat.getCurrencyInstance(br);
       return format.format(total);
    }

    public List<Produto> getProdutos() {
        return produtosSelecionados;
    }

    private static class ViewHolder {
        private final TextView nomeProduto;
        private final CheckBox checkBox;

        private final ImageButton btn_mais, btn_menos;
        private final TextView tvquantidade;

        public ViewHolder(View v) {
            checkBox = (CheckBox) v.findViewById(R.id.checkbox);
            nomeProduto = (TextView) v.findViewById(R.id.produto);

            btn_mais = (ImageButton) v.findViewById(R.id.btn_mais);
            btn_menos = (ImageButton) v.findViewById(R.id.btn_menos);

            tvquantidade = (TextView) v.findViewById(R.id.txt_quantidade);
        }
    }
}
