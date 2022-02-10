package com.mobile_prog.myaddressbook.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.mobile_prog.myaddressbook.Constant;
import com.mobile_prog.myaddressbook.R;
import com.mobile_prog.myaddressbook.adapters.EmployeeSearchAdapter;
import com.mobile_prog.myaddressbook.database.DatabaseHelper;
import com.mobile_prog.myaddressbook.models.Employee;
import com.mobile_prog.myaddressbook.models.Response;
import com.mobile_prog.myaddressbook.services.EmployeeService;

import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddressBookActivity extends AppCompatActivity {

    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);
        this.rv = findViewById(R.id.address_book_rv);
        fetchAddressBook();
    }

    private void fetchAddressBook() {
        Context ctx = this;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EmployeeService service = retrofit.create(EmployeeService.class);
        Call<Response> response =  service.getEmployees();

        DatabaseHelper db = new DatabaseHelper(ctx);
        List<Integer> savedEmployeeIds = db.getSavedEmployeeIds();

        response.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful())
                {
                    Response resp = response.body();
                    List<Employee> employees = resp.getEmployees();
                    List<Employee> filteredEmployees = new Vector<>();

                    for (Employee emp : employees) {
                        if (savedEmployeeIds.contains(emp.getEmployeeId())) {
                            filteredEmployees.add(emp);
                        }
                    }

                    EmployeeSearchAdapter adapter = new EmployeeSearchAdapter(filteredEmployees, true);
                    rv.setLayoutManager(new LinearLayoutManager(ctx));
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
}