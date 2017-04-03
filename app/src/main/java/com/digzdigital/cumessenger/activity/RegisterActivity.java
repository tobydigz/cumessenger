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
import com.digzdigital.cumessenger.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener<AuthResult> {

    private ActivityRegisterBinding binding;
    private FirebaseAuth auth;
    private String email, password, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
        binding.btnResetPassword.setOnClickListener(this);
        binding.signInButton.setOnClickListener(this);
        binding.signUpButton.setOnClickListener(this);
    }

    private void switchActivity(Class classFile, boolean addExtras) {
        Intent intent = new Intent(this, classFile);
        if (addExtras) {
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            intent.putExtra("id", id);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_reset_password:
                switchActivity(ResetPasswordActivity.class, false);
                break;
            case R.id.sign_in_button:
                switchActivity(LoginActivity.class, false);
                break;
            case R.id.sign_up_button:
                signup();
                break;
        }
    }

    private void signup() {
        String email = binding.email.getText().toString().trim();
        String password = binding.password.getText().toString().trim();
        String id = binding.matric.getText().toString().toUpperCase().trim();
        if (!validateEmail(email) || !validatePassword(password) || !validateId(id)) return;
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.signUpButton.setEnabled(false);
        this.email = email;
        this.password = password;
        this.id = id;
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, this);
    }

    private boolean validateId(String id) {
        boolean state = true;
        if (TextUtils.isEmpty(id)) {
            Snackbar.make(binding.activitySignUp, "Enter email address!", Snackbar.LENGTH_SHORT).show();
            binding.email.setError("Enter email address!");
            state = false;

        } else binding.email.setError(null);
        return state;
    }


    private boolean validateEmail(String email) {
        boolean state = true;
        if (TextUtils.isEmpty(email)) {
            Snackbar.make(binding.activitySignUp, "Enter email address!", Snackbar.LENGTH_SHORT).show();
            binding.email.setError("Enter email address!");
            state = false;

        } else binding.email.setError(null);

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Snackbar.make(binding.activitySignUp, "Enter a valid email address!", Snackbar.LENGTH_SHORT).show();
            binding.email.setError("Enter a valid email address!");
            state = false;
        } else binding.email.setError(null);

        int pointAt = email.indexOf("@") + 1;
        if (!((email.substring(pointAt).equals("covenantuniversity.edu.ng")) || (email.substring(pointAt).equals("stu.cu.edu.ng")))) {
            Snackbar.make(binding.activitySignUp, "Enter a valid Covenant University email address!", Snackbar.LENGTH_SHORT).show();
            binding.email.setError("Enter a valid Covenant University email address!");
            state = false;
        } else binding.email.setError(null);

        return state;
    }

    private boolean validatePassword(String password) {
        boolean state = true;
        if (TextUtils.isEmpty(password)) {
            Snackbar.make(binding.activitySignUp, "Enter password", Snackbar.LENGTH_SHORT).show();
            binding.password.setError("Enter password");
            state = false;
        } else binding.password.setError(null);

        if (password.length() < 6) {
            Snackbar.make(binding.activitySignUp, "Password too short, enter minimum 6 characters!", Snackbar.LENGTH_SHORT).show();
            binding.password.setError("Password too short");
            state = false;
        } else binding.password.setError(null);
        return state;
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        binding.progressBar.setVisibility(View.GONE);
        if (!task.isSuccessful()) {
            Snackbar.make(binding.activitySignUp, "Sign Up failed, try again", Snackbar.LENGTH_SHORT).show();
            binding.signUpButton.setEnabled(true);
        } else {
            switchActivity(LoginActivity.class, true);
        }

    }
}