package com.audiapp.inicial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.audiapp.Audiapp;
import com.audiapp.R;
import com.audiapp.db.GestorDB;
import com.audiapp.db.GestorUsuarioDB;
import com.audiapp.modelo.TipoUsuario;
import com.audiapp.modelo.Usuario;
import com.audiapp.viewmodels.AppViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

class ElegirModuloFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    @Nullable
    @BindView(R.id.button_goCrearProgresion)
    Button botonCrear;
    @Nullable
    @BindView(R.id.button_goAdivinar)
    Button botonAdivinar;
    @Nullable
    @BindView(R.id.toolbar_elegirModulo)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Nullable
    @BindView(R.id.navigation_drawer)
    NavigationView navigationDrawer;
    private AppViewModel mAppViewModel;
    private NavController mNavController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getParentFragment() != null;
        assert getParentFragment().getParentFragment() != null;
        mAppViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(AppViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar la vista
        View vistaFragmento = inflater.inflate(R.layout.fragment_elegir_modulo, container, false);
        // Bindear Butterknife
        ButterKnife.bind(this, vistaFragmento);
        // Determinar NavController
        mNavController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.general_host);
        // Hacer que el mToolbar y el drawer lo autogestione NavigationUI
        mAppViewModel.setAppBarConfiguration(drawerLayout);
        assert toolbar != null && navigationDrawer != null;
        NavigationUI.setupWithNavController(toolbar, mNavController, mAppViewModel.getAppBarConfiguration());
        NavigationUI.setupWithNavController((NavigationView) vistaFragmento.findViewById(R.id.navigation_drawer), mNavController);
        // Determinar el tipo de usuario
        TipoUsuario tipoUsuario = mAppViewModel.getTipoAcceso();
        // Configurar el drawer
        navigationDrawer.setNavigationItemSelectedListener(this);
        ConstraintLayout header = (ConstraintLayout) navigationDrawer.getHeaderView(0);
        // Si el usuario es anónimo, deshabilitar menús (menos el último) y cambiar el texto del último
        if (tipoUsuario == TipoUsuario.ANONIMO) {
            Menu menu = navigationDrawer.getMenu();
            for (int i = 0; i < menu.size() - 1; i++) {
                navigationDrawer.getMenu().getItem(i).setEnabled(false);
            }
            menu.getItem(menu.size() - 1).setTitle("Volver a la elección de inicio");
        } else {
            List<Usuario> listaUsuarios = ((GestorUsuarioDB) Objects.requireNonNull(new GestorDB().acceder("Usuario"))).leerTodos();
            Usuario usuario = Objects.requireNonNull(listaUsuarios).get(0);
            TextView textView = (TextView) header.getChildAt(1);
            textView.setText(usuario.getNick());
        }
        // Añadir navegación
        assert botonCrear != null;
        botonCrear.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_elegirModulo_to_crearProgresion));
        assert botonAdivinar != null;
        botonAdivinar.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_elegirModulo_to_modoEleccionEjercicio));
        if (tipoUsuario == TipoUsuario.ANONIMO) {
            botonAdivinar.setEnabled(false);
        }
        return vistaFragmento;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        Objects.requireNonNull(drawerLayout).closeDrawers();

        switch (item.getItemId()) {
            case R.id.drawer_mostrar_perfil:
                break;
            case R.id.drawer_mostrar_progresiones:
                break;
            case R.id.drawer_mostrar_estadisticas:
                break;
            case R.id.drawer_logout:
                if (mAppViewModel.getTipoAcceso() == TipoUsuario.ANONIMO) {
                    // Salir a ElegirInicioFragment
                    mNavController.navigate(R.id.elegirInicioFragment);
                } else {
                    MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(Objects.requireNonNull(getContext()));
                    dialogBuilder.setTitle("Cerrar sesión");
                    dialogBuilder.setMessage("¿Deseas cerrar la sesión?");
                    dialogBuilder.setPositiveButton(R.string.text_si, (dialog, which) -> {
                        item.setChecked(false);
                        dialog.dismiss();
                        // Resetear retrofit y navegar a ElegirInicioFragment
                        Audiapp.getInstancia().resetRetroAudiappFit();
                        mNavController.navigate(R.id.elegirInicioFragment);
                    });
                    dialogBuilder.setNegativeButton(R.string.text_no, (dialog, which) -> {
                        item.setChecked(false);
                        dialog.dismiss();
                    });
                    dialogBuilder.show();
                }
                break;
        }
        return true;
    }
}
