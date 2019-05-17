package com.audiapp.inicial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.audiapp.R;
import com.audiapp.modelo.TipoUsuario;
import com.audiapp.viewmodels.AppViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ElegirInicioFragment extends Fragment {
    @Nullable
    @BindView(R.id.button_goLogin)
    MaterialButton botonLogin;
    @Nullable
    @BindView(R.id.button_goRegistro)
    MaterialButton botonRegistro;
    @Nullable
    @BindView(R.id.button_goAnonimo)
    MaterialButton botonAnonimo;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vistaFragmento = inflater.inflate(R.layout.fragment_elegir_inicio, container, false);
        ButterKnife.bind(this, vistaFragmento);
        assert botonLogin != null;
        botonLogin.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_elegirInicio_to_login));
        assert botonRegistro != null;
        botonRegistro.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_elegirInicio_to_registro));
        assert botonAnonimo != null;
        botonAnonimo.setOnClickListener(v -> {
            // Actualizar el AppViewModel y navegar
            ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(AppViewModel.class).setTipoAcceso(TipoUsuario.ANONIMO);
            Navigation.findNavController(Objects.requireNonNull(this.getView())).navigate(R.id.action_elegirInicio_to_funcionalidadApp);
        });
        return vistaFragmento;
    }
}
