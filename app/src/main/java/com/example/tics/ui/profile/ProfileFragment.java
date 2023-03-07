package com.example.tics.ui.profile;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tics.IP;
import com.example.tics.R;
import com.example.tics.databinding.FragmentProfileBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("tics", MODE_PRIVATE);
        String logged = sharedPreferences.getString("logged", "");
        if (logged.equals("true")) {
            String teacherName = sharedPreferences.getString("TeacherName", "");
            String apikey = sharedPreferences.getString("apikey", "");
            Log.d("ProfileFragment", "Teacher name: " + teacherName);
            Log.d("ProfileFragment", "API key: " + apikey);

            // get teacher information
            String url = IP.BASE_URL+"teacher.php?TeacherName=" + teacherName;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // get teacher information from response
                                int teacherID = response.getInt("TeacherID");
                                String teacherName = response.getString("TeacherName");
                                String teacherEmail = response.getString("TeacherEmail");
                                String teacherPassword = response.getString("TeacherPassword");
                                int classID = response.getInt("ClassID");

                                // display teacher information
                                TextView teacherIdTextView = root.findViewById(R.id.Teacher_ID);
                                teacherIdTextView.setText(String.valueOf(teacherID));

                                TextView teacherNameTextView = root.findViewById(R.id.Teacher_Username);
                                teacherNameTextView.setText(teacherName);

                                TextView teacherEmailTextView = root.findViewById(R.id.Teacher_Email);
                                teacherEmailTextView.setText(teacherEmail);

                                TextView teacherPasswordTextView = root.findViewById(R.id.Teacher_Password);
                                teacherPasswordTextView.setText(teacherPassword);

                                TextView classIdTextView = root.findViewById(R.id.Teacher_ClassID);
                                classIdTextView.setText(String.valueOf(classID));
                            } catch (JSONException e) {
                                Log.e("ProfileFragment", "JSONException: " + e.getMessage());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("ProfileFragment", "VolleyError: " + error.getMessage());
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("apikey", apikey);
                    return headers;
                }
            };

            // add request to request queue
            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
            requestQueue.add(jsonObjectRequest);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
