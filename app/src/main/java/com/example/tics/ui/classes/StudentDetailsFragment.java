package com.example.tics.ui.classes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tics.R;
import com.example.tics.databinding.FragmentStudentDetailsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StudentDetailsFragment extends Fragment {
    private FragmentStudentDetailsBinding binding;
    private ClassesViewModel classesViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStudentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // retrieve studentId from arguments
        String studentId = getArguments().getString("studentId");
        Log.d("StudentID", studentId);

        // initialize ViewModel
        classesViewModel = new ViewModelProvider(requireActivity()).get(ClassesViewModel.class);

        // get student details using studentId
        classesViewModel.getStudentDetails(Integer.parseInt(studentId), student -> {
            if (student != null) {
                TextView StudentDetailsIdTextView = binding.StudentDetailsStudentID;
                StudentDetailsIdTextView.setText(String.valueOf(student.getStudentID()));

                TextView StudentDetailsNameTextView = binding.StudentDetailsStudentName;
                StudentDetailsNameTextView.setText(student.getStudentName());

                TextView StudentDetailsParentNameTextView = binding.studentDetailsStudentParentDetails;
                StudentDetailsParentNameTextView.setText(student.getStudentParentName()+"-  "+student.getStudentParentNo());
            }
        });

        classesViewModel.getTicCount(Integer.parseInt(studentId), ticCount -> {
            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            TextView studentDetailsTicCountTextView = binding.StudentDetailsTicCount;
            TextView studentDetailsDateTextView = binding.StudentDetailsDate;
            if (ticCount != null) {
                studentDetailsTicCountTextView.setText(String.valueOf(ticCount));
                studentDetailsDateTextView.setText(currentDate);

                if (ticCount == 0) {
                    studentDetailsDateTextView.setText(currentDate);
                }
            } else {
                // handle case where ticCount is null
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}