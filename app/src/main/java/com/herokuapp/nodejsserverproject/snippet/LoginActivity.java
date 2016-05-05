package com.herokuapp.nodejsserverproject.snippet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.herokuapp.nodejsserverproject.snippet.async_tasks.LoginTask;
import com.herokuapp.nodejsserverproject.snippet.async_tasks.RegisterTask;
import com.herokuapp.nodejsserverproject.snippet.utils.Utills;
import com.herokuapp.nodejsserverproject.snippet.utils.WebUtills;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    @Bind(R.id.etRegisterUser)
    EditText etRegisterUser;
    @Bind(R.id.etRegisterEmail)
    EditText etRegisterEmail;
    @Bind(R.id.etResgiterPass)
    EditText etRegisterPass;
    @Bind(R.id.snippetObjectLayout)
    CardView snippetObjectLayout;
    @Bind(R.id.registerLayout)
    FrameLayout registerLayout;
    @Bind(R.id.etLoginUser)
    AutoCompleteTextView etLoginUser;
    @Bind(R.id.etLoginPassword)
    EditText etLoginPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


    }


    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @OnClick({R.id.btnRegister, R.id.btnLogin, R.id.btnSubmit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                Utills.hideKeyboard(this);
                snippetObjectLayout.setVisibility(View.GONE);
                registerLayout.setVisibility(View.VISIBLE);
                clearFields();
                break;
            case R.id.btnLogin:
                Utills.hideKeyboard(this);
                loginTask();
                //clearFields();
                break;
            case R.id.btnSubmit:
                Utills.hideKeyboard(this);
                snippetObjectLayout.setVisibility(View.VISIBLE);
                registerLayout.setVisibility(View.GONE);
                if (WebUtills.paramCheck(etRegisterEmail.getText().toString(), etRegisterUser.getText().toString(), etRegisterPass.getText().toString()))
                    registerTask(etRegisterEmail.getText().toString(), etRegisterUser.getText().toString(), etRegisterPass.getText().toString());
                //registerTask(null,null,null);
                clearFields();
                break;
        }
    }

    private void clearFields() {
        etRegisterEmail.setText("");
        etRegisterPass.setText("");
        etRegisterUser.setText("");
        etLoginPassword.setText("");
        etLoginUser.setText("");

    }

    private void loginTask() {
        LoginTask task = new LoginTask(this,etLoginUser.getText().toString(),etLoginPassword.getText().toString());
        task.execute();


    }

    private void registerTask(String email, String user, String pass) {
        RegisterTask task = new RegisterTask(user, email, pass, this);
        task.execute();
    }


    public void hideKeyboard(View view) {
        Utills.hideKeyboard(this);
    }

    public void hideRegistrationFormAndDisplayLogin(View view) {
        snippetObjectLayout.setVisibility(View.VISIBLE);
        registerLayout.setVisibility(View.GONE);
    }
}

