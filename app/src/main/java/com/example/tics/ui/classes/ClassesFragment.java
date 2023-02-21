package com.example.tics.ui.classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.tics.R;
import com.example.tics.databinding.FragmentClassesBinding;

import java.util.ArrayList;
public class ClassesFragment extends Fragment {

    private FragmentClassesBinding binding;
    private ClassesViewModel classesViewModel;
    private ArrayList<String> classesList;
    private AutoCompleteTextView classDropdown;
    private RecyclerView studentRecyclerView;
    private StudentAdapter studentAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentClassesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        classesList = new ArrayList<>();
        classesViewModel = new ViewModelProvider(this).get(ClassesViewModel.class);
        classesViewModel.getClasses(classes -> {
            classesList = new ArrayList<>(classes);
            ArrayAdapter<String> classAdapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_dropdown_item_1line, classesList);
            classDropdown = binding.ClassDropdown;
            classDropdown.setAdapter(classAdapter);
            studentRecyclerView = binding.StudentRecyclerView;

            if (classDropdown != null) {
                classDropdown.setOnItemClickListener((parent, view, pos, id) -> {
                    String selectedClass = (String) parent.getItemAtPosition(pos);
                    classesViewModel.getStudentsForClass(selectedClass, students -> {
                        studentAdapter = new StudentAdapter(getContext(), students, studentId -> {
                            Bundle bundle = new Bundle();
                            bundle.putString("studentId", studentId);
                            Navigation.findNavController(requireView()).navigate(R.id.nav_studentdetails, bundle);
                        });
                        studentRecyclerView.setAdapter(studentAdapter);
                    });
                });
            }
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            studentRecyclerView.setLayoutManager(layoutManager);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}