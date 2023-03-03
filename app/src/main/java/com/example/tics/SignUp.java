package com.example.tics;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {
    ImageButton button;
    EditText editUsername;
    EditText editPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        editUsername=findViewById(R.id.SignUp_Name);
        editPassword=findViewById(R.id.SignUp_Password);
        button =(ImageButton) findViewById(R.id.SignUp_SignUp);

        editUsername.addTextChangedListener(input_watcher);
        editPassword.addTextChangedListener(input_watcher);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();
                String email= "";
                String classID= "";
                String url = IP.BASE_URL+"signup.php";
                String type = "signup";
                BackgroundWorker backgroundWorker = new BackgroundWorker(SignUp.this);
                backgroundWorker.execute(url, type, username, password, email, classID);
                success();
            }
        });
    }

    private void success() {
        Intent intent = new Intent(SignUp.this, Success.class);
        startActivity(intent);
    }

    private TextWatcher input_watcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            String userNameInput= editUsername.getText().toString();
            String passwordInput= editPassword.getText().toString();

            button.setEnabled(!userNameInput.isEmpty() && !passwordInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}