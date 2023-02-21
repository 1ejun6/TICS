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

import com.android.volley.AuthFailureError;
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
                RequestQueue queue = Volley.newRequestQueue(SignIn.this);
                String url = "https://example.com/api/signin";
                StringRequest request = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean("success")) {
                                        apikey = jsonObject.getString("apikey");
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("apikey", apikey);
                                        editor.apply();
                                        startActivity(new Intent(SignIn.this, MainActivity.class));
                                        finish();
                                    } else {
                                        textViewError.setText(jsonObject.getString("message"));
                                        textViewError.setVisibility(View.VISIBLE);
                                    }
                                } catch (JSONException e) {
                                    textViewError.setText(R.string.error_while_logging_in);
                                    textViewError.setVisibility(View.VISIBLE);
                                    e.printStackTrace();
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                textViewError.setText(R.string.error_while_logging_in);
                                textViewError.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", username);
                        params.put("password", password);
                        return params;
                    }
                };
                queue.add(request);
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