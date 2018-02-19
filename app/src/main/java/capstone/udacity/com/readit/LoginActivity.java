package capstone.udacity.com.readit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.udacity.com.readit.Listeners.OnFinishListener;
import capstone.udacity.com.readit.BusinessLayer.Firebase;

public class LoginActivity extends AppCompatActivity {
    Firebase firebase;
    @BindView(R.id.email_text)
    EditText emailText;
    @BindView(R.id.password_text)
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        firebase = new Firebase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            moveToMain();
            finish();

        }
    }

    public void onRegister(View view) {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }
    private void moveToMain(){
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }
    public void login(View view) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        firebase.login(email , password , new OnFinishListener(){
            @Override
            public void onSuccess() {
                Toast.makeText(LoginActivity.this , R.string.logged_successfully ,Toast.LENGTH_SHORT).show();
                moveToMain();
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(LoginActivity.this , getString(R.string.theres_problem) + errorMessage + getString(R.string.try_again),Toast.LENGTH_SHORT).show();

            }
        });
    }
}
