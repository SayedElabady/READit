package capstone.udacity.com.readit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.udacity.com.readit.Models.Account;
import capstone.udacity.com.readit.Listeners.OnFinishListener;
import capstone.udacity.com.readit.BusinessLayer.Firebase;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.email_register_text)
    EditText emailRegisterText;
    @BindView(R.id.password_register_text)
    EditText passwordRegisterText;
    @BindView(R.id.name_text)
    EditText nameText;
    @BindView(R.id.phone_register_text)
    EditText phoneRegisterText;
    @BindView(R.id.address_register_text)
    EditText addressRegisterText;


    Firebase firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        firebase = new Firebase();
    }

    public void register(View view) {
        final String email = emailRegisterText.getText().toString();
        String password = passwordRegisterText.getText().toString();
        String name = nameText.getText().toString();
        String phone = phoneRegisterText.getText().toString();
        String address = addressRegisterText.getText().toString();
        Account account = new Account();
        account.setAddress(address);
        account.setEmail(email);
        account.setName(name);
        account.setPhone(phone);
        account.setPassword(password);
        firebase.createUser(account, new OnFinishListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(RegisterActivity.this , "Congrats you've registered successfully" ,Toast.LENGTH_SHORT).show();
                moveToLogin();
            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(RegisterActivity.this , "There was a problem, " + errorMessage + "Try again later",Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void login(View view) {
        moveToLogin();
    }

    private void moveToLogin() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }
}
