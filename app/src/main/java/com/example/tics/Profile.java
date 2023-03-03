package com.example.tics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {
    ImageButton completebutton;
    AutoCompleteTextView classinput;
    TextView username;
    EditText emailinput;

    private List<String> classNames;
    private List<String> classIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        completebutton = findViewById(R.id.Profile_Complete);
        classinput = findViewById(R.id.Profile_ClassInput);
        username = findViewById(R.id.Profile_Name);
        emailinput = findViewById(R.id.Profile_Email);

        Intent receiveIntent = getIntent();
        String receivedValue = receiveIntent.getStringExtra("username");
        username.setText(receivedValue);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = IP.BASE_URL+"class.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonArray = jsonResponse.getJSONArray("class");
                            classNames = new ArrayList<>();
                            classIds = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                classNames.add(obj.getString("ClassName"));
                                classIds.add(obj.getString("ClassID"));
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(Profile.this,
                                    R.layout.item_classid, classNames);
                            classinput.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Profile.this, "Failed to get classes", Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        completebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = receivedValue;
                String email = emailinput.getText().toString();
                String selectedClassName = classinput.getText().toString();
                String password = "";
                String url = IP.BASE_URL+"profile.php";
                String type = "update";
                // Get the ID for the selected class
                String selectedClassId = classIds.get(classNames.indexOf(selectedClassName));
                BackgroundWorker backgroundWorker = new BackgroundWorker(Profile.this);
                backgroundWorker.execute(url, type, username, password, email, selectedClassId);
                Intent intent = new Intent(Profile.this, MainActivity.class);
                startActivity(intent);
                Intent senderIntent = new Intent(getApplicationContext(), MainActivity.class);
                senderIntent.putExtra("PROF_SENDER", receivedValue);
                startActivity(senderIntent);
            }
        });
    }
}

