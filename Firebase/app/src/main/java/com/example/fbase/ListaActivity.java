package com.example.fbase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUsuarios;
    private UsuarioAdapter usuarioAdapter;
    private List<Usuario> listaUsuarios = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        recyclerViewUsuarios = findViewById(R.id.recyclerViewUsuarios);
        recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewUsuarios.addItemDecoration(new SpaceItemDecoration(2));

        usuarioAdapter = new UsuarioAdapter(listaUsuarios);
        recyclerViewUsuarios.setAdapter(usuarioAdapter);

        // Configurar botón "Lista" para ir a la lista de cultivos
        Button volver = findViewById(R.id.volver);
        volver.setOnClickListener(v -> {
            Intent intent = new Intent(ListaActivity.this, MainActivity.class);
            startActivity(intent);
        });

        Button crear = findViewById(R.id.crear);
        crear.setOnClickListener(v -> {
            Intent intent = new Intent(ListaActivity.this, CrearFichaActivity.class);
            startActivity(intent);
        });

        db = FirebaseFirestore.getInstance();
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        db.collection("usuarios")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    listaUsuarios.clear();  // Limpia la lista para evitar duplicados
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Usuario usuario = document.toObject(Usuario.class);
                        listaUsuarios.add(usuario);  // Agrega cada usuario a la lista
                    }
                    usuarioAdapter.notifyDataSetChanged();  // Refresca el adaptador con los nuevos datos
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al cargar usuarios", Toast.LENGTH_SHORT).show());
    }
}