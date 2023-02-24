package com.example.tics.ui.medicalhistory;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tics.R;
import com.example.tics.Student;
import com.example.tics.databinding.FragmentMedicalhistoryBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MedicalHistoryFragment extends Fragment {

    private FragmentMedicalhistoryBinding binding;
    private AutoCompleteTextView studentIdAutoCompleteTextView;
    private ArrayAdapter<String> studentIdArrayAdapter;
    private MedicalHistoryViewModel viewModel;

    private TextView studentNameTextView;
    private TextView studentParentNameNoTextView;
    private TextView classIdTextView;
    private TextView medicalHistoryDescriptionTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMedicalhistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        studentIdAutoCompleteTextView = binding.autoCompleteTextView;
        studentNameTextView = binding.MedicalHistoryStudentName;
        studentParentNameNoTextView = binding.MedicalHistoryStudentParentNameNo;
        classIdTextView = binding.MedicalHistoryClassID;
        medicalHistoryDescriptionTextView = binding.MedicalHistoryDescription;

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
        String url = "http://192.168.1.14/medicalhistorystudentid.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String studentId = jsonArray.getString(i);
                            studentIdArrayAdapter.add(studentId);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("Volley", error.toString()));

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private void fetchMedicalHistoryData(String studentId) {
        String url = "http://192.168.1.14/medicalhistory.php?student_id=" + studentId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String medicalHistoryDescription = jsonObject.getString("MedicalHistoryDesc");
                        String studentName = jsonObject.getString("StudentName");
                        String studentParentName = jsonObject.getString("StudentParentName");
                        String studentParentNo = jsonObject.getString("StudentParentNo");
                        String classId = jsonObject.getString("ClassID");

                        medicalHistoryDescriptionTextView.setText(medicalHistoryDescription);
                        studentNameTextView.setText(studentName);
                        studentParentNameNoTextView.setText(studentParentName + " - " + studentParentNo);
                        classIdTextView.setText(classId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("Volley", error.toString()));

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
