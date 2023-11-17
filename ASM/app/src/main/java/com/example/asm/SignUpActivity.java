package com.example.asm;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.asm.api.ApiService;
import com.example.asm.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignUp, loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextEmail = findViewById(R.id.signupEmailEditText);
        editTextPassword = findViewById(R.id.signupPasswordEditText);
        buttonSignUp = findViewById(R.id.signupButton);
        loginbtn = findViewById(R.id.loginBtn);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(SignUpActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });
    }

    private void signUpUser() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        // Kiểm tra email và password
        if (isValidInput(email, password)) {
            // Tạo đối tượng User từ dữ liệu nhập
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(password);

            // Gọi API để đăng ký
            registerUser(newUser);
        }
    }

    private boolean isValidInput(String email, String password) {
        // Kiểm tra null và rỗng
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            showToast("Vui lòng nhập đầy đủ thông tin");
            return false;
        }

        // Loại bỏ khoảng trắng ở đầu và cuối
        String trimmedEmail = email.trim();
        String trimmedPassword = password.trim();

        // Kiểm tra độ dài tối thiểu
        int minEmailLength = 5; // Độ dài tối thiểu cho email
        int minPasswordLength = 6; // Độ dài tối thiểu cho password

        if (trimmedEmail.length() < minEmailLength) {
            showToast("Email phải có ít nhất " + minEmailLength + " ký tự");
            return false;
        }

        if (trimmedPassword.length() < minPasswordLength) {
            showToast("Mật khẩu phải có ít nhất " + minPasswordLength + " ký tự");
            return false;
        }

        // Kiểm tra định dạng email (ví dụ đơn giản)
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
            showToast("Email không hợp lệ");
            return false;
        }

        return true;
    }

    private void registerUser(User newUser) {
        ApiService apiService = RetrofitClient.getRetrofit().create(ApiService.class);

        Call<User> call = apiService.registerUser(newUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // Đăng ký thành công, có thể xử lý và chuyển đến màn hình đăng nhập hoặc màn hình chính
                    Intent loginIntent = new Intent(SignUpActivity.this, Login.class);
                    startActivity(loginIntent);
                    finish();
                } else {
                    Toast.makeText(SignUpActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Xử lý khi có lỗi kết nối hoặc lỗi khác
                // Hiển thị thông báo hoặc xử lý tùy thuộc vào yêu cầu của bạn
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
