package com.heapixLearn.discovery.ui.authorization;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.heapixLearn.discovery.MainActivity;
import com.heapixLearn.discovery.R;
import com.heapixLearn.discovery.logic.authorization.AuthManager;
import com.heapixLearn.discovery.logic.authorization.AuthManagerWith;
import com.heapixLearn.discovery.logic.authorization.RunnableWithError;

public class LoginActivity extends AppCompatActivity {

    private static final int RESULT_SUCCESS=4;
    public int  RC_SIGN_IN = 119;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private int SIGN_UP_REQUEST = 8;
    private AutoCompleteTextView mUserNameView;
    private EditText mPasswordView;
    private CallbackManager callbackManager;
    private AuthManagerWith authManager = new AuthManager();
    private Runnable onSuccess = new Runnable() {
    @Override
    public void run() {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
    private RunnableWithError onFailure = new RunnableWithError() {
        @Override
        public void run() {
            Toast.makeText(LoginActivity.this, "" + this.getError().getDescription(),  Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView createAccount = (TextView) findViewById(R.id.create_acco);
        mUserNameView = (AutoCompleteTextView) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);

        final Button signInButton = (Button) findViewById(R.id.login_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        View googleButton = (View) findViewById(R.id.google_sign_in);

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this, gso);
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }

        });

        callbackManager = CallbackManager.Factory.create();
        final LoginButton facebookSignIn = new LoginButton(this);
        Button fb = (Button) findViewById(R.id.facebook_sign_in);
        facebookSignIn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();
                authManager.tryLoginWithFacebook(profile, onSuccess, onFailure);
                LoginManager.getInstance().logOut();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });
        mUserNameView.setText(authManager.getStoreLogin());

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookSignIn.callOnClick();
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivityForResult(myIntent, SIGN_UP_REQUEST);
            }
        });

        TextView forgotPassword = (TextView) findViewById(R.id.forgot_password);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void attemptLogin() {
        if (mUserNameView.getText().toString().isEmpty()){
            mUserNameView.setError(getResources().getString(R.string.empty_zone));
            return;
        }

        if (mPasswordView.getText().toString().isEmpty()){
            mPasswordView.setError(getResources().getString(R.string.empty_zone));
            return;
        }
        authManager.tryLoginWith(mUserNameView.getText().toString(), mPasswordView.getText().toString(), onSuccess, onFailure);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==SIGN_UP_REQUEST){
            if (resultCode==RESULT_SUCCESS){
                finish();
            }
        }
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            authManager.tryLoginWithGoogle(account, onSuccess, onFailure);
            signOut();
        } catch (ApiException e) {
        }
    }

}
