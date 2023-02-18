package com.example.tics.ui.medicalhistory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tics.databinding.FragmentMedicalhistoryBinding;

public class MedicalHistoryFragment extends Fragment {

    private FragmentMedicalhistoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MedicalHistoryModel medicalHistoryModel =
                new ViewModelProvider(this).get(MedicalHistoryModel.class);

        binding = FragmentMedicalhistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMedicalhistory;
        medicalHistoryModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}