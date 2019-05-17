package com.audiapp.inicial;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.audiapp.R;
import com.audiapp.viewmodels.AppViewModel;

public class MainActivity extends AppCompatActivity {
    private AppViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
    }
}
