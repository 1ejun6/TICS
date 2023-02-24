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

import java.util.ArrayList;

public class MedicalHistoryFragment extends Fragment {

    private FragmentMedicalhistoryBinding binding;
    private AutoCompleteTextView StudentID;
    private ArrayAdapter<String> mArrayAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMedicalhistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        StudentID = binding.autoCompleteTextView;

        mArrayAdapter = new ArrayAdapter<>(getContext(),
                R.layout.item_studentid, new ArrayList<String>());

        StudentID.setAdapter(mArrayAdapter);

        // Fetch data from server
        String url = "http://192.168.1.14/medicalhistorystudentid.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String studentId = jsonArray.getString(i);
                                mArrayAdapter.add(studentId);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        });

        Volley.newRequestQueue(getContext()).add(stringRequest);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
