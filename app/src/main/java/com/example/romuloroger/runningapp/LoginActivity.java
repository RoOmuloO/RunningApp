package com.example.romuloroger.runningapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

public class LoginActivity extends AppCompatActivity {

    EditText edtLogin, edtSenha;
    ImageButton imgbtnLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding();
    }

    private void binding() {
        edtLogin = findViewById(R.id.edtTelaLoginLogin);
        edtSenha = findViewById(R.id.edtTelaLoginPassword);
        imgbtnLogar = findViewById(R.id.imgbtnTelaLoginLogar);
    }
}
