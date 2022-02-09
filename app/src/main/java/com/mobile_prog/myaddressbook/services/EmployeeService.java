package com.mobile_prog.myaddressbook.services;

import com.mobile_prog.myaddressbook.models.Employee;
import com.mobile_prog.myaddressbook.models.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EmployeeService {
    @GET("people/?nim=2301846212&nama=Lionel Ritchie")
    Call<Response> getEmployees();

    @GET("people/{id}/?nim=2301846212&nama=Lionel Ritchie")
    Call<Response> getEmployee(@Path("id") int id);
}
