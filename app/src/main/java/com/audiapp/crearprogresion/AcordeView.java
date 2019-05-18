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
import com.audiapp.modelo.Acorde;

public class AcordeView extends LinearLayout {
    private EditText mAcorde;
    private String mFigura;
    private Spinner mSpinner;

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


    public Acorde getAcorde() {
        //String str = mAcorde.getText().toString();
        return new Acorde(/*mAcorde.getText().toString()*/"", mFigura);
    }
}
