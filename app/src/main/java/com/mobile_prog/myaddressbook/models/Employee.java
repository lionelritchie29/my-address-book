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

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public EmployeeName getName() {
        return name;
    }

    public void setName(EmployeeName name) {
        this.name = name;
    }

    public EmployeeLocation getLocation() {
        return location;
    }

    public void setLocation(EmployeeLocation location) {
        this.location = location;
    }

    public RegisterInfo getRegistered() {
        return registered;
    }

    public void setRegistered(RegisterInfo registered) {
        this.registered = registered;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public PictureUrl getPicture() {
        return picture;
    }

    public void setPicture(PictureUrl picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public class EmployeeName {
        private String title;
        private String first;
        private String last;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }
    }

    public class EmployeeLocation {
        private String city;
        private String state;
        private String country;
        private String postcode;
        private Coordinate coordinates;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public Coordinate getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(Coordinate coordinates) {
            this.coordinates = coordinates;
        }
    }

    public class Coordinate {
        private String latitude;
        private String longitude;

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }
    }

    public class PictureUrl {
        private String large;
        private String medium;
        private String thumbnail;

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }

    public class RegisterInfo {
        private String date;
        private int age;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
