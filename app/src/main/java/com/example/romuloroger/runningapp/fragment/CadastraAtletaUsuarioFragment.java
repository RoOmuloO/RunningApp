package com.example.romuloroger.runningapp.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.romuloroger.runningapp.LoginActivity;
import com.example.romuloroger.runningapp.R;
import com.example.romuloroger.runningapp.http.HttpService;
import com.example.romuloroger.runningapp.models.Corredor;

public class CadastraAtletaUsuarioFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ProgressDialog progressDialog;
    private EditText nome, email, login, senha, confirmSenha;
    private ImageButton btnSalvarAtleta;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CadastraAtletaUsuarioFragment() {
        // Required empty public constructor
    }

    public static CadastraAtletaUsuarioFragment newInstance(String param1, String param2) {
        CadastraAtletaUsuarioFragment fragment = new CadastraAtletaUsuarioFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastra_atleta_usuario, container, false);
        this.binding(view);
        this.btnSalvarAtleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarAtleta();
            }
        });
        return view;
    }


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

    public void salvarAtleta() {
        Corredor corredor = new Corredor(
                extrairEditText(this.nome),
                extrairEditText(this.login),
                extrairEditText(this.senha),
                extrairEditText(this.email)
        );
        if(validarCamposObrigatorios() && compararSenhas()){
            new TaskSalvar().execute(corredor);
        }

    }

    private void binding(View view) {
        this.nome = view.findViewById(R.id.editNomeAtleta);
        this.email = view.findViewById(R.id.editEmailAtleta);
        this.login = view.findViewById(R.id.editLoginAtleta);
        this.senha = view.findViewById(R.id.editSenhaAtleta);
        this.confirmSenha = view.findViewById(R.id.editConfirmSenhaAtleta);
        this.btnSalvarAtleta = view.findViewById(R.id.btnSalvarAtleta);
    }

    private String extrairEditText(EditText editText) {
        return editText.getText().toString().trim();
    }

    private boolean validarCamposObrigatorios(){
        boolean valido = true;
        if(extrairEditText(this.nome).isEmpty()){
            valido = false;
            this.nome.setError("O campo nome é obrigatório!");
        }
        if(extrairEditText(this.email).isEmpty()){
            valido = false;
            this.email.setError("O campo email é obrigatório!");
        }
        if(extrairEditText(this.login).isEmpty()){
            valido = false;
            this.login.setError("O campo login é obrigatório!");
        }
        if(extrairEditText(this.senha).isEmpty()){
            valido = false;
            this.senha.setError("O campo senha é obrigatório!");
        }
        if(extrairEditText(this.confirmSenha).isEmpty()){
            valido = false;
            this.confirmSenha.setError("O campo confirmar senha é obrigatório!");
        }
        return valido;
    }

    private boolean compararSenhas(){
        boolean valido = true;
        if(!extrairEditText(this.senha).equals(extrairEditText(this.confirmSenha))){
            this.confirmSenha.setError("As senhas não correspondem!");
            valido = false;
        }
        return valido;
    }

    private void limparCampos(){
        this.nome.setText("");
        this.email.setText("");
        this.login.setText("");
        this.senha.setText("");
        this.confirmSenha.setText("");
    }

    public class TaskSalvar extends AsyncTask<Corredor, Void, Corredor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Aguarde.....");
            progressDialog.show();
        }

        @Override
        protected Corredor doInBackground(Corredor... corredors) {
            HttpService<Corredor, Corredor> httpService = new HttpService<>("corredor", getContext(), Corredor.class);
            Corredor corredor = httpService.post("", corredors[0]);
            return corredor;
        }

        @Override
        protected void onPostExecute(Corredor corredor) {
            super.onPostExecute(corredor);
            if (corredor != null) {
                Toast.makeText(getContext(), "Atleta salvo com sucesso!", Toast.LENGTH_LONG).show();
                limparCampos();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
            progressDialog.dismiss();
        }
    }

}
