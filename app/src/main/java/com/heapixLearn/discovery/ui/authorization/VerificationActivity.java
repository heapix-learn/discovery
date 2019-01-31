package com.heapixLearn.discovery.ui.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.heapixLearn.discovery.R;
import com.heapixLearn.discovery.entity.authorization.Person;
import com.heapixLearn.discovery.logic.authorization.AuthManager;
import com.heapixLearn.discovery.logic.authorization.AuthManagerWith;
import com.heapixLearn.discovery.logic.authorization.RunnableWithError;

public class VerificationActivity extends AppCompatActivity {

    public static final int RESULT_SUCCESS=4;
    public static final String NICK = "nick";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String CONFIRMATION_CODE="confCode";

    private TextView phoneView;
    private TextView emailView;
    private EditText editPhone;
    private EditText editEmail;
    private TextView massage;
    private Button btnNext;
    private View lineEmail;
    private View linePhone;
    private String name;
    private String nick;
    private String password;
    private String phone;
    private String email;
    private int CONFIRM_EMAIL_REQUEST=10;
    private int CONFIRM_SMS_REQUEST=11;
    final RunnableWithError onFailure = new RunnableWithError() {
        @Override
        public void run() {
            Toast.makeText(VerificationActivity.this, this.getError().getDescription()+"",Toast.LENGTH_SHORT).show();
        }
    };
    private AuthManagerWith authManager = new AuthManager();

    final Runnable onSuccessLogin = new Runnable() {
        @Override
        public void run() {
            setResult(RESULT_SUCCESS);
            Intent intent = new Intent(VerificationActivity.this, SuccessRegistrationActivity.class);
            intent.putExtra(NICK, nick);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_verification);
        phoneView = (TextView) findViewById(R.id.phoneView);
        emailView = (TextView) findViewById(R.id.emailView);
        editPhone = (EditText) findViewById(R.id.phone);
        editEmail = (EditText) findViewById(R.id.email);
        massage = (TextView) findViewById(R.id.massageVerific);
        btnNext = (Button) findViewById(R.id.next_button_verification);
        linePhone = (View) findViewById(R.id.lineForPhoneView);
        lineEmail = (View) findViewById(R.id.lineForEmailView);
        phoneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneIsVisible();
            }
        });
        emailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailIsVisible();
            }
        });

        name = getIntent().getExtras().getString(NAME);
        nick = getIntent().getExtras().getString(NICK);
        password = getIntent().getExtras().getString(PASSWORD);
        final Intent EmailIntent = new Intent(VerificationActivity.this, SignUpConfirmCodeEmailActivity.class);
        final Intent SMSIntent = new Intent(VerificationActivity.this, SignUpConfirmCodePhoneActivity.class);

        final Runnable sendEmailOnSuccess = new Runnable() {
            @Override
            public void run() {
                startActivityForResult(EmailIntent,CONFIRM_EMAIL_REQUEST);
            }
        };
        final Runnable sendSMSOnSuccess = new Runnable() {
            @Override
            public void run() {
                startActivityForResult(SMSIntent,CONFIRM_SMS_REQUEST);
            }
        };


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (!editEmail.getText().toString().isEmpty()){
                   email = editEmail.getText().toString();
                   EmailIntent.putExtra(EMAIL, email);
                   authManager.sendEmail(email, sendEmailOnSuccess, onFailure);

                   return;
               }
               if (!editPhone.getText().toString().isEmpty()){
                   phone = editPhone.getText().toString();
                   SMSIntent.putExtra(PHONE, phone);
                   authManager.sendSMSToPhone(phone, sendSMSOnSuccess, onFailure);

               }
            }
        });
    }

    private void phoneIsVisible(){
        editPhone.setText("");
        editEmail.setText("");
        massage.setText(getString(R.string.textPhoneForVerification));
        editEmail.setVisibility(View.GONE);
        editPhone.setVisibility(View.VISIBLE);
        linePhone.setVisibility(View.VISIBLE);
        lineEmail.setVisibility(View.GONE);
    }
    private void emailIsVisible(){
        linePhone.setVisibility(View.GONE);
        editEmail.setText("");
        editPhone.setText("");
        massage.setText(getString(R.string.textEmailForVerification));
        editEmail.setVisibility(View.VISIBLE);
        editPhone.setVisibility(View.GONE);
        lineEmail.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CONFIRM_EMAIL_REQUEST){
            if (resultCode==RESULT_SUCCESS){
                authManager.checkEmailVerification(new Runnable() {
                    @Override
                    public void run() {
                        Person myPerson = new Person();
                        myPerson.setPassword(password);
                        myPerson.setEmail(email);
                        myPerson.setName(name);
                        myPerson.setUserName(nick);

                        authManager.tryRegistrationWith(myPerson, new Runnable() {
                            @Override
                            public void run() {
                                authManager.tryLoginWith(email, password, onSuccessLogin, onFailure);
                            }
                        }, onFailure);
                    }
                }, onFailure);
            }
            return;
        }

        if (requestCode==CONFIRM_SMS_REQUEST){
            if (resultCode==RESULT_SUCCESS){
                String code = data.getExtras().getString(CONFIRMATION_CODE, "");
                authManager.checkPhoneVerification(code, new Runnable() {
                    @Override
                    public void run() {
                        Person myPerson = new Person();
                        myPerson.setPassword(password);
                        myPerson.setPhone(phone);
                        myPerson.setName(name);
                        myPerson.setUserName(nick);

                        authManager.tryRegistrationWith(myPerson, new Runnable() {
                            @Override
                            public void run() {
                                authManager.tryLoginWith(phone, password, onSuccessLogin, onFailure);
                            }
                        }, onFailure);
                    }
                }, onFailure);
            }
        }


    }
}
