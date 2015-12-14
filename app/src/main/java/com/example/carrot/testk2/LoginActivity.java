package com.example.carrot.testk2;
        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.parse.LogInCallback;
        import com.parse.ParseException;
        import com.parse.ParseUser;

/**
 * Created by Administrator on 2015-12-12.
 */
public class LoginActivity extends Activity{
    String id;
    String password;
    EditText loginId;

    EditText loginPassword;
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        loginId = (EditText) findViewById(R.id.userId);
        loginPassword = (EditText) findViewById(R.id.userPassword);
        Button loginBtn = (Button) findViewById(R.id.login_button);
        Button signinBtn = (Button) findViewById(R.id.signin_button);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id = loginId.getText().toString();
                password = loginPassword.getText().toString();

                ParseUser.logInInBackground(id, password,
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (e == null) {
                                    Toast.makeText(getApplicationContext(), "Login_Successed", Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else if (e != null) {
                                    Toast.makeText(getApplicationContext(), "Error : " + e, Toast.LENGTH_LONG).show();
                                } else if (id == null) {
                                    Toast.makeText(getApplicationContext(), "아이디가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}