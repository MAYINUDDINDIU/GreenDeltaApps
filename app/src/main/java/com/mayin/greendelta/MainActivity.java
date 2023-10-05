package com.mayin.greendelta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button login,signup;
    EditText username, password;
    String U_name, U_Pass;
    ProgressBar loading;
    SharedPreferences pref;
    String url = "https://sopnerprakalpo.com/public/api/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("user_details",MODE_PRIVATE);
        loading = findViewById(R.id.loading);
        username = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup=findViewById(R.id.sign_up);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Signup.class);
                startActivity(intent);
            }
        });

        if(pref.contains("username") && pref.contains("password")){
            Intent intent=new Intent(MainActivity.this,Dashboard.class);
            startActivity(intent);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }


    public void login() {

        if(username.getText().toString().equals("")){
            username.setError("Enter student ID");
            username.requestFocus();
            return;
        }
        else if(password.getText().toString().equals("")){
            password.setError("Enter Password");
            password.requestFocus();
            return;
        }
        else{


            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait.......");

            progressDialog.show();

            U_name = username.getText().toString().trim();
            U_Pass = password.getText().toString();

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
//
//                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();

                    try {
                        JSONObject obj = new JSONObject(response);
                        String username=obj.getString("username");
                        String password=obj.getString("password");
                        String status=obj.getString("status");



                        if (status.equalsIgnoreCase("1")){

                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("username",username);
                            editor.putString("password",password);
                            editor.putString("status",status);
                            editor.commit();
                          Intent intent=new Intent(MainActivity.this,Dashboard.class);
                          startActivity(intent);
                        }
                        else if(status.isEmpty()) {
                            Toast.makeText(MainActivity.this,"failed",Toast.LENGTH_SHORT).show();

                        }

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

                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
//                    Toast.makeText(login.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("username",U_name);
                    params.put("password",U_Pass);
                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(request);

        }
    }
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    }
