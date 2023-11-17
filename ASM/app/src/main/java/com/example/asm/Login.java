package com.example.asm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asm.api.ApiService;
import com.example.asm.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPass;
    private Button btnLogin, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.usernameEditText);
        editTextPass = findViewById(R.id.passwordEditText);
        btnLogin = findViewById(R.id.loginButton);
        btnSignUp = findViewById(R.id.registerButton);

        ApiService apiService = RetrofitClient.getRetrofit().create(ApiService.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi hàm xử lý đăng nhập khi nút đăng nhập được bấm
                loginUser(apiService);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(Login.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });
    }

    private void loginUser(ApiService apiService) {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPass.getText().toString().trim();

        // Kiểm tra tính hợp lệ của thông tin đăng nhập
        if (isValidLoginInput(email, password)) {
            Call<List<User>> call = apiService.getUsers();
            call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.isSuccessful()) {
                        List<User> userList = response.body();

                        // Kiểm tra thông tin đăng nhập
                        if (validateLogin(userList, email, password)) {
                            Intent mainIntent = new Intent(Login.this, MainActivity.class);
                            startActivity(mainIntent);
                            Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            // Đăng nhập không thành công
                            Toast.makeText(Login.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Xử lý lỗi khi gọi API
                        Toast.makeText(Login.this, "Có lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    // Xử lý lỗi kết nối
                    Toast.makeText(Login.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean isValidLoginInput(String email, String password) {
        // Kiểm tra null và rỗng
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            showToast("Vui lòng nhập đầy đủ thông tin");
            return false;
        }

        return true;
    }

    private boolean validateLogin(List<User> userList, String email, String password) {
        for (User user : userList) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return true; // Thông tin đăng nhập hợp lệ
            }
        }
        return false; // Thông tin đăng nhập không hợp lệ
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
