package com.example.apppedidos;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.apppedidos.model.ItemPedido;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ListaItemPedidoAdapter extends ArrayAdapter<ItemPedido> {

    private List<ItemPedido> itensPedido;
    private Activity activity;


    public ListaItemPedidoAdapter(Activity context, int resource, List<ItemPedido> objects) {
        super(context, resource, objects);

        this.activity = context;
        this.itensPedido = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lista_itens_pedido, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Integer qtd = getItem(position).getQuantidade();
        Double valor = getItem(position).getValorUnitario();

        holder.nomeProduto.setText(String.valueOf(getItem(position).getProduto().getNome()));
        holder.quantidade.setText(String.valueOf(qtd));
        holder.valorUnitario.setText(String.valueOf(formatNumber(valor)));

        Double total = qtd * valor;
        holder.total.setText(String.valueOf(formatNumber(total)));

        return convertView;
    }

    protected String formatNumber(Double total) {
        Locale br = new Locale("pt", "BR");
        NumberFormat format = NumberFormat.getCurrencyInstance(br);
        return format.format(total);
    }

    private static class ViewHolder {
        private final TextView nomeProduto, quantidade, valorUnitario, total;

        public ViewHolder(View v) {
            nomeProduto = (TextView) v.findViewById(R.id.produto);
            quantidade = (TextView) v.findViewById(R.id.quantidade);
            valorUnitario = (TextView) v.findViewById(R.id.valorUnitario);
            total = (TextView) v.findViewById(R.id.total);
        }
    }
}
