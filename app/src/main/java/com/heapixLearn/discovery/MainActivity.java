package com.heapixLearn.discovery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.heapixLearn.discovery.Server.Contacts.ServerContact;
import com.heapixLearn.discovery.Server.Contacts.ServerContactManager;
import com.heapixLearn.discovery.Server.TypeOfServerError;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ServerContactManager serverContactManager = new ServerContactManager();

        RunnableWithObject<List<ServerContact>> onSuccess_List = new RunnableWithObject<List<ServerContact>>(){
            @Override
            public void run(){
                List<ServerContact> list = this.getDescription();
            }
        };

        RunnableWithObject<TypeOfServerError> onFailure = new RunnableWithObject<TypeOfServerError>(){
          @Override
          public void run(){
              Toast.makeText(AppContext.getInstance(), this.getDescription().getTypeOfServerError(), Toast.LENGTH_SHORT).show();
          }
        };

        serverContactManager.readContacts(onSuccess_List, onFailure);

        Runnable onSuccess = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AppContext.getInstance(), "Success", Toast.LENGTH_SHORT).show();
            }
        };

        serverContactManager.createContact(new ServerContact(), onSuccess, onFailure);

        serverContactManager.deleteContact(new ServerContact(), onSuccess, onFailure);

        serverContactManager.updateContact(new ServerContact(), onSuccess, onFailure);

    }
}
