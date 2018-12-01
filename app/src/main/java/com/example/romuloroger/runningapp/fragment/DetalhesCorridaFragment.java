package com.example.romuloroger.runningapp.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.romuloroger.runningapp.LoginActivity;
import com.example.romuloroger.runningapp.R;
import com.example.romuloroger.runningapp.http.HttpService;
import com.example.romuloroger.runningapp.models.Corrida;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetalhesCorridaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetalhesCorridaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalhesCorridaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ProgressDialog progressDialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private int idCorrida;
    private TextView nome, data, hora, valor, inscicoes;
    private Button btnInscrever;

    public DetalhesCorridaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetalhesCorridaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalhesCorridaFragment newInstance(String param1, String param2) {
        DetalhesCorridaFragment fragment = new DetalhesCorridaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        this.progressDialog = new ProgressDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhes_corrida, container, false);
        this.idCorrida = this.getArguments().getInt("corridaId");
        new TaskBuscarCorrida().execute(this.idCorrida);
        this.binding(view);


        this.inscreverCorrida();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    //Meus métodos

    private void inscreverCorrida(){
        this.btnInscrever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TaskInscricaoCorrida().execute(idCorrida);
            }
        });
    }

    private void binding(View view) {
        this.nome = view.findViewById(R.id.detalhe_corrida_nome);
        this.data = view.findViewById(R.id.detalhe_corrida_data);
        this.hora = view.findViewById(R.id.detalhe_corrida_hora);
        this.inscicoes = view.findViewById(R.id.detalhe_corrida_inscricoes);
        this.valor = view.findViewById(R.id.detalhe_corrida_valor);
        this.btnInscrever = view.findViewById(R.id.btnInscricao);
    }

    private void showLoading() {
        progressDialog.setTitle("Aguarde.....");
        progressDialog.show();
    }

    private void hideLoading() {
        progressDialog.dismiss();
    }

    public class TaskBuscarCorrida extends AsyncTask<Integer, Void, Corrida> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        protected Corrida doInBackground(Integer... ids) {
            HttpService<Corrida, Corrida> httpService = new HttpService<>("corridas", getContext(), Corrida.class);
            Corrida corrida = httpService.getById("/" + ids[0]);
            return corrida;
        }

        @Override
        protected void onPostExecute(Corrida corrida) {
            super.onPostExecute(corrida);
            if (corrida != null) {
                nome.setText(corrida.getNome());
                data.setText(corrida.getDataCorrida().split(" ")[0]);
                hora.setText(corrida.getDataCorrida().split(" ")[1] + " Hs");
                inscicoes.setText(String.valueOf(corrida.getNumroInscritos() + " inscrito(s)"));
                valor.setText("R$ " + corrida.getValorInscricao());
            }
            hideLoading();
        }
    }

    public class TaskInscricaoCorrida extends AsyncTask<Integer, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        protected String doInBackground(Integer... id) {
            try {
                HttpService<String, String> httpService = new HttpService<String, String>("corridas/", getContext(), String.class);
                String mensagem = httpService.post(id[0] + "/inscrever","");
                return mensagem;
            }catch (Exception ex){
                return null;
            }
        }

        @Override
        protected void onPostExecute(String mensagem) {
            super.onPostExecute(mensagem);
            hideLoading();
            Toast.makeText(getContext(), mensagem, Toast.LENGTH_SHORT).show();
        }

    }

}
