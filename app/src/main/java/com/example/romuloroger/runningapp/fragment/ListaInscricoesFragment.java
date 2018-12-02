package com.example.romuloroger.runningapp.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.example.romuloroger.runningapp.R;
import com.example.romuloroger.runningapp.adapter.CorridasAdapter;
import com.example.romuloroger.runningapp.http.HttpService;
import com.example.romuloroger.runningapp.models.Corredor;
import com.example.romuloroger.runningapp.models.Corrida;
import com.example.romuloroger.runningapp.utils.GlobalHttpErrorHandler;
import com.example.romuloroger.runningapp.utils.Preferencias;

import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaInscricoesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaInscricoesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaInscricoesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recViewListaCorridas;
    private SearchView svPesquisa;
    private Button btnFiltrar;
    private ProgressDialog progressDialog;
    private List<Corrida> corridasFiltro = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public ListaInscricoesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ListaInscricoesFragment newInstance(String param1, String param2) {
        ListaInscricoesFragment fragment = new ListaInscricoesFragment();
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

    }

    private void binding(View view) {
        svPesquisa = view.findViewById(R.id.buscaInscricoes);
        btnFiltrar = view.findViewById(R.id.btnInscricoes);
        recViewListaCorridas = view.findViewById(R.id.listaInscricoes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_inscricoes, container, false);
        this.binding(view);
        this.progressDialog = new ProgressDialog(getContext());
        new BuscarInscricoesTask().execute();
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

    private void configurarRecView(CorridasAdapter corridasAdapter) {
        if (recViewListaCorridas != null) {
            recViewListaCorridas.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recViewListaCorridas.setLayoutManager(layoutManager);
            recViewListaCorridas.setAdapter(corridasAdapter);
        }
    }

    public class BuscarInscricoesTask extends AsyncTask<Void, Void, Corredor> {


        private HttpClientErrorException httpClientErrorException;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Aguarde....");
            progressDialog.show();
        }

        @Override
        protected Corredor doInBackground(Void... voids) {
            HttpService<Corredor, String> httpService = new HttpService<>("corredor", getContext(), Corredor.class);
            try {
                Corredor corredor = httpService.getById("");
                return corredor;
            } catch (HttpClientErrorException e) {
                this.httpClientErrorException = e;
                return null;
            } catch (Exception ex) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Corredor corredor) {
            super.onPostExecute(corredor);
            if (this.httpClientErrorException != null) {
                GlobalHttpErrorHandler.getInstance(getContext()).handle(this.httpClientErrorException);
            } else {
                if(corredor != null && corredor.getCorridas() != null){
                    List<Corrida>corridas = new ArrayList<>();
                    for (Corrida corrida:corredor.getCorridas()){
                        if(!corrida.isCancelada()){
                            corridas.add(corrida);
                        }
                    }
                    this.listarCorridas(corridas);
                }
            }
            progressDialog.dismiss();
        }

        private void listarCorridas(List<Corrida> corridas) {
            corridasFiltro = corridas;
            CorridasAdapter corridasAdapter = new CorridasAdapter(corridas);
            configurarRecView(corridasAdapter);
        }

    }

}
