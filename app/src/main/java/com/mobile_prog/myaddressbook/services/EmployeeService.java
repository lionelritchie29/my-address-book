package com.mobile_prog.myaddressbook.services;

import com.mobile_prog.myaddressbook.models.Employee;
import com.mobile_prog.myaddressbook.models.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EmployeeService {
    @GET("people/?nim=2301846212&nama=Lionel Ritchie")
    Call<Response> getEmployees();
}
