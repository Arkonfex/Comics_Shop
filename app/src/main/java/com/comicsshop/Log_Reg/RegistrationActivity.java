package com.comicsshop.Log_Reg;

import androidx.appcompat.app.AppCompatActivity;
import com.comicsshop.DatabaseHelper;
import com.comicsshop.DatabaseHelper.User;
import com.comicsshop.R;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etEmail;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etUsername = findViewById(R.id.nameEditText);
        etPassword = findViewById(R.id.passwordEditText);
        etEmail = findViewById(R.id.emailEditText);
        btnRegister = findViewById(R.id.registerButton);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем введенные пользователем данные
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String email = etEmail.getText().toString().trim();

                // Проверяем, что поля не пустые
                if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                } else {
                    // Создаем экземпляр DatabaseHelper
                    DatabaseHelper databaseHelper = new DatabaseHelper(RegistrationActivity.this);

                    // Проверяем, существует ли пользователь с таким же именем
                    if (databaseHelper.isUserExists(username)) {
                        Toast.makeText(RegistrationActivity.this, "Пользователь с таким именем уже существует", Toast.LENGTH_SHORT).show();
                    } else {
                        // Создаем нового пользователя
                        User user = new User(username, password, email);

                        // Добавляем пользователя в базу данных
                        databaseHelper.addUser(user);

                        Toast.makeText(RegistrationActivity.this, "Регистрация выполнена успешно", Toast.LENGTH_SHORT).show();
                        finish(); // Закрываем активити регистрации и возвращаемся на предыдущий экран
                    }
                }
            }
        });
    }
}
