package com.ezediaz.loginconfoto.ui.registro;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.ezediaz.loginconfoto.databinding.ActivityRegistroBinding;
import com.ezediaz.loginconfoto.model.Usuario;

public class RegistroActivity extends AppCompatActivity {
    private ActivityRegistroBinding binding;
    private RegistroActivityViewModel vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RegistroActivityViewModel.class);
        vm.getMUsuario().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                if (usuario != null){
                    binding.etDNI.setText(usuario.getDni());
                    binding.etApellido.setText(usuario.getApellido());
                    binding.etNombre.setText(usuario.getNombre());
                    binding.etEmailPerfil.setText(usuario.getEmail());
                    binding.etPasswordPerfil.setText(usuario.getPassword());
                    vm.cargarFoto();
                }
            }
        });
        Intent intent = getIntent();
        int i = intent.getIntExtra("flag", 0);
        vm.cargar(i);
        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dni = binding.etDNI.getText().toString();
                String nombre = binding.etNombre.getText().toString();
                String apellido = binding.etApellido.getText().toString();
                String email = binding.etEmailPerfil.getText().toString();
                String password = binding.etPasswordPerfil.getText().toString();
                vm.guardar(dni, nombre, apellido, email, password);
            }
        });
        vm.getMFoto().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                binding.ivFoto.setImageBitmap(bitmap);
            }
        });
        binding.btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        vm.respuestaCamara(requestCode, resultCode, data, 1);
    }
}