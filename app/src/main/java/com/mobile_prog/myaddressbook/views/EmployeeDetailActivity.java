package com.mobile_prog.myaddressbook.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mobile_prog.myaddressbook.Constant;
import com.mobile_prog.myaddressbook.R;

public class EmployeeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);
        EmployeeDetailFragment fragment = (EmployeeDetailFragment) getSupportFragmentManager().findFragmentById(R.id.employee_detail_fragment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        int employeeId = getIntent().getExtras().getInt(Constant.EMPLOYEE_ID_KEY);
        fragment.setEmployee(employeeId);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}