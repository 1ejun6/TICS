package com.example.tics;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignIn extends AppCompatActivity {
    ImageButton button;
    EditText editTextUsername;
    EditText editTextPassword;
    String username, password, apikey;
    TextView textViewError;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        button = findViewById(R.id.SignIn_SignIn);
        editTextUsername = findViewById(R.id.SignIn_Name);
        editTextPassword = findViewById(R.id.SignIn_Password);

        editTextUsername.addTextChangedListener(input_watcher);
        editTextPassword.addTextChangedListener(input_watcher);

        textViewError = findViewById(R.id.error);
        progressBar = findViewById(R.id.loading);

        sharedPreferences = getSharedPreferences("tics", MODE_PRIVATE);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                textViewError.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                username = String.valueOf(editTextUsername.getText());
                password = String.valueOf(editTextPassword.getText());
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://192.168.1.14/signin.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(View.GONE);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.getString("status");
                                    String message = jsonObject.getString("message");
                                    username = jsonObject.getString("TeacherName");
                                    apikey = jsonObject.getString("apikey");
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("logged", "true");
                                    editor.putString("TeacherName", username);
                                    editor.putString("apikey", apikey);
                                    editor.apply();
                                    Intent senderIntent = new Intent(getApplicationContext(), MainActivity.class);
                                    senderIntent.putExtra("username", username);
                                    startActivity(senderIntent);
                                    finish();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        textViewError.setVisibility(View.VISIBLE);
                    }
                }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("TeacherName", username);
                        paramV.put("TeacherPassword", password);
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }

    private TextWatcher input_watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            String userNameInput = editTextUsername.getText().toString();
            String passwordInput = editTextPassword.getText().toString();

            button.setEnabled(!userNameInput.isEmpty() && !passwordInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}