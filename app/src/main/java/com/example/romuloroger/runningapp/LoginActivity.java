package com.example.romuloroger.runningapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.romuloroger.runningapp.models.request.LoginRequest;
import com.example.romuloroger.runningapp.models.response.LoginResponse;
import com.example.romuloroger.runningapp.services.usuario.UsuarioService;

public class LoginActivity extends AppCompatActivity {

    EditText edtLogin, edtSenha;
    ImageButton imgbtnLogar;
    TextView txtLoginSenhaIncorretos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding();
    }

    public void botaoLogar(View view) {
        LoginRequest req = new LoginRequest(edtLogin.getText().toString(), edtSenha.getText().toString());
        LoginResponse loginResponse = UsuarioService.getInstance(this).login(req);
        if (loginResponse == null) {
            txtLoginSenhaIncorretos.setVisibility(View.VISIBLE);
            limparCampos();
        } else {
            Intent itn = new Intent();
            itn.putExtra("user", loginResponse);
            setResult(10, itn);
            finish();
        }
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

}
