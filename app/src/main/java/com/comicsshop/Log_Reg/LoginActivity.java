package com.comicsshop.Log_Reg;

import androidx.appcompat.app.AppCompatActivity;
import com.comicsshop.MainActivity;
import com.comicsshop.DatabaseHelper;
import com.comicsshop.DatabaseHelper.User;
import com.comicsshop.R;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister;

    DatabaseHelper databaseHelper;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.nameEditText);
        etPassword = findViewById(R.id.passwordEditText);
        btnLogin = findViewById(R.id.loginButton);
        btnRegister = findViewById(R.id.registrationButton);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем введенные пользователем данные
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Проверяем правильность введенных данных
                boolean isCredentialsValid = databaseHelper.checkCredentials(username, password);

                if (isCredentialsValid) {
                    // Пользователь найден в базе данных, выполняем дополнительные действия

                    // Получаем данные пользователя
                    User user = databaseHelper.getUserByUsername(username);

                    // Пример вывода данных пользователя
                    Toast.makeText(LoginActivity.this, "Добро пожаловать, " + user.getUsername(), Toast.LENGTH_SHORT).show();

                    // Переходим на главный экран приложения
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Закрываем активити входа, чтобы пользователь не мог вернуться назад
                } else {
                    // Пользователь не найден или введены неправильные данные
                    Toast.makeText(LoginActivity.this, "Неправильное имя пользователя или пароль", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переходим на экран регистрации
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}