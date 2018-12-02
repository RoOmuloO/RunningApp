package com.example.romuloroger.runningapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.romuloroger.runningapp.models.response.LoginResponse;

public class Preferencias {

    private int id;
    private String nome, email, token,tipo;

    public static void salvarToken(LoginResponse response, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", response.getToken());
        editor.putInt("id", response.getId());
        editor.putString("nome", response.getNome());
        editor.putString("email", response.getEmail());
        editor.putString("tipo", response.getTipo());
        editor.commit();
    }

    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }

    public static void limparDados(Context context){
        SharedPreferences settings = context.getSharedPreferences("shared", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }


    public static LoginResponse getUsuarioLogado(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE);
        LoginResponse response = new LoginResponse(
                sharedPreferences.getInt("id",0),
                sharedPreferences.getString("nome", ""),
                sharedPreferences.getString("email", ""),
                sharedPreferences.getString("token", ""),
                sharedPreferences.getString("tipo", "")
        );
        return response;
    }



}
