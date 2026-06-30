package com.mycooking;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout    layoutNIM, layoutPassword;
    private TextInputEditText  editNIM, editPassword;
    private MaterialButton     btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        layoutNIM      = findViewById(R.id.layoutNIM);
        layoutPassword = findViewById(R.id.layoutPassword);
        editNIM        = findViewById(R.id.editNIM);
        editPassword   = findViewById(R.id.editPassword);
        btnLogin       = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> validasiLogin());
    }

    private void validasiLogin() {
        String inputNIM  = editNIM.getText().toString().trim();
        String inputPass = editPassword.getText().toString().trim();

        // Reset error sebelumnya
        layoutNIM.setError(null);
        layoutPassword.setError(null);

        boolean valid = true;

        if (inputNIM.isEmpty()) {
            layoutNIM.setError("NIM tidak boleh kosong!");
            valid = false;
        } else if (!inputNIM.equals("231011400324")) {
            layoutNIM.setError("NIM tidak terdaftar di MyCooking!");
            valid = false;
        }

        if (inputPass.isEmpty()) {
            layoutPassword.setError("Password tidak boleh kosong!");
            valid = false;
        } else if (!inputPass.equals("4321")) {
            layoutPassword.setError("Password salah!");
            valid = false;
        }

        if (valid) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
