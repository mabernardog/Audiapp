package com.audiapp.inicial;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.audiapp.R;

public class MainActivity extends AppCompatActivity {
    //AppViewModel mViewModel;  DESCOMENTAR SI HAY PROBLEMAS CON ESTE VIEWMODEL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
    }
}
