package com.example.tics.ui.game;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tics.IP;
import com.example.tics.R;
import com.example.tics.databinding.FragmentGameBinding;
import com.example.tics.ui.medicalhistory.MedicalHistoryViewModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class GameFragment extends Fragment {

    private FragmentGameBinding binding;
    private AutoCompleteTextView studentIdAutoCompleteTextView;
    private ArrayAdapter<String> studentIdArrayAdapter;
    private MedicalHistoryViewModel viewModel;

    private TextView studentNameTextView;
    private TextView studentParentNameNoTextView;
    private TextView classIdTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        studentIdAutoCompleteTextView = binding.autoCompleteTextView;
        studentNameTextView = binding.GameStudentName;
        studentParentNameNoTextView = binding.GameStudentParentNameNo;
        classIdTextView = binding.GameClassID;

        studentIdArrayAdapter = new ArrayAdapter<>(getContext(),
                R.layout.item_studentid, new ArrayList<>());
        studentIdAutoCompleteTextView.setAdapter(studentIdArrayAdapter);

        viewModel = new ViewModelProvider(requireActivity()).get(MedicalHistoryViewModel.class);
        viewModel.getSelectedStudentId().observe(getViewLifecycleOwner(), this::fetchMedicalHistoryData);

        studentIdAutoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedStudentId = studentIdArrayAdapter.getItem(position);
            viewModel.setSelectedStudentId(selectedStudentId);
        });

        fetchStudentIdData();

        return root;
    }

    private void fetchStudentIdData() {
        String url = IP.BASE_URL+"gamestudentid.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String StudentID = jsonArray.getString(i);
                            studentIdArrayAdapter.add(StudentID);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("Volley", error.toString()));

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void fetchMedicalHistoryData(String StudentID) {
        String url = IP.BASE_URL+"game.php?StudentID=" + StudentID;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}