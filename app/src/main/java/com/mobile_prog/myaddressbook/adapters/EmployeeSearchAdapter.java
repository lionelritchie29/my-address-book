package com.mobile_prog.myaddressbook.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.mobile_prog.myaddressbook.views.MainActivity;

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
    private boolean forContact;

    public EmployeeSearchAdapter(List<Employee> employees, EmployeeSearchFragment fragment, boolean forContract) {
        this.employees = employees;
        this.fragment = fragment;
        this.forContact = forContract;
    }

    public EmployeeSearchAdapter(List<Employee> employees, boolean forContract) {
        this.employees = employees;
        this.forContact = forContract;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_card, parent, false);
        this.view = view;

        return new ViewHolder(view, fragment, forContact);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee emp = employees.get(position);
        holder.setEmployeeId(emp.getEmployeeId());
        holder.setEmployee(emp);
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
        private TextView phoneHeader;
        private TextView memberHeader;
        private ImageView employeeImg;
        private Button callBtn;
        private Button emailBtn;
        private EmployeeSearchFragment frg;
        private int id;
        private Employee emp;

        public ViewHolder(@NonNull View view, EmployeeSearchFragment frg, boolean forContract) {
            super(view);
            this.frg = frg;
            this.employeeNameTxt = view.findViewById(R.id.employee_card_name);
            this.employeeCityTxt = view.findViewById(R.id.employee_card_city);
            this.employeePhoneTxt = view.findViewById(R.id.employee_card_phone);
            this.employeeMemberSinceTxt = view.findViewById(R.id.employee_card_member_since);
            this.employeeImg = view.findViewById(R.id.employee_card_img);
            this.callBtn = view.findViewById(R.id.employee_card_call_btn);
            this.emailBtn = view.findViewById(R.id.employee_card_email_btn);
            this.phoneHeader = view.findViewById(R.id.employee_card_phone_header);
            this.memberHeader = view.findViewById(R.id.employee_card_member_header);

            if (forContract) {
                this.callBtn.setVisibility(View.VISIBLE);
                this.emailBtn.setVisibility(View.VISIBLE);
                this.employeePhoneTxt.setVisibility(View.GONE);
                this.employeeMemberSinceTxt.setVisibility(View.GONE);
                this.memberHeader.setVisibility(View.GONE);
                this.phoneHeader.setVisibility(View.GONE);
            } else {
                this.callBtn.setVisibility(View.GONE);
                this.emailBtn.setVisibility(View.GONE);
                this.employeePhoneTxt.setVisibility(View.VISIBLE);
                this.employeeMemberSinceTxt.setVisibility(View.VISIBLE);
                this.memberHeader.setVisibility(View.VISIBLE);
                this.phoneHeader.setVisibility(View.VISIBLE);
            }


            view.setOnClickListener(v -> {
                if (frg != null) {
                    frg.onEmployeeClicked(id);
                }
            });

            callBtn.setOnClickListener( v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + emp.getPhone()));
                v.getContext().startActivity(intent);
            });

            emailBtn.setOnClickListener( v -> {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + emp.getEmail()));
                intent.putExtra(Intent.EXTRA_EMAIL, emp.getEmail());
                intent.putExtra(Intent.EXTRA_SUBJECT, "Hello");
                v.getContext().startActivity(intent);
            });
        }

        public void setEmployee(Employee emp) {
            this.emp = emp;
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
