package com.heapixLearn.discovery.ui.authorization;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.heapixLearn.discovery.R;


public class SignUpConfirmCodePhoneActivity extends AppCompatActivity {

    private static final int RESULT_SUCCESS=4;
    private static final String PHONE = "phone";
    private static final String CONFIRMATION_CODE="confCode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up_confirm_code_phone);
        TextView massage = (TextView) findViewById(R.id.text_confirm_code);

        String s1 = getString(R.string.text_type_confirm_code_phone)+" ";
        String phone = getIntent().getExtras().getString(PHONE, "");
        String s2 =phone+". ";
        String s3 = getString(R.string.text_type_confirm_code_phone2)+" ";
        String s4 = getString(R.string.text_type_confirm_code_phone3)+" ";
        String s5 = getString(R.string.text_type_confirm_code_phone4);
        massage.setText(Html.fromHtml(s1+ s2 +"<b>"+s3+"</b>" + s4 + "<b>" + s5 + "</b>"));
        Button btnSend = (Button) findViewById(R.id.send_button_phone);
        final EditText editCode = (EditText) findViewById(R.id.confirmation_code) ;
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editCode.getText().toString().isEmpty()){
                    editCode.setError(getString(R.string.empty_zone));
                    return;
                }
                getIntent().putExtra(CONFIRMATION_CODE, editCode.getText().toString());
                setResult(RESULT_SUCCESS, getIntent());
                finish();
            }
        });
    }
}
