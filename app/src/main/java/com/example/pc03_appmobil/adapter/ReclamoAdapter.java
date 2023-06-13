package com.example.pc03_appmobil.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pc03_appmobil.DetalleReclamo;
import com.example.pc03_appmobil.MainActivity;
import com.example.pc03_appmobil.R;
import com.example.pc03_appmobil.enums.ReclamoEnun;
import com.example.pc03_appmobil.model.Reclamo;

import java.util.List;

public class ReclamoAdapter extends RecyclerView.Adapter<ReclamoAdapter.MyViewHolder>{
    private final Context context;
    private final List<Reclamo> reclamos;
    private final Activity activity;
    private Reclamo reclamo;

    public ReclamoAdapter(Activity activity, Context context, List<Reclamo> reclamos) {
        this.context = context;
        this.reclamos = reclamos;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_reclamo, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (reclamos.size() > 0) {
            reclamo = reclamos.get(position);
            holder.codigoTextView.setText(reclamo.getCodigo());
            holder.asuntoTextView.setText(reclamo.getAsunto());
            holder.descripcionTextView.setText(reclamo.getDescripcion());
            holder.estadoTextView.setText(reclamo.getEstado());
            holder.fechaTextView.setText(reclamo.getFecha().isEmpty()?"":reclamo.getFecha());

            holder.mainLayout.setOnClickListener(v -> {
                System.out.println(v);
                Intent intent = new Intent(context, DetalleReclamo.class);
                intent.putExtra(ReclamoEnun.KEY_NAME.getValue(), reclamo);
                activity.startActivityForResult(intent, 1);
            });
        }
    }

    @Override
    public int getItemCount() {
        return reclamos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  codigoTextView, asuntoTextView, descripcionTextView, estadoTextView, fechaTextView;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            codigoTextView = itemView.findViewById(R.id.textViewCodigo);
            asuntoTextView = itemView.findViewById(R.id.textViewAsunto);
            descripcionTextView = itemView.findViewById(R.id.textViewDescripcion);
            estadoTextView = itemView.findViewById(R.id.textViewEstado);
            fechaTextView = itemView.findViewById(R.id.textViewFecha);
            mainLayout = (LinearLayout) itemView.findViewById(R.id.mainLayout);

            switch (estadoTextView.getText().toString()){
                case "Pendiente":
                    estadoTextView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.yellow));
                    break;
                case "Proceso":
                    estadoTextView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.orange));
                    break;
                case "Completo":
                    estadoTextView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.green));
                    break;
                case "Rechazado":
                    estadoTextView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.red));
                    break;
                default:
                    return;
            }

            Animation teanslate_ani = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(teanslate_ani);
        }
    }
}
