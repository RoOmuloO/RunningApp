package com.example.romuloroger.runningapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.romuloroger.runningapp.http.HttpService;
import com.example.romuloroger.runningapp.models.request.LoginRequest;
import com.example.romuloroger.runningapp.models.response.LoginResponse;
import com.example.romuloroger.runningapp.utils.GlobalHttpErrorHandler;
import com.example.romuloroger.runningapp.utils.Preferencias;

import org.springframework.web.client.HttpClientErrorException;

public class LoginActivity extends AppCompatActivity {

    EditText edtLogin, edtSenha;
    ImageButton imgbtnLogar;
    TextView txtLoginSenhaIncorretos;
    private ProgressDialog progressDialog;
    private final int LOGIN_AFTER_CADASTRO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding();
        this.progressDialog = new ProgressDialog(this);
    }

    public void botaoLogar(View view) {
        LoginRequest req = new LoginRequest(edtLogin.getText().toString(), edtSenha.getText().toString());
        new LoginTask().execute(req);

    }

    public void limparCampos() {
        edtLogin.setText("");
        edtSenha.setText("");
        edtLogin.requestFocus();
    }

    private void binding() {
        edtLogin = findViewById(R.id.edtTelaLoginLogin);
        edtSenha = findViewById(R.id.edtTelaLoginPassword);
        imgbtnLogar = findViewById(R.id.imgbtnTelaLoginLogar);
        this.txtLoginSenhaIncorretos = findViewById(R.id.txtLoginSenhaIncorretos);
    }

    private class LoginTask extends AsyncTask<LoginRequest, Void, LoginResponse> {


        private HttpClientErrorException httpClientErrorException;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Aguarde....");
            progressDialog.show();
        }

        @Override
        protected LoginResponse doInBackground(LoginRequest... loginRequests) {
            HttpService<LoginResponse, LoginRequest> httpService = new HttpService<>("usuarios/", getApplicationContext(), LoginResponse.class);
            try {
                LoginResponse response = httpService.post("login", loginRequests[0]);
                if (response != null) {
                    Preferencias.salvarToken(response, getApplicationContext());
                }
                return response;
            } catch (HttpClientErrorException e) {
                this.httpClientErrorException = e;
                return null;
            }
        }


        @Override
        protected void onPostExecute(LoginResponse response) {
            super.onPostExecute(response);
            if (this.httpClientErrorException != null) {
                GlobalHttpErrorHandler.getInstance(getApplicationContext()).handle(this.httpClientErrorException);
            }
            if (response == null) {
                txtLoginSenhaIncorretos.setVisibility(View.VISIBLE);
                limparCampos();
            } else {
                Intent itn = new Intent(getApplicationContext(),MainActivity.class);
                itn.putExtra("user", response);
                startActivityForResult(itn,LOGIN_AFTER_CADASTRO);
                //setResult(10, itn);
                //finish();
            }
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOGIN_AFTER_CADASTRO){
            finish();
        }
    }
}
