package com.example.romuloroger.runningapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.romuloroger.runningapp.models.response.LoginResponse;

public class Preferencias {

    public static void salvarToken(LoginResponse response, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", response.getToken());
        editor.putString("nome", response.getNome());
        editor.commit();
    }

    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }

}
