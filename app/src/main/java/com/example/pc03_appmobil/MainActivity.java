package com.example.pc03_appmobil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.pc03_appmobil.adapter.ReclamoAdapter;
import com.example.pc03_appmobil.dao.ReclamoDao;
import com.example.pc03_appmobil.db.DatabaseHelper;
import com.example.pc03_appmobil.enums.MensajeEnun;
import com.example.pc03_appmobil.enums.ReclamoEnun;
import com.example.pc03_appmobil.model.Reclamo;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton btnAgregar;
    LinearLayout no_data;

    DatabaseHelper databaseHelper;
    ReclamoDao reclamoDao;
    ReclamoAdapter reclamoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = DatabaseHelper.getInstance(this);
        reclamoDao = new ReclamoDao(databaseHelper.getWritableDatabase());

        recyclerView = findViewById(R.id.recyclerView);
        btnAgregar = findViewById(R.id.btnAddLibro);
        no_data = findViewById(R.id.empty_lista);


        loadTareas();

        btnAgregar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AgregarReclamo.class);
            startActivity(intent);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all) {
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadTareas() {
        List<Reclamo> libros = reclamoDao.getAll();
        if (libros.isEmpty()) {
            no_data.setVisibility(View.VISIBLE);
        }

        reclamoAdapter = new ReclamoAdapter(MainActivity.this, this, libros);
        recyclerView.setAdapter(reclamoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    void confirmDialog() {
        MaterialAlertDialogBuilder builder =  new MaterialAlertDialogBuilder(this)
                .setTitle("¿Eliminar todo?")
                .setMessage(MensajeEnun.ELIMINAR_TODO.getValue())
                .setIcon(R.drawable.ic_delete_sweep_r)
                .setNeutralButton("Cancelar", (dialog, which) -> {})
                .setPositiveButton("Sí", (dialog, which) -> {
                    long estado = reclamoDao.deleteAll();
                    if (estado == -1) {
                        return;
                    }

                    // Actualizar la actividad
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}