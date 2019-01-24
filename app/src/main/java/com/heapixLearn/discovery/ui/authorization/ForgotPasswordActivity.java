package com.heapixLearn.discovery.ui.authorization;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.heapixLearn.discovery.logic.authorization.AuthManager;
import com.heapixLearn.discovery.logic.authorization.AuthManagerInterface;
import com.heapixLearn.discovery.logic.authorization.RunnableWithError;
import com.heapixLearn.discovery.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    private AuthManagerInterface authManagerInterface = AuthManager.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forgot_password);
        Button btnSend = (Button) findViewById(R.id.send_button_forgot_password);
        final AutoCompleteTextView info = (AutoCompleteTextView) findViewById(R.id.info_forgot_password);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.getText().toString().isEmpty()){
                    info.setError(getResources().getString(R.string.empty_zone));
                    return;
                }
                Runnable onSuccess = new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                };

                final RunnableWithError onFailure = new RunnableWithError() {
                    @Override
                    public void run() {
                        Toast.makeText(ForgotPasswordActivity.this, this.getError().getDescription()+"",Toast.LENGTH_SHORT).show();
                    }
                };
                authManagerInterface.forgotPassword(info.getText().toString(), onSuccess, onFailure);
            }
        });
    }
}
