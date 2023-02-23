package com.example.tics.ui.students;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tics.databinding.FragmentStudentsBinding;

import java.util.ArrayList;

public class StudentsFragment extends Fragment {

    private FragmentStudentsBinding binding;
    private StudentsViewModel studentsViewModel;
    private StudentListAdapter studentListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStudentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Create the view model and adapter
        studentsViewModel = new ViewModelProvider(this).get(StudentsViewModel.class);
        studentListAdapter = new StudentListAdapter(new ArrayList<>());

        // Set up the RecyclerView
        RecyclerView recyclerView = binding.StudentsRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(studentListAdapter);

        // Fetch the list of students
        studentsViewModel.fetchStudentsList(getContext());

        // Observe the studentsList LiveData object in the view model and update the UI accordingly
        studentsViewModel.getStudentsList().observe(getViewLifecycleOwner(), students -> {
            studentListAdapter.StudentList = students;
            studentListAdapter.notifyDataSetChanged();
        });

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}