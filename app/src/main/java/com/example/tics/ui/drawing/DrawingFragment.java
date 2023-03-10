package com.example.tics.ui.drawing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tics.databinding.FragmentDrawingBinding;

public class DrawingFragment extends Fragment {

    private FragmentDrawingBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        DrawingViewModel drawingViewModel = new ViewModelProvider(this)
                .get(DrawingViewModel.class);

        binding = FragmentDrawingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}