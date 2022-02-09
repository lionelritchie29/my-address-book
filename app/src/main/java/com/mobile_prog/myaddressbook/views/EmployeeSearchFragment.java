package com.mobile_prog.myaddressbook.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile_prog.myaddressbook.Constant;
import com.mobile_prog.myaddressbook.R;
import com.mobile_prog.myaddressbook.models.Employee;
import com.mobile_prog.myaddressbook.models.Response;
import com.mobile_prog.myaddressbook.services.EmployeeService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmployeeSearchFragment extends Fragment {

    public EmployeeSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void fetchEmployees() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EmployeeService service = retrofit.create(EmployeeService.class);
        Call<Response> response =  service.getEmployees();
        response.enqueue(new Callback<Response>() {

            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful())
                {
                    Response resp = response.body();
                    Log.i("Hehe", resp.getNim());
                    Log.i("Hehe", resp.getNama());
                    Log.i("Hehe", String.valueOf(resp.getEmployees().size()));
                    Log.i("Hehe", resp.toString());
                }
                else
                {
                    Log.i("Hehe", "Request Error :: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.i("Hehe", "Network Error :: " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_employee_search, container, false);
    }
}