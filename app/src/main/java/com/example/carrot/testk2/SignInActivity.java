package com.example.carrot.testk2;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.parse.ParseException;
        import com.parse.ParseUser;
        import com.parse.SignUpCallback;

/**
 * Created by Administrator on 2015-12-12.
 */
public class SignInActivity extends Activity {

    EditText idText;
    EditText passwordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        idText = (EditText) findViewById(R.id.id_input);
        passwordText = (EditText) findViewById(R.id.password_input);
        Button buttonPut = (Button) findViewById(R.id.sign_button);

        buttonPut.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                ParseUser user = new ParseUser();
                user.setUsername(idText.getText().toString());
                user.setPassword(passwordText.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SignInActivity.this, LoginActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed" + e, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}