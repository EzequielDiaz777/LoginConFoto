package com.ezediaz.loginconfoto.request;
import android.content.Context;
import android.widget.Toast;
import com.ezediaz.loginconfoto.model.Usuario;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ApiClient {
    public void guardar(Context context, Usuario usuario){
        File archivo = new File(context.getFilesDir(), "datos.dat");
        try {
            FileOutputStream fos = new FileOutputStream(archivo);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream ous = new ObjectOutputStream(bos);
            ous.writeObject(usuario);
            bos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(context,"Error al guardar", Toast.LENGTH_LONG).show();
        } catch (IOException io){
            io.printStackTrace();
            Toast.makeText(context,"Error de entrada/salida "+io.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public Usuario leer(Context context){
        File archivo = new File(context.getFilesDir(), "datos.dat");
        if(!archivo.exists()){
            return null;
        }
        Usuario usuario = null;
        try {
            FileInputStream fis = new FileInputStream(archivo);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            usuario = (Usuario)ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(context,"Error al guardar",Toast.LENGTH_LONG).show();
        } catch (ClassNotFoundException e) {
            Toast.makeText(context,"Error de clase",Toast.LENGTH_LONG).show();
        } catch (IOException io) {
            Toast.makeText(context, "Error de entrada/salida ", Toast.LENGTH_LONG).show();
        }
        return usuario;
    }

    public Usuario login(Context context, String email, String password){
        Usuario usuario = leer(context);
        if(usuario.getEmail().equals(email) && usuario.getPassword().equals(password)){
            return usuario;
        }
        return null;
    }
}
