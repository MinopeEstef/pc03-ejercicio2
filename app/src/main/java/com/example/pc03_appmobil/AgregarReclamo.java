package com.example.pc03_appmobil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pc03_appmobil.dao.ReclamoDao;
import com.example.pc03_appmobil.db.DatabaseHelper;
import com.example.pc03_appmobil.enums.MensajeEnun;
import com.example.pc03_appmobil.model.Reclamo;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AgregarReclamo extends AppCompatActivity {

    Reclamo reclamo;
    DatabaseHelper databaseHelper;
    ReclamoDao reclamoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_reclamo);

        databaseHelper = DatabaseHelper.getInstance(this);
        reclamoDao = new ReclamoDao(databaseHelper.getWritableDatabase());

        TextInputLayout codigoTextInput = findViewById(R.id.codigo);
        TextInputLayout asuntoTexttInput = findViewById(R.id.asunto);
        TextInputLayout descripcionTextInput = findViewById(R.id.descripcion);
        MaterialButton materialButton = findViewById(R.id.addLibroBtn);

        materialButton.setOnClickListener(v -> {
            try {

                EditText codigoEditText = codigoTextInput.getEditText();
                EditText asuntoEditText = asuntoTexttInput.getEditText();
                EditText descripcionTextInputEditText = descripcionTextInput.getEditText();

                assert codigoEditText != null && asuntoEditText != null && descripcionTextInputEditText != null;

                String codigo = codigoEditText.getText().toString();
                String asunto = asuntoEditText.getText().toString();
                String descripcion = descripcionTextInputEditText.getText().toString();
                String fecha = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                if (!codigo.isEmpty() && !asunto.isEmpty() && !descripcion.isEmpty()) {
                    reclamo = new Reclamo(asunto, codigo,descripcion,"Pendiente",fecha);
                    long estado = reclamoDao.insert(reclamo);
                    if (estado == -1) {
                        Toast.makeText(AgregarReclamo.this, MensajeEnun.ERROR_REGISTRO.getValue(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(AgregarReclamo.this, MensajeEnun.OK_REGISTRO.getValue(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AgregarReclamo.this,MainActivity.class);
                     startActivity(intent);
                } else {
                    throw new IllegalArgumentException(MensajeEnun.CAMPO_IMPOCOMPLETO.getValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(AgregarReclamo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });

    }
}