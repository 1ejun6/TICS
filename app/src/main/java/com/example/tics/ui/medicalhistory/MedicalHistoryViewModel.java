package com.example.tics.ui.medicalhistory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MedicalHistoryViewModel extends ViewModel {
    private MutableLiveData<String> selectedStudentId = new MutableLiveData<>();

    public LiveData<String> getSelectedStudentId() {
        return selectedStudentId;
    }

    public void setSelectedStudentId(String studentId) {
        selectedStudentId.setValue(studentId);
    }
}
