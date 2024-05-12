package com.ezediaz.loginconfoto.ui.login;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.ezediaz.loginconfoto.model.Usuario;
import com.ezediaz.loginconfoto.request.ApiClient;
import com.ezediaz.loginconfoto.ui.registro.RegistroActivity;

public class MainActivityViewModel extends AndroidViewModel {
    private Context context;
    private ApiClient api;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        api = new ApiClient();
    }
    public void loguearse(String email, String password){

        Usuario usuario = api.login(context, email, password);
        if(usuario != null){
            Intent intent = new Intent(context, RegistroActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("flag", 1);
            context.startActivity(intent);
        }  else {
            Toast.makeText(context, "Email o password incorrecto.", Toast.LENGTH_LONG).show();
        }
    }

    public void registrarse(){
        Intent intent = new Intent(context, RegistroActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
