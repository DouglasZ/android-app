package com.example.apppedidos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListaPedidoAdapter extends RecyclerView.Adapter<ListaPedidoAdapter.ListaViewHolder> {

    Context context;
    ArrayList<String> idPedido, nomeComprador, dataCompra;

    public ListaPedidoAdapter(Context context, ArrayList<String> idPedido, ArrayList<String> nomeComprador, ArrayList<String> dataCompra) {
        this.context = context;
        this.idPedido = idPedido;
        this.nomeComprador = nomeComprador;
        this.dataCompra = dataCompra;
    }

    @NonNull
    @Override
    public ListaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lista_pedidos, parent, false);
        return new ListaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaViewHolder holder, int position) {
        holder.idPedido.setText(String.valueOf(idPedido.get(position)));
        holder.nomeComprador.setText(String.valueOf(nomeComprador.get(position)));
        holder.dataCompra.setText(String.valueOf(dataCompra.get(position)));
        holder.listaPedidosLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PedidoVisualizarActivity.class);
                intent.putExtra("id", String.valueOf(idPedido.get(position)));
                intent.putExtra("nome_comprador", String.valueOf(nomeComprador.get(position)));
                intent.putExtra("data_compra", String.valueOf(dataCompra.get(position)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return idPedido.size();
    }

    public static class ListaViewHolder extends RecyclerView.ViewHolder {

        TextView idPedido, nomeComprador, dataCompra;
        LinearLayout listaPedidosLayout;

        public ListaViewHolder(@NonNull View itemView) {
            super(itemView);
            idPedido = itemView.findViewById(R.id.txt_idPedido);
            nomeComprador = itemView.findViewById(R.id.txt_NomeComprador);
            dataCompra = itemView.findViewById(R.id.txt_DataCompra);
            listaPedidosLayout = itemView.findViewById(R.id.listaPedidosLayout);
        }
    }
}
