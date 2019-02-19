package com.heapixLearn.discovery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.heapixLearn.discovery.ui.contact.ContactsFragment;
import com.heapixLearn.discovery.ui.dummy_fragments.MapFragment;
import com.heapixLearn.discovery.ui.dummy_fragments.SettingsFragment;
import com.heapixLearn.discovery.ui.dummy_fragments.SupportFragment;
import com.heapixLearn.discovery.ui.dummy_fragments.UserProfileFragment;

public class MainActivity extends AppCompatActivity {

    final UserProfileFragment userProfileFragment = new UserProfileFragment();
    final MapFragment mapFragment = new MapFragment();
    final SupportFragment supportFragment = new SupportFragment();
    final ContactsFragment contactsFragment = new ContactsFragment();
    final SettingsFragment settingsFragment = new SettingsFragment();
    Fragment active = userProfileFragment;

    final FragmentManager fragmentManager = getSupportFragmentManager();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_user:
                    fragmentManager.beginTransaction().hide(active).show(userProfileFragment).commit();
                    active = userProfileFragment;
                    return true;
                case R.id.navigation_map:
                    fragmentManager.beginTransaction().hide(active).show(mapFragment).commit();
                    active = mapFragment;
                    return true;
                case R.id.navigation_support:
                    fragmentManager.beginTransaction().hide(active).show(supportFragment).commit();
                    active = supportFragment;
                    return true;
                case R.id.navigation_contacts:
                    fragmentManager.beginTransaction().hide(active).show(contactsFragment).commit();
                    active = contactsFragment;
                    return true;
                case R.id.navigation_settings:
                    fragmentManager.beginTransaction().hide(active).show(settingsFragment).commit();
                    active = settingsFragment;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager.beginTransaction().add(R.id.main_container, contactsFragment).hide(contactsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, settingsFragment).hide(settingsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, supportFragment).hide(supportFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, mapFragment).hide(mapFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, userProfileFragment).commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}