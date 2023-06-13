package com.example.pc03_appmobil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.pc03_appmobil.dao.ReclamoDao;
import com.example.pc03_appmobil.db.DatabaseHelper;
import com.example.pc03_appmobil.enums.MensajeEnun;
import com.example.pc03_appmobil.enums.ReclamoEnun;
import com.example.pc03_appmobil.model.Reclamo;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetalleReclamo extends AppCompatActivity {

    TextInputLayout codigoInput, asuntoInput, descripcionInput;
    RadioGroup radioGroup;
    RadioButton radioButtonPendiente  ;
    RadioButton radioButtonProceso  ;
    RadioButton radioButtonCompleto ;
    RadioButton radioButtonRechazado ;

    MaterialButton actualizarBtn, eliminarBtn;
    Reclamo reclamo;
    DatabaseHelper databaseHelper;
    ReclamoDao reclamoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reclamo);

        databaseHelper = DatabaseHelper.getInstance(this);
        reclamoDao = new ReclamoDao(databaseHelper.getWritableDatabase());

        codigoInput = findViewById(R.id.codigo);
        asuntoInput = findViewById(R.id.asunto);
        descripcionInput = findViewById(R.id.descripcion);
        radioGroup = findViewById(R.id.radioGroup);
        actualizarBtn = findViewById(R.id.detalleBtnUpdate);
        eliminarBtn = findViewById(R.id.detalleBtnEliminarLibro);
          radioButtonPendiente = findViewById(R.id.radioButtonPendiente);
          radioButtonProceso = findViewById(R.id.radioButtonProceso);
          radioButtonCompleto = findViewById(R.id.radioButtonCompleto);
          radioButtonRechazado = findViewById(R.id.radioButtonRechazado);

        Intent intent = getIntent();
        reclamo = (Reclamo) intent.getSerializableExtra(ReclamoEnun.KEY_NAME.getValue());
        if (reclamo.getCodigo() != null && reclamo.getAsunto() != null && reclamo.getDescripcion() != null) {
            codigoInput.getEditText().setText(reclamo.getCodigo());
            asuntoInput.getEditText().setText(reclamo.getAsunto());
            descripcionInput.getEditText().setText(reclamo.getDescripcion());

            radioButtonPendiente.setChecked(false);
            radioButtonProceso.setChecked(false);
            radioButtonCompleto.setChecked(false);
            radioButtonRechazado.setChecked(false);

            switch (reclamo.getEstado()) {
                case "Pendiente":
                    radioButtonPendiente.setChecked(true);
                    break;
                case "Proceso":
                    radioButtonProceso.setChecked(true);
                     break;
                case "Completo":
                    radioButtonCompleto.setChecked(true);
                    break;
                case "Rechazado":
                    radioButtonRechazado.setChecked(true);
                    break;
                default:
                     return;
            }
        }

        actualizarBtn.setOnClickListener(v -> {
            String codigo = codigoInput.getEditText().getText().toString();
            String asunto = asuntoInput.getEditText().getText().toString();
            String descripcion = descripcionInput.getEditText().getText().toString();
            String estado = "";
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.radioButtonPendiente:
                    estado = radioButtonPendiente.getText().toString();
                    break;
                case R.id.radioButtonProceso:
                    estado = radioButtonProceso.getText().toString();
                    break;
                case R.id.radioButtonCompleto:
                    estado = radioButtonCompleto.getText().toString();
                    break;
                case R.id.radioButtonRechazado:
                    estado = radioButtonRechazado.getText().toString();
                    break;
                default:
                    estado = "";
                    return;
            }
            String fecha = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

            Reclamo reclamoUpdate = new Reclamo(reclamo.getId(),asunto, codigo, descripcion, estado,fecha);
            long res = reclamoDao.update(reclamoUpdate);
            if (res == -1) {
                Toast.makeText(DetalleReclamo.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(DetalleReclamo.this, "Reclamo Actualizado", Toast.LENGTH_SHORT).show();
            Intent intent2 = new Intent(DetalleReclamo.this,MainActivity.class);
            startActivity(intent2);
        });

        eliminarBtn.setOnClickListener(v -> {
            confirmDialog();
        });
    }

    void confirmDialog() {
        new AlertDialog.Builder(this)
                .setTitle(String.format("¿Eliminar %s?", reclamo.getCodigo()))
                .setMessage(MensajeEnun.ELIMINAR_ITEM.getValue())
                .setNegativeButton("No", ((dialog, which) -> {}))
                .setPositiveButton("Sí", (dialog, which) -> {
                    long estado = reclamoDao.deleteById(String.valueOf(reclamo.getId()));
                    if (estado == -1) {
                        return;
                    }

                    finish();
                })
                .show();
    }
}