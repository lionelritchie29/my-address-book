package com.mobile_prog.myaddressbook.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile_prog.myaddressbook.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployeeSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeSearchFragment extends Fragment {

    public EmployeeSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_employee_search, container, false);
    }
}