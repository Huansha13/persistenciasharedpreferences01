package xyz.android.persistenciasharedpreferences01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String PREF_NAME = "RegistroPref";

    private EditText editTextUserName, editTextEmail;
    private TextView textViewRegistro;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Iniciar componentes
        editTextUserName = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        Button btnRegistrar = findViewById(R.id.buttonRegister);
        Button btnListar = findViewById(R.id.buttonList);
        textViewRegistro = findViewById(R.id.textViewRegistro);

        //Obtener instancia de SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        listaRegistro();

        // Configurar el click listener del boton registrar
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUserName.getText().toString();
                String email = editTextEmail.getText().toString();
                if (!username.isEmpty() && !email.isEmpty()) {
                    registrarUsuario(username, email);
                } else {
                  mensaje("Compos requeridos");
                }
            }
        });

        // Configurar el click listener del boton Listar
        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaRegistro();
            }
        });
    }

    private void registrarUsuario(String username, String email) {
        try {
            // Obtener el editor de SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // Guardar el usuario y correo electronico
            editor.putString(username, email);
            boolean guardadoExitoso = editor.commit();
            if (guardadoExitoso) {
                mensaje("Contacto registrado");
                limpiarFormulario();
            }
        } catch (Exception e) {
            mensaje("Error al registrar contacto");
        }
    }

    private void listaRegistro() {
        // Obtener _todo los registros almacenados
        Map<String, ?> registros = sharedPreferences.getAll();
        
        if (registros == null || registros.size() == 0) {
            mensaje("No hay registros");
           return;
        }

        // Construir el texto a mostrar
        StringBuilder registroText = new StringBuilder();
        for (Map.Entry<String, ?> entry : registros.entrySet()) {
            String username = entry.getKey();
            String email = entry.getValue().toString();
            registroText.append("Usuario: ").append(username).append(", Correo: ").append(email).append("\n");
        }

        textViewRegistro.setText(registroText.toString());
    }

    private void mensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void limpiarFormulario() {
        editTextEmail.setText("");
        editTextUserName.setText("");
    }
}