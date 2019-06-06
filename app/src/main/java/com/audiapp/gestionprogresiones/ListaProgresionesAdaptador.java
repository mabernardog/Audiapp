package com.audiapp.gestionprogresiones;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.audiapp.R;
import com.audiapp.modelo.progresiones.ProgresionArmonica;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ListaProgresionesAdaptador extends RecyclerView.Adapter<ListaProgresionesAdaptador.ListaProgresionesVH> {
    private ArrayList<ProgresionArmonica> lista;
    private OnProgresionClickListener mListener;

    public ListaProgresionesAdaptador(ArrayList<ProgresionArmonica> lista, OnProgresionClickListener listener) {
        this.lista = lista;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ListaProgresionesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListaProgresionesVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_progresion, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListaProgresionesVH holder, int position) {
        ProgresionArmonica p = lista.get(position);
        holder.listenerProgresion = mListener;
        holder.nombreProgresion.setText(p.getNombre());
        holder.numAcordesProgresion.setText(Integer.toString(p.getAcordeArrayList().size()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.US);
        holder.fechaProgresion.setText(dateFormat.format(p.getFecha()));
    }

    @Override
    public int getItemCount() {
        return lista == null ? 0 : lista.size();
    }

    public interface OnProgresionClickListener {
        void onProgresionClick(int pos);
    }

    static class ListaProgresionesVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnProgresionClickListener listenerProgresion;
        TextView nombreProgresion;
        TextView numAcordesProgresion;
        TextView fechaProgresion;

        ListaProgresionesVH(@NonNull View itemView) {
            super(itemView);
            nombreProgresion = itemView.findViewById(R.id.textView_nombreProgCV);
            numAcordesProgresion = itemView.findViewById(R.id.textView_numAcordesProgCV);
            fechaProgresion = itemView.findViewById(R.id.textView_fechaProgCV);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listenerProgresion.onProgresionClick(getAdapterPosition());
        }
    }
}
