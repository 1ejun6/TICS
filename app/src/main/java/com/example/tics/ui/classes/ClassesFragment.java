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

import com.example.tics.databinding.FragmentClassesBinding;
import com.example.tics.databinding.FragmentGameBinding;

import java.util.ArrayList;

public class ClassesFragment extends Fragment {

    private FragmentClassesBinding binding;
    private ClassesViewModel classesViewModel;
    private ArrayList<String> classesList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentClassesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        classesList = new ArrayList<>();
        classesViewModel = new ViewModelProvider(this).get(ClassesViewModel.class);
        classesViewModel.getClasses(classes -> {
            classesList = new ArrayList<>(classes);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_dropdown_item_1line, classesList);
            AutoCompleteTextView autoCompleteTextView = binding.ClassDropdown;
            autoCompleteTextView.setAdapter(adapter);
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
