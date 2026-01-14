package com.ifcompany.smart_house_hydroponics.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ifcompany.smart_house_hydroponics.MainActivity;
import com.ifcompany.smart_house_hydroponics.R;
import com.ifcompany.smart_house_hydroponics.databinding.ActivityLoginsBinding;
import com.ifcompany.smart_house_hydroponics.retrofit.Retrofit_Factory;
import com.ifcompany.smart_house_hydroponics.retrofit.Retrofit_Interface;
import com.ifcompany.smart_house_hydroponics.retrofit.model.receive.Retrofit_ReceiveLoginModel;
import com.ifcompany.smart_house_hydroponics.retrofit.model.send.Retrofit_SendLoginModel;
import com.ifcompany.smart_house_hydroponics.token.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private String tag = "LoginActivity";
    private LoginViewModel loginViewModel;
    private ActivityLoginsBinding binding;
    private Retrofit_Interface retrofitService;
    private TokenManager tokenManager;
    private ProgressBar loadingProgressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retrofitService = Retrofit_Factory.getInstance(getApplicationContext()).create();
        tokenManager = TokenManager.getInstance(getApplicationContext());

        binding = ActivityLoginsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        loadingProgressBar = binding.loading;
        final CheckBox autoLogin = binding.checkAutoLogin;

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = usernameEditText.getText().toString();
                String pw = passwordEditText.getText().toString();

                loadingProgressBar.setVisibility(View.VISIBLE);

                Login(email, pw, autoLogin.isChecked());

//                loginViewModel.login(usernameEditText.getText().toString(),
//                        passwordEditText.getText().toString());
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void Login(String email, String pw , Boolean autoLoginCheck){
        Retrofit_SendLoginModel rl = new Retrofit_SendLoginModel(email,pw);
        Log.d(tag,"login handle");
        retrofitService.Login(rl).enqueue(new Callback<Retrofit_ReceiveLoginModel>() {
            @Override
            public void onResponse(Call<Retrofit_ReceiveLoginModel> call, Response<Retrofit_ReceiveLoginModel> response) {

                Log.d(tag,"response.code() : "+response.code());
                Log.d(tag,"response.success() : "+response.body().getItem().getSuccess());

                if(!response.body().getItem().getSuccess()|| response.body().getItem().getSuccess() == null){
                    showLoginFailed(R.string.login_failed);
                    loadingProgressBar.setVisibility(View.GONE);
                    return;
                }

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                Retrofit_ReceiveLoginModel.Retrofit_ReceiveLoginModel_item m = response.body().getItem();

                tokenManager.setAutoLogin(autoLoginCheck);

                tokenManager.saveTokens(m.getToken_pair().getAccess_token(), m.getToken_pair().getRefresh_token(), m.getToken_pair().getExpires_in(), m.getEmail(), new TokenManager.TokenSaveCallback() {
                    @Override
                    public void onTokenSaved() {
                        loadingProgressBar.setVisibility(View.GONE);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onTokenSaveFailed() {
                        showLoginFailed(R.string.login_failed);
                        loadingProgressBar.setVisibility(View.GONE);
                    }
                });


//                startActivity(intent);
//                finish();

            }

            @Override
            public void onFailure(Call<Retrofit_ReceiveLoginModel> call, Throwable t) {
                Log.d(tag, String.valueOf(t));
                showLoginFailed(R.string.login_failed);
                loadingProgressBar.setVisibility(View.GONE);
            }
        });
    }
}