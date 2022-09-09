package com.aman.sqlite.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class EditActivityViewModel extends BaseViewModel {

    private final MutableLiveData<String> nameEditText;
    private final MutableLiveData<String> phoneEditText;

    public EditActivityViewModel(@NonNull Application application) {
        super(application);
        nameEditText = new MutableLiveData<>();
        phoneEditText = new MutableLiveData<>();
    }


    public LiveData<String> getName() {
        return nameEditText;
    }

    public LiveData<String> getPhone() {
        return phoneEditText;
    }

    public void setNameText(String text) {
        nameEditText.setValue(text);
    }

    public void setPhoneText(String text) {
        phoneEditText.setValue(text);
    }

}
