package com.heapixLearn.discovery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.heapixLearn.discovery.server.TypeOfServerError;
import com.heapixLearn.discovery.server.contacts.ServerContact;
import com.heapixLearn.discovery.server.contacts.ServerContactManager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RunnableWithObject<List<ServerContact>> onSuccessList = new RunnableWithObject<List<ServerContact>>(){
            @Override
            public void run(){

            }
        };

        RunnableWithObject<TypeOfServerError> onFailure = new RunnableWithObject<TypeOfServerError>(){
            @Override
            public void run(){
                Toast.makeText(AppContext.getInstance(), this.getDescription().getTypeOfServerError(), Toast.LENGTH_SHORT).show();
            }
        };

        RunnableWithObject<ServerContact> onSuccessContact = new RunnableWithObject<ServerContact>(){
            @Override
            public void run(){

            }
        };

        Runnable onSuccess = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AppContext.getInstance(), "Success", Toast.LENGTH_SHORT).show();
            }
        };

        List<ServerContact> serverContact= new ArrayList<ServerContact>();

        ServerContact contact = new ServerContact();
        contact.setId("1");
        contact.setName("qwerty");
        contact.setEmail("ins@kdmcm.com");

        serverContact.add(contact);

        ServerContactManager serverContactManager = new ServerContactManager();
        serverContactManager.createContact(contact, onSuccess, onFailure);
        serverContactManager.getContactById("1", onSuccessContact, onFailure);
        serverContactManager.updateContact(contact, onSuccess, onFailure);
        serverContactManager.deleteContact(contact, onSuccess, onFailure);
        serverContactManager.readContacts(onSuccessList, onFailure);

    }
}
