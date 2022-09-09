package com.aman.sqlite.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MainViewModel extends BaseViewModel {
    private final MutableLiveData<String> nameEditText;
    private final MutableLiveData<String> phoneEditText;
    public MainViewModel(@androidx.annotation.NonNull Application application) {
        super(application);
        nameEditText = new MutableLiveData<>();
        phoneEditText = new MutableLiveData<>();
        nameEditText.setValue("");
        phoneEditText.setValue("");
    }

    public LiveData<String> getName() {
        return nameEditText;
    }

    public LiveData<String> getPhone() {
        return phoneEditText;
    }
}
