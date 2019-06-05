package com.audiapp.crearprogresion;

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
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.audiapp.R;
import com.audiapp.midigen.MidiGen;
import com.audiapp.modelo.progresiones.ProgresionArmonica;
import com.audiapp.modelo.progresiones.Tempo;
import com.audiapp.viewmodels.AppViewModel;
import com.audiapp.viewmodels.CrearProgresionViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


class CrearProgresionTempoFragment extends Fragment {
    @Nullable
    @BindView(R.id.toolbar_crearProgresionTempo)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.rbtnG_tempo)
    RadioGroup mRadioGroup;
    @Nullable
    @BindView(R.id.btn_eleccion_tempo)
    ToggleButton mToggleButton;
    @Nullable
    @BindView(R.id.edit_tempo)
    EditText mEditText;
    @Nullable
    @BindView(R.id.fabCrearProgresion)
    FloatingActionButton mFab;

    private CrearProgresionViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(CrearProgresionViewModel.class);
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
        View vistaFragmento = inflater.inflate(R.layout.fragment_crear_progresion_tempo, container, false);
        ButterKnife.bind(this, vistaFragmento);
        // Determinar NavController
        NavController mNavController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.general_host);
        // Hacer que el mToolbar lo autogestione NavigationUI
        AppViewModel appViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(AppViewModel.class);
        assert toolbar != null;
        NavigationUI.setupWithNavController(toolbar, mNavController, appViewModel.getAppBarConfiguration());

        assert mRadioGroup != null;
        for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
            mRadioGroup.getChildAt(i).setEnabled(false);
        }
        assert mEditText != null;
        if (mViewModel.getProgresion().getTempo() != null) {
            mEditText.setText(Integer.toString(mViewModel.getProgresion().getTempo().getPpm()));
            for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
                if (((RadioButton) mRadioGroup.getChildAt(i)).getText().equals(mViewModel.getProgresion().getTempo().getClasificacion()))
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
            mViewModel.getProgresion().setTempo(new Tempo(btn.getText().toString()));
            mEditText.setText(Integer.toString(mViewModel.getProgresion().getTempo().getPpm()));
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
                if (s.toString().equals("")) mViewModel.getProgresion().setTempo(null);
                else mViewModel.getProgresion().setTempo(new Tempo(Integer.parseInt(s.toString())));
            }
        });


        // Configurar el FAB
        assert mFab != null;
        mFab.setOnClickListener(v -> {
            // La lista de acordes está ya validada: validar que haya tempo
            ProgresionArmonica progresion = mViewModel.getProgresion();
            if (progresion.getTempo() == null) {
                Toast toast = Toast.makeText(getContext(), R.string.toast_corregir_tempo_crear_progresion, Toast.LENGTH_SHORT);
                toast.show(); // Mostrar toast de error si falta el tempo
            } else {
                File archivo = new File(Objects.requireNonNull(getContext()).getFilesDir(), "progpreview.mid");
                /*if (archivo.exists()) {
                    boolean delted = archivo.delete();
                    try {
                        archivo.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }*/
                MidiGen.generarProgresion(progresion, archivo.getPath());
                mViewModel.getProgresion().setArchivo(archivo.getPath());
                mNavController.navigate(R.id.action_crearProgresionTempo_to_reproductorProgresionCreada);
            }
        });
        return vistaFragmento;
    }

}
