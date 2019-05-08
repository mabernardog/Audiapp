package com.audiapp.progresiones;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.audiapp.R;
import com.audiapp.modelo.Tempo;

import butterknife.BindView;
import butterknife.ButterKnife;


class TempoProgresionTonalFragment extends Fragment {
    @Nullable
    @BindView(R.id.rbtnG_tempo)
    RadioGroup mRadioGroup;
    @Nullable
    @BindView(R.id.btn_eleccion_tempo)
    ToggleButton mToggleButton;
    @Nullable
    @BindView(R.id.edit_tempo)
    EditText mEditText;
    private ProgresionTonalViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getParentFragment() != null;
        assert getParentFragment().getParentFragment() != null;
        mViewModel = ViewModelProviders.of(getParentFragment().getParentFragment()).get(ProgresionTonalViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar la vista
        View vistaFragmento = inflater.inflate(R.layout.fragment_tempo_progresion_tonal, container, false);
        ButterKnife.bind(this, vistaFragmento);
        assert mRadioGroup != null;
        for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
            mRadioGroup.getChildAt(i).setEnabled(false);
        }
        assert mEditText != null;
        if (mViewModel.getTempo() != null) {
            mEditText.setText(Integer.toString(mViewModel.getTempo().getPpm()));
            for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
                if (((RadioButton) mRadioGroup.getChildAt(i)).getText().equals(mViewModel.getTempo().getClasificacion()))
                    ((RadioButton) mRadioGroup.getChildAt(i)).setChecked(true);
            }
        }

        // Definir listener para el ToggleButton
        assert mToggleButton != null;
        mToggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Modificar el RadioGroup
            for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
                // Si el botón estará activo
                if (isChecked) {
                    mRadioGroup.getChildAt(i).setEnabled(true);
                } else {
                    mRadioGroup.getChildAt(i).setEnabled(false);
                }
            }
            // Modificar el EditText y gestionar el RadioGroup
            if (isChecked) {
                // Elegir el RadioButton apropiado si hay texto (evitar nulos)
                if (!mEditText.getText().toString().equals("")) {
                    Tempo t = new Tempo(Integer.parseInt(mEditText.getText().toString()));
                    for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
                        if (((RadioButton) mRadioGroup.getChildAt(i)).getText().equals(t.getClasificacion()))
                            ((RadioButton) mRadioGroup.getChildAt(i)).setChecked(true);
                    }
                }
                // Desactivar el edit
                mEditText.setEnabled(false);
                //
            } else {
                // Activar el edit
                mEditText.setEnabled(true);
                RadioButton btn = mRadioGroup.findViewById(mRadioGroup.getCheckedRadioButtonId());
                if (btn != null) {
                    Tempo t = new Tempo(btn.getText().toString());
                    mEditText.setText(Integer.toString(t.getPpm()));
                }
            }
        });
        // Listener para el RadioGroup
        mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // Añadir el tempo al ViewModel
            RadioButton btn = mRadioGroup.findViewById(checkedId);
            mViewModel.setTempo(new Tempo(btn.getText().toString()));
            mEditText.setText(Integer.toString(mViewModel.getTempo().getPpm()));
        });
        // Listener para el EditText
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) mViewModel.setTempo(null);
                else mViewModel.setTempo(new Tempo(Integer.parseInt(s.toString())));
            }
        });
        return vistaFragmento;
    }

}
