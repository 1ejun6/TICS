package com.example.tics.ui.classes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tics.R;
import com.example.tics.databinding.FragmentStudentDetailsBinding;


public class StudentDetailsFragment extends Fragment {

    private FragmentStudentDetailsBinding binding;
    private ClassesViewModel classesViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStudentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // retrieve studentId from arguments
        int StudentID = getArguments().getInt("StudentID");

        Log.d("StudentID", String.valueOf(StudentID));

        // initialize ViewModel
        classesViewModel = new ViewModelProvider(requireActivity()).get(ClassesViewModel.class);

        // get student details using studentId
        classesViewModel.getStudentDetails(StudentID, student -> {
            if(student != null){
                TextView studentIdTextView = binding.StudentStudentID;
                studentIdTextView.setText(student.getStudentID());

                TextView studentNameTextView = binding.StudentStudentName;
                studentNameTextView.setText(student.getStudentName());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
