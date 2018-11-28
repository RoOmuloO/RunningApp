package com.example.romuloroger.runningapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.romuloroger.runningapp.R;
import com.example.romuloroger.runningapp.models.Inscricao;

import java.util.List;

public class InscricoesAdapter extends RecyclerView.Adapter<InscricoesAdapter.InscricaoViewHolder> {

    private List<Inscricao> inscricoes;
    private Context context;


    public InscricoesAdapter(List<Inscricao> inscricoes){
        this.inscricoes = inscricoes;
    }


    @NonNull
    @Override
    public InscricaoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_inscricao, viewGroup, false);
        this.context = viewGroup.getContext();
        return new InscricoesAdapter.InscricaoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InscricaoViewHolder inscricaoViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return inscricoes.size();
    }

    protected static class InscricaoViewHolder extends RecyclerView.ViewHolder{

        protected TextView nome, data, valor, horario, situacaoInscricao, detalhes,local;

        public InscricaoViewHolder(@NonNull View View) {
            super(View);

            this.nome = View.findViewById(R.id.txtItemInscricaoNomeCorrida);
            this.data = View.findViewById(R.id.txtItemInscricaoDataCorrida);
            this.valor = View.findViewById(R.id.txtItemInscricaoValorInscricao);
            this.horario = View.findViewById(R.id.txtItemInscricaoHorario);
            this.detalhes = View.findViewById(R.id.txtItemInscricaoTipoCorrida);
            this.situacaoInscricao = View.findViewById(R.id.txtItemInscricaoStatusInscricao);
//            this.local = View.findViewById(R.id.txtItemInscricaoLocalCorrida);
        }
    }

}
