package com.example.romuloroger.runningapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.romuloroger.runningapp.R;
import com.example.romuloroger.runningapp.models.Corredor;

import java.util.ArrayList;
import java.util.List;

public class ResultadoAdapter extends RecyclerView.Adapter<ResultadoAdapter.CorredorViewHolder> {

    private List<Corredor> corredores = new ArrayList<>();
    private Context context;

    public ResultadoAdapter(List<Corredor> corredores) {
        this.corredores = corredores;
    }

    @NonNull
    @Override
    public CorredorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_resultado, viewGroup, false);
        this.context = viewGroup.getContext();
        return new CorredorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CorredorViewHolder corredorViewHolder, int i) {
        final Corredor corredor = this.corredores.get(i);
        corredorViewHolder.nome.setText(corredor.getNome());
        corredorViewHolder.pontuacao.setText(String.valueOf(corredor.getPontuacao())+" pontos");
        corredorViewHolder.posicao.setText(String.valueOf(corredor.getPosicao()+"° lugar"));
    }

    @Override
    public int getItemCount() {
        return corredores.size();
    }

    protected static class CorredorViewHolder extends RecyclerView.ViewHolder {

        protected TextView nome,pontuacao,posicao;

        public CorredorViewHolder(View view) {
            super(view);
            this.nome = view.findViewById(R.id.result_nome);
            this.pontuacao = view.findViewById(R.id.result_pontuacao);
            this.posicao = view.findViewById(R.id.result_posicao);

        }
    }
}
