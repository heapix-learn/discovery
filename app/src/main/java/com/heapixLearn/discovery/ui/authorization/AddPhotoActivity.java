package com.heapixLearn.discovery.ui.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.heapixLearn.discovery.MainActivity;
import com.heapixLearn.discovery.R;

public class AddPhotoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        Button photoButton = (Button) findViewById(R.id.photo_button);
        TextView skip = (TextView) findViewById(R.id.skip);

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPhotoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPhotoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
