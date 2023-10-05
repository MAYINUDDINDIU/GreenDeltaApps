package com.mayin.greendelta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    EditText username,password;
    String user_Name,Password;
    Button register,login;
    String url = "https://sopnerprakalpo.com/public/api/register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setTitle("REGISTER");
        ActionBar actionBar = getSupportActionBar();

        // Customize the back button
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        username=findViewById(R.id.user_name);
        password=findViewById(R.id.password);

        register=findViewById(R.id.sign_up);
        login=findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Signup.this,MainActivity.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });






    }

    public void register() {

        if(username.getText().toString().equals("")){
            username.setError("Enter Username");
            username.requestFocus();
            return;
        }
        else if(password.getText().toString().equals("")){
            password.setError("Enter Password");
            password.requestFocus();
            return;
        }
        else{


            final ProgressDialog progressDialog = new ProgressDialog(Signup.this);
            progressDialog.setMessage("Please wait.......");

            progressDialog.show();

            user_Name = username.getText().toString().trim();
            Password = password.getText().toString().trim();


            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
//
                    username.setText("");
                    password.setText("");



                    try {
                        JSONObject obj = new JSONObject(response);
                        String message=obj.getString("message");
                        Toast.makeText(Signup.this, message, Toast.LENGTH_SHORT).show();


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

                        Toast.makeText(Signup.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
//                    Toast.makeText(login.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("username",user_Name);
                    params.put("password",Password);

                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Signup.this);
            requestQueue.add(request);

        }
    }
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)Signup.this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
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

}