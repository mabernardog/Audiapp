package com.audiapp.crearprogresion;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.audiapp.R;
import com.audiapp.viewmodels.AppViewModel;

import java.util.Objects;
import java.util.Scanner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReferenciaAcordesDialog extends DialogFragment {
    @Nullable
    @BindView(R.id.toolbar_referenciaAcordes)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.hoja_referenciaAcordes)
    WebView hoja;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AudiappTheme_DialogFullScreen);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar la vista
        View vistaFragmento = inflater.inflate(R.layout.dialog_referencia_acordes, container, false);
        // Bindear Butterknife
        ButterKnife.bind(this, vistaFragmento);
        // Determinar NavController
        NavController mNavController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.general_host);
        // Hacer que el mToolbar lo autogestione NavigationUI
        AppViewModel appViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(AppViewModel.class);
        assert toolbar != null;
        NavigationUI.setupWithNavController(toolbar, mNavController, appViewModel.getAppBarConfiguration());
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        Scanner scanner = new Scanner(getResources().openRawResource(R.raw.referencia_acordes));
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine());
        }

        assert hoja != null;
        hoja.loadData(stringBuilder.toString(), "text/html", "utf-8");

        return vistaFragmento;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
        }
    }
}
