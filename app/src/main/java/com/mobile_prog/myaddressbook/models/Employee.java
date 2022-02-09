package com.mobile_prog.myaddressbook.models;

public class Employee {
    private int employeeId;
    private String gender;
    private EmployeeName name;
    private EmployeeLocation location;
    private RegisterInfo registered;
    private String phone;
    private String cell;
    private PictureUrl picture;
    private String email;

    private class EmployeeName {
        private String title;
        private String first;
        private String last;
    }

    private class EmployeeLocation {
        private String city;
        private String state;
        private String country;
        private String postcode;
        private Coordinate coordinates;
    }

    private class Coordinate {
        private String latitude;
        private String longitude;
    }

    private class PictureUrl {
        private String large;
        private String medium;
        private String thumbnail;
    }

    private class RegisterInfo {
        private String date;
        private int age;
    }
}
