package com.heapixLearn.discovery.ui.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.heapixLearn.discovery.MainActivity;
import com.heapixLearn.discovery.R;

public class SuccessRegistrationActivity extends AppCompatActivity {

    private static final String NICK = "nick";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_registration_activity);
        TextView firstMassage = (TextView) findViewById(R.id.text_success_first);


        String nick = getIntent().getExtras().getString(NICK, "");
        String s1 = getString(R.string.text_success_reg1)+" "+nick;
        firstMassage.setText(s1);

        TextView lastMassage = (TextView) findViewById(R.id.text_success_last);

        String s3 = getString(R.string.text_success_reg3)+" ";
        String s5 = getString(R.string.text_success_reg4);

        lastMassage.setText(Html.fromHtml(s3 +"<b>"+s5+"</b>"));

        Button btnSend = (Button) findViewById(R.id.button_success_reg);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessRegistrationActivity.this, AddPhotoActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
