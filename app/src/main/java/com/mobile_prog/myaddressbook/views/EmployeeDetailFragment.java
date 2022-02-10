package com.mobile_prog.myaddressbook.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobile_prog.myaddressbook.Constant;
import com.mobile_prog.myaddressbook.R;
import com.mobile_prog.myaddressbook.adapters.EmployeeSearchAdapter;
import com.mobile_prog.myaddressbook.database.DatabaseHelper;
import com.mobile_prog.myaddressbook.models.Employee;
import com.mobile_prog.myaddressbook.models.Response;
import com.mobile_prog.myaddressbook.services.EmployeeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmployeeDetailFragment extends Fragment {

    private int employeeId;
    private TextView nameTv;
    private TextView cityTv;
    private TextView phoneTv;
    private TextView emailTv;
    private TextView memberSinceTv;
    private MapView mapView;
    private GoogleMap googleMap;
    private Button addAddressBookBtn;

    public EmployeeDetailFragment(int employeeId) {
        this.employeeId = employeeId;
    }
    public EmployeeDetailFragment() {

    }

    public void setEmployee(int id) {
        this.employeeId = id;
    }

    private void fetchEmployee(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EmployeeService service = retrofit.create(EmployeeService.class);
        Call<Response> response =  service.getEmployee(id);
        response.enqueue(new Callback<Response>() {

            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful())
                {
                    Response resp = response.body();
                    List<Employee> employees = resp.getEmployees();
                    populateData(employees.get(0));
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

    private void populateData(Employee emp) {
        nameTv.setText(emp.getName().getFirst() + " " + emp.getName().getLast());
        cityTv.setText(emp.getLocation().getCity() + ", " + emp.getLocation().getCountry());
        phoneTv.setText(emp.getCell() + " / " + emp.getPhone());
        memberSinceTv.setText(emp.getRegistered().getDate());
        emailTv.setText(emp.getEmail());

        mapView.getMapAsync(map -> {
            googleMap = map;
            LatLng pos = new LatLng(
                    Double.parseDouble(emp.getLocation().getCoordinates().getLatitude()),
                    Double.parseDouble(emp.getLocation().getCoordinates().getLongitude()));
            googleMap.addMarker(new MarkerOptions().position(pos).title("Location").snippet("Location"));
            CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(12).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        });
    }

    private void initializeMap(View view, Bundle savedInstanceState) {
        mapView = (MapView) view.findViewById(R.id.mapView);
        Log.i("MapMap", mapView.toString());
        mapView.onCreate(savedInstanceState);

        mapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            employeeId = savedInstanceState.getInt("id");
        }

        View view = inflater.inflate(R.layout.fragment_employee_detail, container, false);
        initializeMap(view, savedInstanceState);
        nameTv = view.findViewById(R.id.employee_detail_name);
        cityTv = view.findViewById(R.id.employee_detail_city);
        phoneTv = view.findViewById(R.id.employee_detail_phone);
        memberSinceTv = view.findViewById(R.id.employee_detail_member_since);
        emailTv = view.findViewById(R.id.employee_detail_email);
        addAddressBookBtn = view.findViewById(R.id.employee_detail_add);

        addAddressBookBtn.setOnClickListener(v -> {
            DatabaseHelper db = new DatabaseHelper(getContext());
            v.setEnabled(false);
            if (db.addEmployeeToAddressBook(employeeId)) {
                Toast.makeText(getContext(), "Saved to address book!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "This employee is already in your address book!", Toast.LENGTH_SHORT).show();
            }
            v.setEnabled(true);
        });

        fetchEmployee(employeeId);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("id", employeeId);
    }
}