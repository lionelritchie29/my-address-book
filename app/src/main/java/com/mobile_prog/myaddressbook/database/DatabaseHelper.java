package com.mobile_prog.myaddressbook.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.mobile_prog.myaddressbook.models.Employee;

import java.util.List;
import java.util.Vector;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "my_address_book";
    private static final int DB_VER = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE employees (" +
                "_id INTEGER PRIMARY KEY," +
                "first_name TEXT," +
                "last_name TEXT," +
                "city TEXT," +
                "country TEXT," +
                "latitude REAL," +
                "longitude REAL," +
                "email TEXT," +
                "registered_date TEXT," +
                "phone TEXT," +
                "cell TEXT," +
                "picture_url TEXT" +
                ");");

        sqLiteDatabase.execSQL("CREATE TABLE address_book (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "employee_id INTEGER);");
    }

    public void sync(List<Employee> apiEmployees) {
        getReadableDatabase().delete("employees", null, null);
        addEmployees(apiEmployees);
    }

    public boolean addEmployeeToAddressBook(int employeeId) {
        List<Integer> ids = getSavedEmployeeIds();

        if (ids.contains(employeeId)) {
            return false;
        }

        ContentValues values = new ContentValues();
        values.put("employee_id", employeeId);
        getReadableDatabase().insert("address_book", null, values);
        return true;
    }

    @SuppressLint("Range")
    public List<Integer> getSavedEmployeeIds() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM address_book", null);
        List<Integer> employeeIds = new Vector<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                employeeIds.add(cursor.getInt(cursor.getColumnIndex("employee_id")));
                cursor.moveToNext();
            }
        }

        return employeeIds;
    }

    public void addEmployees(List<Employee> employees) {
        for (Employee emp: employees) {
            ContentValues values = mapEmployee(emp);
            getReadableDatabase().insert("employees", null, values);
        }
    }

    @SuppressLint("Range")
    public List<Employee> getEmployees() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM employees", null);
        List<Employee> employees = new Vector<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Employee emp = new Employee();
                emp.setEmployeeId(cursor.getInt(cursor.getColumnIndex("_id")));
                emp.getName().setFirst(cursor.getString(cursor.getColumnIndex("first_name")));
                emp.getName().setLast(cursor.getString(cursor.getColumnIndex("last_name")));
                emp.getLocation().setCity(cursor.getString(cursor.getColumnIndex("city")));
                emp.getLocation().setCountry(cursor.getString(cursor.getColumnIndex("country")));
                emp.getLocation().getCoordinates().setLatitude(cursor.getString(cursor.getColumnIndex("latitude")));
                emp.getLocation().getCoordinates().setLongitude(cursor.getString(cursor.getColumnIndex("longitude")));
                emp.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                emp.setCell(cursor.getString(cursor.getColumnIndex("cell")));
                emp.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                emp.getPicture().setMedium(cursor.getString(cursor.getColumnIndex("picture_url")));
                emp.getRegistered().setDate(cursor.getString(cursor.getColumnIndex("registered_date")));

                employees.add(emp);
                cursor.moveToNext();
            }
        }

        return employees;
    }

    private ContentValues mapEmployee(Employee emp) {
        ContentValues values = new ContentValues();
        values.put("_id", emp.getEmployeeId());
        values.put("first_name", emp.getName().getFirst());
        values.put("last_name", emp.getName().getLast());
        values.put("city", emp.getLocation().getCity());
        values.put("country", emp.getLocation().getCountry());
        values.put("latitude", emp.getLocation().getCoordinates().getLatitude());
        values.put("longitude", emp.getLocation().getCoordinates().getLongitude());
        values.put("email", emp.getEmail());
        values.put("registered_date", emp.getRegistered().getDate());
        values.put("phone", emp.getPhone());
        values.put("cell", emp.getCell());
        values.put("picture_url", emp.getPicture().getMedium());
        return values;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
