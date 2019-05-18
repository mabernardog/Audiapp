package com.audiapp.viewmodels;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModel;
import androidx.navigation.ui.AppBarConfiguration;

import com.audiapp.R;
import com.audiapp.modelo.TipoUsuario;

import java.util.HashSet;
import java.util.Set;

public class AppViewModel extends ViewModel {
    private TipoUsuario mTipoAcceso;
    private AppBarConfiguration mAppBarConfiguration;

    public AppBarConfiguration getAppBarConfiguration() {
        return mAppBarConfiguration;
    }

    public void setAppBarConfiguration(DrawerLayout drawerLayout) {
        Set<Integer> topDestinations = new HashSet<>();
        topDestinations.add(R.id.inicialActivity);
        topDestinations.add(R.id.elegirInicioFragment);
        topDestinations.add(R.id.elegirModuloFragment);
        this.mAppBarConfiguration = new AppBarConfiguration.Builder(topDestinations).setDrawerLayout(drawerLayout).build();
    }

    public void setAppBarConfiguration() {
        Set<Integer> topDestinations = new HashSet<>();
        topDestinations.add(R.id.inicialActivity);
        topDestinations.add(R.id.elegirInicioFragment);
        topDestinations.add(R.id.elegirModuloFragment);
        this.mAppBarConfiguration = new AppBarConfiguration.Builder(topDestinations).build();
    }

    public TipoUsuario getTipoAcceso() {
        return mTipoAcceso;
    }

    public void setTipoAcceso(TipoUsuario tipoAcceso) {
        this.mTipoAcceso = tipoAcceso;
    }
}
