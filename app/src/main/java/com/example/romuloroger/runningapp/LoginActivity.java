package com.example.romuloroger.runningapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.romuloroger.runningapp.services.LoginServiceTask;
import com.example.romuloroger.runningapp.services.request.LoginRequest;
import com.example.romuloroger.runningapp.services.response.LoginResponse;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    EditText edtLogin, edtSenha;
    ImageButton imgbtnLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding();

        imgbtnLogar.setOnClickListener(botaoLogar());
    }

    private View.OnClickListener botaoLogar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRequest req = new LoginRequest(edtLogin.getText().toString(),edtSenha.getText().toString());

                LoginResponse resp = null;
                try {
                    resp = new LoginServiceTask().execute(req).get();
                } catch (ExecutionException e) {
                    resp = null;
                    Log.e("Erro","onClick: Erro ao consumir o Serviço :: "+e.getMessage() );
                } catch (InterruptedException e) {
                    resp = null;
                    Log.e("Erro","onClick: Erro ao consumir o Serviço :: "+e.getMessage() );
                }

                if (resp == null){
                    Toast.makeText(getApplicationContext(),"Login ou Senha Incorreta!!!",
                            Toast.LENGTH_LONG).show();
                    edtLogin.setText("");
                    edtSenha.setText("");
                    edtLogin.requestFocus();
                }else{
                    Intent itn = new Intent();
                    itn.putExtra("user",resp);
                    setResult(10,itn);
                    finish();
                }
            }
        };
    }

    private void binding() {
        edtLogin = findViewById(R.id.edtTelaLoginLogin);
        edtSenha = findViewById(R.id.edtTelaLoginPassword);
        imgbtnLogar = findViewById(R.id.imgbtnTelaLoginLogar);
    }
}
