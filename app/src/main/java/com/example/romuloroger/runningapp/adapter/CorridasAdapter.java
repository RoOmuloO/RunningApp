package com.example.romuloroger.runningapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.romuloroger.runningapp.R;
import com.example.romuloroger.runningapp.fragment.DetalhesCorridaFragment;
import com.example.romuloroger.runningapp.fragment.ResultadoCorridaFragment;
import com.example.romuloroger.runningapp.models.Corrida;
import com.example.romuloroger.runningapp.utils.Preferencias;

import java.util.List;

public class CorridasAdapter extends RecyclerView.Adapter<CorridasAdapter.CorridaViewHolder> {

    private List<Corrida> corridas;
    private Context context;
    private String token;

    public CorridasAdapter(List<Corrida> corridas, String token) {
        this.corridas = corridas;
        this.token = token;
    }

    public CorridasAdapter(List<Corrida> corridas) {
        this.corridas = corridas;
    }

    @NonNull
    @Override
    public CorridaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_corrida, viewGroup, false);
        this.context = viewGroup.getContext();
        return new CorridaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CorridaViewHolder corridaViewHolder, int i) {
        final Corrida corrida = this.corridas.get(i);
        String data = corrida.getDataCorrida().split(" ")[0];
        String hora = corrida.getDataCorrida().split(" ")[1];
        corridaViewHolder.nome.setText(corrida.getNome());
        corridaViewHolder.valor.setText("R$ " + corrida.getValorInscricao());
        corridaViewHolder.data.setText(data);
        corridaViewHolder.horario.setText(hora + " Hs");
        corridaViewHolder.total.setText(corrida.getNumroInscritos() + " inscritos");
        corridaViewHolder.km.setText(corrida.getKm() + " Km");
        corridaViewHolder.status.setText(corrida.isFinalizada() ? "Finalizada" : "Aberta");
        if (!corrida.isFinalizada()) {
            corridaViewHolder.resultado.setVisibility(View.GONE);
        }

        if (!Preferencias.getToken(context).isEmpty()) {
            corridaViewHolder.detalhes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new DetalhesCorridaFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("corridaId", corrida.getId());
                    myFragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, myFragment).addToBackStack(null).commit();
                }
            });

            corridaViewHolder.resultado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new ResultadoCorridaFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("corridaId", corrida.getId());
                    myFragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, myFragment).addToBackStack(null).commit();
                }
            });


        } else {
            corridaViewHolder.detalhes.setVisibility(View.GONE);
            corridaViewHolder.resultado.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return corridas.size();
    }

    protected static class CorridaViewHolder extends RecyclerView.ViewHolder {

        protected TextView nome, data, valor, horario, total, detalhes, resultado, km, status;

        public CorridaViewHolder(View view) {
            super(view);
            this.nome = view.findViewById(R.id.txtItemInscricaoNomeCorrida);
            this.data = view.findViewById(R.id.result_posicao);
            this.valor = view.findViewById(R.id.result_pontuacao);
            this.horario = view.findViewById(R.id.txtItemCorridaHorario);
            this.total = view.findViewById(R.id.txtItemCorridaInscricoes);
            this.km = view.findViewById(R.id.km);
            this.status = view.findViewById(R.id.status);
            this.detalhes = view.findViewById(R.id.detalhes);
            this.resultado = view.findViewById(R.id.resultado);
        }
    }
}


