package com.example.romuloroger.runningapp.services.usuario;

import android.content.Context;

import com.example.romuloroger.runningapp.models.request.LoginRequest;
import com.example.romuloroger.runningapp.models.response.LoginResponse;
import com.example.romuloroger.runningapp.tasks.usuario.LoginTask;

import java.util.concurrent.ExecutionException;

public class UsuarioService {

    public static UsuarioService usuarioService;
    private Context context;

    private UsuarioService(Context context) {
        this.context = context;
    }

    public LoginResponse login(LoginRequest loginRequest) {

        try {
            return new LoginTask(context).execute(loginRequest).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void logout() {

    }


    public static UsuarioService getInstance(Context context) {
        if (usuarioService == null) {
            usuarioService = new UsuarioService(context);
        }
        return usuarioService;
    }

}
