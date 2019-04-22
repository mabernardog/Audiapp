package com.audiapp.progresiones;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.audiapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GeneradorProgresionesActivity extends AppCompatActivity {
    @Nullable
    @BindView(R.id.toolbar_generadorProgresiones)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generador_progresiones);
        ButterKnife.bind(this);
        // Determinar NavController
        NavController mNavController = Navigation.findNavController(this, R.id.progresiones_host);
        // Hacer que el mToolbar lo autogestione NavigationUI
        AppBarConfiguration mAppBarConfiguration = new AppBarConfiguration.Builder(mNavController.getGraph()).build();
        assert mToolbar != null;
        NavigationUI.setupWithNavController(mToolbar, mNavController, mAppBarConfiguration);
    }

}
