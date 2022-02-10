package com.mobile_prog.myaddressbook.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobile_prog.myaddressbook.Constant;
import com.mobile_prog.myaddressbook.adapters.EmployeeSearchAdapter;
import com.mobile_prog.myaddressbook.database.DatabaseHelper;
import com.mobile_prog.myaddressbook.models.Employee;
import com.mobile_prog.myaddressbook.models.Response;
import com.mobile_prog.myaddressbook.views.EmployeeSearchFragment;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DatabaseSyncService extends Service {
    private Runnable runnable;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Handler handler = new Handler();
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

        runnable = new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constant.API_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                EmployeeService service = retrofit.create(EmployeeService.class);
                Call<Response> response =  service.getEmployees();
                response.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        Response resp = response.body();
                        List<Employee> employees = resp.getEmployees();
                        db.sync(employees);
                        Toast.makeText(getApplicationContext(), "Data Synced", Toast.LENGTH_LONG).show();
                        Log.i("DatabaseSyncService", "Synced for " + employees.size() + " employees");
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });
                handler.postDelayed(runnable, 7200 * 1000);
            }
        };

        List<Employee> emps = db.getEmployees();
        if (emps.size() == 0) {
            handler.postDelayed(runnable, 1);
        } else {
            handler.postDelayed(runnable, 7200 * 1000);
        }

        Log.i("DatabaseSyncService", "Service Called");
        return super.onStartCommand(intent, flags, startId);
    }
}
