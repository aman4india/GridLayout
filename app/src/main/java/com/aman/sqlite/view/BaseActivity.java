package com.aman.sqlite.view;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.aman.sqlite.viewmodel.BaseViewModel;

public abstract class BaseActivity<BINDING extends ViewBinding, VM extends BaseViewModel> extends AppCompatActivity {

    protected VM viewModel;
    protected BINDING binding;

    @NonNull
    protected abstract VM createViewModel();

    @NonNull
    protected abstract BINDING createViewBinding(LayoutInflater layoutInflater);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = createViewBinding(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        viewModel = createViewModel();
    }


}
