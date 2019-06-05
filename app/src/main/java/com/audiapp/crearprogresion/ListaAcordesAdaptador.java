package com.audiapp.crearprogresion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.audiapp.R;
import com.audiapp.modelo.progresiones.Acorde;

import java.util.ArrayList;

public class ListaAcordesAdaptador extends RecyclerView.Adapter<ListaAcordesAdaptador.ListaAcordesViewHolder> {
    private ArrayList<AcordeView> mSetDatos;
    private Context mContext;

    public ListaAcordesAdaptador(ArrayList<AcordeView> setDatos, Context context) {
        if (setDatos == null) {
            mSetDatos = new ArrayList<>();
        } else {
            mSetDatos = setDatos;
        }
        mContext = context;
    }

    @NonNull
    @Override
    public ListaAcordesAdaptador.ListaAcordesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(mContext).inflate(R.layout.card_acorde, parent, false);
        return new ListaAcordesViewHolder(vista);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaAcordesViewHolder holder, int position) {
        // Coger el acorde de la tarjeta y meter en él el que se bindea
        CardView tarjeta = (CardView) holder.cardView;
        AcordeView acordeDeTarjeta = null;
        acordeDeTarjeta = (AcordeView) ((LinearLayout) tarjeta.getChildAt(0)).getChildAt(0);
        acordeDeTarjeta = mSetDatos.get(position);
    }

    @Override
    public int getItemCount() {
        return mSetDatos.size();
    }

    public void insertarNuevo() {
        mSetDatos.add(new AcordeView(mContext, null));
        Acorde ac = mSetDatos.get(0).getAcorde();
        notifyItemInserted(mSetDatos.size() - 1);
    }

    public static class ListaAcordesViewHolder extends RecyclerView.ViewHolder {
        public View cardView;

        public ListaAcordesViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView;
        }
    }
}
