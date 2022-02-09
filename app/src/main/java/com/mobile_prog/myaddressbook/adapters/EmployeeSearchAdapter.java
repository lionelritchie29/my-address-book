package com.mobile_prog.myaddressbook.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobile_prog.myaddressbook.R;
import com.mobile_prog.myaddressbook.models.Employee;
import com.mobile_prog.myaddressbook.models.Response;
import com.mobile_prog.myaddressbook.services.ImageLoaderService;
import com.mobile_prog.myaddressbook.views.EmployeeSearchFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Callback;

public class EmployeeSearchAdapter extends RecyclerView.Adapter<EmployeeSearchAdapter.ViewHolder>{

    private List<Employee> employees;
    private View view;
    private EmployeeSearchFragment fragment;

    public EmployeeSearchAdapter(List<Employee> employees, EmployeeSearchFragment fragment) {
        this.employees = employees;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_card, parent, false);
        this.view = view;

        return new ViewHolder(view, fragment);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee emp = employees.get(position);
        holder.setEmployeeId(emp.getEmployeeId());
        String name = emp.getName().getFirst() + " " + emp.getName().getLast();
        String city = emp.getLocation().getCity() + ", " + emp.getLocation().getCountry();
        String phone = emp.getCell() + " / " + emp.getPhone();
        Date date = new Date();

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            date = format.parse(emp.getRegistered().getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String formattedDate = (String) android.text.format.DateFormat.format("MMM", date);
        formattedDate += ", ";
        formattedDate += (String) android.text.format.DateFormat.format("dd", date);

        holder.getEmployeeNameTxt().setText(name);
        holder.getEmployeeCityTxt().setText(city);
        holder.getEmployeePhoneTxt().setText(phone);
        holder.getEmployeeMemberSinceTxt().setText(formattedDate);
        Glide.with(view).load(emp.getPicture().getMedium()).placeholder(R.drawable.ic_launcher_background).into(holder.getEmployeeImgView());
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView employeeNameTxt;
        private TextView employeeCityTxt;
        private TextView employeePhoneTxt;
        private TextView employeeMemberSinceTxt;
        private ImageView employeeImg;
        private EmployeeSearchFragment frg;
        private int id;

        public ViewHolder(@NonNull View view, EmployeeSearchFragment frg) {
            super(view);
            this.frg = frg;
            this.employeeNameTxt = view.findViewById(R.id.employee_card_name);
            this.employeeCityTxt = view.findViewById(R.id.employee_card_city);
            this.employeePhoneTxt = view.findViewById(R.id.employee_card_phone);
            this.employeeMemberSinceTxt = view.findViewById(R.id.employee_card_member_since);
            this.employeeImg = view.findViewById(R.id.employee_card_img);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    frg.onEmployeeClicked(id);
                }
            });
        }

        public void setEmployeeId(int id) {
            this.id = id;
        }

        public TextView getEmployeeNameTxt() {
            return employeeNameTxt;
        }

        public TextView getEmployeeCityTxt() {
            return employeeCityTxt;
        }

        public TextView getEmployeePhoneTxt() {
            return employeePhoneTxt;
        }

        public TextView getEmployeeMemberSinceTxt() {
            return employeeMemberSinceTxt;
        }

        public ImageView getEmployeeImgView() {
            return employeeImg;
        }
    }
}
