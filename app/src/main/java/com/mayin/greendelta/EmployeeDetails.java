package com.mayin.greendelta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class EmployeeDetails extends AppCompatActivity {

    TextView edob;
    DatePickerDialog datePickerDialog;
    public static String date_birth;
    EditText efirstname,elastname,ephone,egender,eskillname,eexperience,eskilllevel;
    String Firstname,Lastname,Phone,Gender,Skillname,Experience,Skilllevel,url,e_id;
    Button eadd_information;
      ImageView edit,delete;
     TextView firstname,lastname,dob,gender,phone,skillname,experience,skilllevel;
     LinearLayout details_info,editpart;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        setTitle("EMPLOYEE DETAILS INFORMATION");
        ActionBar actionBar = getSupportActionBar();

        // Customize the back button
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        //initialize by id
        edit=findViewById(R.id.edit);
        delete=findViewById(R.id.delete);
        firstname=findViewById(R.id.firstname);
        lastname=findViewById(R.id.lastname);
        dob=findViewById(R.id.dob);
        gender=findViewById(R.id.gender);
        phone=findViewById(R.id.phone);
        skilllevel=findViewById(R.id.skilllevel);
        skillname=findViewById(R.id.skillname);
        experience=findViewById(R.id.experience);
        details_info=findViewById(R.id.details_info);
        editpart=findViewById(R.id.editpart);
        //initialize by id


        edob=findViewById(R.id.edob);
        efirstname=findViewById(R.id.efirstname);
        elastname=findViewById(R.id.elastname);
        ephone=findViewById(R.id.ephone);
        egender=findViewById(R.id.egender);
        eskillname=findViewById(R.id.eskillname);
        eexperience=findViewById(R.id.eexperince);
        eskilllevel=findViewById(R.id.skill_level);
        eadd_information=findViewById(R.id.edit_info);

        //received data
         e_id=getIntent().getStringExtra("id");
        String Firstname=getIntent().getStringExtra("firstname");
        String Lastname=getIntent().getStringExtra("lastname");
        String Dob=getIntent().getStringExtra("dob");
        String Gender=getIntent().getStringExtra("gender");
        String Phone=getIntent().getStringExtra("phone");
        String Skilllevel=getIntent().getStringExtra("skilllevel");
        String Skillname=getIntent().getStringExtra("skillname");
        String Eperience=getIntent().getStringExtra("experience");


        //received data

        firstname.setText(Firstname);
        lastname.setText(Lastname);
        dob.setText(Dob);
        gender.setText(Gender);
        phone.setText(Phone);
        skillname.setText(Skillname);
        skilllevel.setText(Skilllevel);
        experience.setText(Eperience);

//
        efirstname.setText(Firstname);
        elastname.setText(Lastname);
        edob.setText(Dob);
        egender.setText(Gender);
        ephone.setText(Phone);
        eskillname.setText(Skillname);
        eskilllevel.setText(Skilllevel);
        eexperience.setText(Eperience);



        eadd_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_employee_info();
            }
        });

        edob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int dayy = calendar.get(Calendar.DAY_OF_MONTH);
                final String[] set_date = new String[1];

                datePickerDialog = new DatePickerDialog(EmployeeDetails.this, new DatePickerDialog.OnDateSetListener() {
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


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                details_info.setVisibility(View.GONE);
                editpart.setVisibility(View.VISIBLE);

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EmployeeDetails.this,"delete",Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void update_employee_info() {
            url="https://sopnerprakalpo.com/public/api/update_employee/"+e_id;
            final ProgressDialog progressDialog = new ProgressDialog(EmployeeDetails.this);
            progressDialog.setMessage("Please wait.......");

            progressDialog.show();

            Firstname = efirstname.getText().toString().trim();
            Lastname = elastname.getText().toString().trim();
            Phone = ephone.getText().toString().trim();
            Gender = egender.getText().toString().trim();
            Skillname = eskillname.getText().toString().trim();
            Experience = eexperience.getText().toString().trim();
            Skilllevel = eskilllevel.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
//
                    efirstname.setText("");
                    elastname.setText("");
                    ephone.setText("");
                    egender.setText("");
                    eskillname.setText("");
                    eexperience.setText("");
                    eskilllevel.setText("");


                    try {
                        JSONObject obj = new JSONObject(response);
                        String message=obj.getString("message");
                        Toast.makeText(EmployeeDetails.this, message, Toast.LENGTH_SHORT).show();


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

                        Toast.makeText(EmployeeDetails.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
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
                    params.put("gender",Gender);
                    params.put("phone",Phone);
                    params.put("skillname",Skillname);
                    params.put("experience",Experience);
                    params.put("skilllevel",Skilllevel);
                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(EmployeeDetails.this);
            requestQueue.add(request);

        }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)EmployeeDetails.this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }
}