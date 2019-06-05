package com.audiapp.crearprogresion;


import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.audiapp.R;
import com.audiapp.modelo.progresiones.Acorde;

public class AcordeView extends LinearLayout {
    private final EditText mAcorde;
    private final Spinner mSpinner;
    private String mFigura;

    public AcordeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflador.inflate(R.layout.view_acorde, this, true);
        // Inicializar los handlers
        mAcorde = (EditText) getChildAt(0);
        mSpinner = (Spinner) getChildAt(1);
        // Inicializar el spinner
        mSpinner.setSelection(2); // Inicializar siempre a negra
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mFigura = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nada
            }
        });
    }

    public void setError() {
        mAcorde.setError("Acorde inválido");
    }


    public Acorde getAcorde() {
        try {
            return new Acorde(mAcorde.getText().toString(), mFigura);
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setAcorde(Acorde acorde) {
        this.mAcorde.setText(acorde.getCadena());
        switch (acorde.getFigura()) {
            case "Semicor.":
                this.mSpinner.setSelection(0);
                break;
            case "Corchea":
                this.mSpinner.setSelection(1);
                break;
            case "Negra":
                this.mSpinner.setSelection(2);
                break;
            case "Blanca":
                this.mSpinner.setSelection(3);
                break;
            case "Redonda":
                this.mSpinner.setSelection(4);
                break;
            default:
                this.mSpinner.setSelected(false);
                break;
        }
    }
}
