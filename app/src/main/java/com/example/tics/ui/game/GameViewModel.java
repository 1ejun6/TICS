package com.example.tics.ui.game;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameViewModel extends ViewModel {
    private MutableLiveData<String> selectedStudentId = new MutableLiveData<>();

    public void setSelectedStudentId(String studentId) {
        selectedStudentId.setValue(studentId);
    }
}