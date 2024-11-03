package com.example.fbase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CrearFichaActivity extends AppCompatActivity {

    private EditText etNombre, etCorreo, etTelefono;
    private Button btnGuardarFicha;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_ficha);

        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreo);
        etTelefono = findViewById(R.id.etTelefono);
        btnGuardarFicha = findViewById(R.id.btnGuardarFicha);

        db = FirebaseFirestore.getInstance();

        btnGuardarFicha.setOnClickListener(view -> guardarFicha());

        // Configurar botón "Lista" para ir a la lista de cultivos
        Button lista = findViewById(R.id.lista);
        lista.setOnClickListener(v -> {
            Intent intent = new Intent(CrearFichaActivity.this, ListaActivity.class);
            startActivity(intent);
        });

    }

    private void guardarFicha() {
        String nombre = etNombre.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();

        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> usuario = new HashMap<>();
        usuario.put("nombre", nombre);
        usuario.put("correo", correo);
        usuario.put("telefono", telefono);

        db.collection("usuarios")
                .add(usuario)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Ficha guardada correctamente", Toast.LENGTH_SHORT).show();
                    // Con esto se reinician los campos
                    etNombre.setText("");
                    etCorreo.setText("");
                    etTelefono.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al guardar la ficha", Toast.LENGTH_SHORT).show();
                });
    }
}