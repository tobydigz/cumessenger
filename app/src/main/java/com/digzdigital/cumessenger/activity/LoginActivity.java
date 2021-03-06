package com.digzdigital.cumessenger.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import com.digzdigital.cumessenger.R;
import com.digzdigital.cumessenger.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener<AuthResult> {

    private ActivityLoginBinding binding;
    private FirebaseAuth auth;
    private String id = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        auth = FirebaseAuth.getInstance();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        if (getIntent()!=null){
            Intent intent = getIntent();
            String email = intent.getStringExtra("email");
            String password = intent.getStringExtra("password");
            id = intent.getStringExtra("id");

            binding.email.setText(email);
            binding.password.setText(password);
        }
        binding.btnLogin.setOnClickListener(this);
        binding.btnResetPassword.setOnClickListener(this);
        binding.btnSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_reset_password:
                switchActivity(ResetPasswordActivity.class);
                break;
            case R.id.btn_signup:
                switchActivity(RegisterActivity.class);
                break;
        }
    }

    private void login() {
        String emailText = binding.email.getText().toString().trim();
        String passwordText = binding.password.getText().toString().trim();
        if (!validate(emailText, passwordText)) return;
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnLogin.setEnabled(false);
        auth.signInWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(this, this);
    }

    private boolean validate(String email, String password) {
        boolean state = true;

        if (TextUtils.isEmpty(email)) {
            Snackbar.make(binding.activityLogin, "Enter email address!", Snackbar.LENGTH_SHORT).show();
            binding.email.setError("Enter email address");
            state = false;
        } else binding.email.setError(null);


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Snackbar.make(binding.activityLogin, "Enter a valid email address!", Snackbar.LENGTH_SHORT).show();
            binding.email.setError("Enter a valid email address!");
            state = false;
        } else binding.email.setError(null);


        if (TextUtils.isEmpty(password)) {
            Snackbar.make(binding.activityLogin, "Enter password", Snackbar.LENGTH_SHORT).show();
            binding.password.setError("Enter password");
            state = false;
        } else binding.password.setError(null);


        if (password.length() < 6) {
            Snackbar.make(binding.activityLogin, R.string.pass_short, Snackbar.LENGTH_SHORT).show();
            binding.password.setError("Password too short");
            state = false;
        } else binding.password.setError(null);

        return state;
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        binding.progressBar.setVisibility(View.GONE);
        if (!task.isSuccessful()) {
            Snackbar.make(binding.activityLogin, R.string.auth_failed, Snackbar.LENGTH_SHORT).show();
            binding.btnLogin.setEnabled(true);
        } else {
            switchActivity(MainActivity.class);
        }
    }

    private void switchActivity(Class classFile) {
        Intent intent = new Intent(this, classFile);
        if (id != null){
            intent.putExtra("id", id);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}