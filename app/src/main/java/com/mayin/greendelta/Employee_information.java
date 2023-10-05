package com.mayin.greendelta;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class Employee_information extends Fragment {
    TextView dob;
    DatePickerDialog datePickerDialog;
    public static String date_birth;
    EditText firstname,lastname,phone,gender,skillname,experience,skilllevel;
    String Firstname,Lastname,Phone,Gender,Skillname,Experience,Skilllevel;
    Button add_information;
    String url = "https://sopnerprakalpo.com/public/api/add-information";
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_employee_information, container, false);

        dob=v.findViewById(R.id.dob);
        firstname=v.findViewById(R.id.firstname);
        lastname=v.findViewById(R.id.lastname);
        phone=v.findViewById(R.id.phone);
        gender=v.findViewById(R.id.gender);
        skillname=v.findViewById(R.id.skillname);
        experience=v.findViewById(R.id.experince);
        skilllevel=v.findViewById(R.id.skill_level);
        add_information=v.findViewById(R.id.add_info);



        add_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_employee_info();

            }
        });


        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int dayy = calendar.get(Calendar.DAY_OF_MONTH);
                final String[] set_date = new String[1];

                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        set_date[0] = year + "-" + (month+1) + "-" + dayOfMonth;
                        dob.setText(set_date[0]);

                        Log.d("From_Date: ", set_date[0]);
                        date_birth = set_date[0];
                    }
                }, year, month, dayy);

                datePickerDialog.show();
            }
        });

        return v;
    }



    public void add_employee_info() {

        if(firstname.getText().toString().equals("")){
            firstname.setError("Enter First Name");
            firstname.requestFocus();
            return;
        }
        else if(lastname.getText().toString().equals("")){
            lastname.setError("Enter Last Name");
            lastname.requestFocus();
            return;
        }
        else if(phone.getText().toString().equals("")){
            phone.setError("Enter Phone");
            phone.requestFocus();
            return;
        }
        else if(gender.getText().toString().equals("")){
            gender.setError("Enter Gender");
            gender.requestFocus();
            return;
        }
        else if(skillname.getText().toString().equals("")){
            skillname.setError("Enter Skill Name");
            skillname.requestFocus();
            return;
        }
        else if(experience.getText().toString().equals("")){
            experience.setError("Enter Experience");
            experience.requestFocus();
            return;
        }

        else if(skilllevel.getText().toString().equals("")){
            skilllevel.setError("Enter Skill level");
            skilllevel.requestFocus();
            return;
        }
        else{


            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait.......");

            progressDialog.show();

            Firstname = firstname.getText().toString().trim();
            Lastname = lastname.getText().toString().trim();
            Phone = phone.getText().toString().trim();
            Gender = gender.getText().toString().trim();
            Skillname = skillname.getText().toString().trim();
            Experience = experience.getText().toString().trim();
            Skilllevel = skilllevel.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
//
                    firstname.setText("");
                    lastname.setText("");
                    phone.setText("");
                    gender.setText("");
                    skillname.setText("");
                    experience.setText("");
                    skilllevel.setText("");


                    try {
                        JSONObject obj = new JSONObject(response);
                        String message=obj.getString("message");
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

//



                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }



                }
            },new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    if (isConnected()){
                        //  Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
                    }else {

                        Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
//                    Toast.makeText(login.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("firstname",Firstname);
                    params.put("lastname",Lastname);
                    params.put("dob",date_birth);
                    params.put("gender",Gender);
                    params.put("phone",Phone);
                    params.put("skillname",Skillname);
                    params.put("experience",Experience);
                    params.put("skilllevel",Skilllevel);
                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(request);

        }
    }

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }
}