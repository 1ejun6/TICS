package com.example.tics.ui.medicalhistory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MedicalHistoryModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MedicalHistoryModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is medical history fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}