package com.mobile_prog.myaddressbook.views;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile_prog.myaddressbook.Constant;
import com.mobile_prog.myaddressbook.R;
import com.mobile_prog.myaddressbook.adapters.EmployeeSearchAdapter;
import com.mobile_prog.myaddressbook.database.DatabaseHelper;
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

    static interface EmployeeListListener {
        void itemClicked(int id);
    }

    private EmployeeListListener listener;
    private RecyclerView rv = null;

    public EmployeeSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (EmployeeListListener) context;
    }

    public void onEmployeeClicked(int employeeId) {
        if (listener != null) {
            listener.itemClicked(employeeId);
        }
    }

    private void fetchEmployees() {
        EmployeeSearchFragment fragment = this;
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
                    List<Employee> employees = resp.getEmployees();
                    EmployeeSearchAdapter adapter = new EmployeeSearchAdapter(employees, fragment);
                    rv.setLayoutManager(new LinearLayoutManager(getContext()));
                    rv.setAdapter(adapter);
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
        View view = inflater.inflate(R.layout.fragment_employee_search, container, false);
        this.rv = view.findViewById(R.id.employee_search_rv);
        fetchEmployees();
        return view;
    }
}