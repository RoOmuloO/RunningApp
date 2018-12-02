package com.example.romuloroger.runningapp.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.romuloroger.runningapp.R;
import com.example.romuloroger.runningapp.adapter.CorridasAdapter;
import com.example.romuloroger.runningapp.http.HttpService;
import com.example.romuloroger.runningapp.models.Corrida;
import com.example.romuloroger.runningapp.utils.GlobalHttpErrorHandler;

import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaCorridas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaCorridas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaCorridas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String token;
    private String mParam2;


    private RecyclerView recViewListaCorridas;
    private SearchView svPesquisa;
    private Button btnFiltrar;
    private ProgressDialog progressDialog;
    private List<Corrida> corridasFiltro = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public ListaCorridas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaCorridas.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaCorridas newInstance(String param1, String param2) {
        ListaCorridas fragment = new ListaCorridas();
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
            token = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private void binding(View view) {
        svPesquisa = view.findViewById(R.id.svListaCorridas);
        btnFiltrar = view.findViewById(R.id.btnTelaListaCorridasFiltrar);
        recViewListaCorridas = view.findViewById(R.id.listaCorridas);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_corridas, container, false);
        this.binding(view);
        this.progressDialog = new ProgressDialog(getContext());
        new BuscarTodasCorridasTask().execute();
        this.filtrarCorridas();
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

    private void filtrarCorridas() {
        this.btnFiltrar.setOnClickListener(new View.OnClickListener() {
            List<Corrida> filtro = new ArrayList<>();
            @Override
            public void onClick(View view) {
                filtro = new ArrayList<>();
                String txtFiltro = svPesquisa.getQuery().toString().trim().toLowerCase();
                for (Corrida corrida : corridasFiltro) {
                    if (corrida.getNome().trim().toLowerCase().contains(txtFiltro)) {
                        addCorrida(corrida);
                    }
                    if (corrida.getDataCorrida().contains(txtFiltro)) {
                        addCorrida(corrida);
                    }
                    String valor = String.valueOf(corrida.getValorInscricao());
                    String valorComSifrao = "R$ " + valor;
                    if (valor.contains(txtFiltro) || valorComSifrao.contains(txtFiltro)) {
                        addCorrida(corrida);
                    }
                    String totalInscritos = String.valueOf(corrida.getNumroInscritos());
                    String totalInscritosExtenso = totalInscritos+" inscritos";
                    if(totalInscritos.contains(txtFiltro) || totalInscritosExtenso.contains(txtFiltro)){
                        addCorrida(corrida);
                    }
                }
                CorridasAdapter corridasAdapter = new CorridasAdapter(filtro);
                configurarRecView(corridasAdapter);
            }

            private void addCorrida(Corrida corrida){
                if(!filtro.contains(corrida)){
                    filtro.add(corrida);
                }
            }
        });
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

    public class BuscarTodasCorridasTask extends AsyncTask<Void, Void, List<Corrida>> {


        private HttpClientErrorException httpClientErrorException;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Aguarde....");
            progressDialog.show();
        }

        @Override
        protected List<Corrida> doInBackground(Void... voids) {
            HttpService<Corrida, Corrida> httpService = new HttpService<>("corridas/abertas", getContext(), Corrida.class);
            try {
                List<Corrida> corridas = httpService.getAll("", Corrida[].class);
                return corridas;
            } catch (HttpClientErrorException e) {
                this.httpClientErrorException = e;
            } catch (Exception ex) {

            }

            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(List<Corrida> corridas) {
            super.onPostExecute(corridas);
            if (this.httpClientErrorException != null) {
                GlobalHttpErrorHandler.getInstance(getContext()).handle(this.httpClientErrorException);
            } else {
                this.listarCorridas(corridas);
            }

            progressDialog.dismiss();
        }

        private void listarCorridas(List<Corrida> corridas) {
            corridasFiltro = corridas;
            if (token != null) {
                CorridasAdapter corridasAdapter = new CorridasAdapter(corridas, token);
                configurarRecView(corridasAdapter);
            } else {
                CorridasAdapter corridasAdapter = new CorridasAdapter(corridas);
                configurarRecView(corridasAdapter);
            }


        }

    }

}
