package com.mobile_prog.myaddressbook.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mobile_prog.myaddressbook.Constant;
import com.mobile_prog.myaddressbook.R;
import com.mobile_prog.myaddressbook.services.DatabaseSyncService;

public class MainActivity extends AppCompatActivity implements EmployeeSearchFragment.EmployeeListListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Intent syncServiceIntent = new Intent(this, DatabaseSyncService.class);
        startService(syncServiceIntent);
    }

    @Override
    public void itemClicked(int id) {
        View fragmentContainer = findViewById(R.id.fragment_container);

        if (fragmentContainer != null) { // large screen
            EmployeeDetailFragment fragment = new EmployeeDetailFragment(id);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(null);
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        } else { // small
            Intent intent = new Intent(this, EmployeeDetailActivity.class);
            intent.putExtra(Constant.EMPLOYEE_ID_KEY, id);
            startActivity(intent);
        }
    }
}