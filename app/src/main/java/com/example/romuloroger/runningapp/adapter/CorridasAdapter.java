package com.example.romuloroger.runningapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.romuloroger.runningapp.R;
import com.example.romuloroger.runningapp.models.Corrida;

import java.util.List;

public class CorridasAdapter extends RecyclerView.Adapter<CorridasAdapter.CorridaViewHolder> {

    private List<Corrida> corridas;

    public CorridasAdapter(List<Corrida> corridas) {
        this.corridas = corridas;
    }

    @NonNull
    @Override
    public CorridaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_corrida, viewGroup, false);
        return new CorridaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CorridaViewHolder corridaViewHolder, int i) {
        Corrida corrida = this.corridas.get(i);
        corridaViewHolder.nome.setText(corrida.getNome());
        corridaViewHolder.valor.setText("R$ " + corrida.getValorInscricao());
        corridaViewHolder.data.setText(corrida.getDataCorrida().toString());
    }

    @Override
    public int getItemCount() {
        return corridas.size();
    }

    protected static class CorridaViewHolder extends RecyclerView.ViewHolder {

        protected TextView nome, data, valor, horario, tipo, local;

        public CorridaViewHolder(View view) {
            super(view);
            this.nome = view.findViewById(R.id.txtItemInscricaoNomeCorrida);
            this.data = view.findViewById(R.id.txtItemInscricaoDataCorrida);
            this.valor = view.findViewById(R.id.txtItemInscricaoValorInscricao);
        }
    }
}


