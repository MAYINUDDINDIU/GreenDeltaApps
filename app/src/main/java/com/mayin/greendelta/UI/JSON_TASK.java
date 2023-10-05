package com.mayin.greendelta.UI;

import static com.mayin.greendelta.Employeelist.adapter;
import static com.mayin.greendelta.Employeelist.list;
import static com.mayin.greendelta.Employeelist.recyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSON_TASK extends AsyncTask<String,String,String> {

    private String result="";

    @Override
    protected String doInBackground(String... strings) {
        String states = "https://sopnerprakalpo.com/public/api/all_employee";
        URL state_url;
        HttpURLConnection myConnection = null;

        // Getting User data
        try {
            state_url = new URL(states);
            myConnection = (HttpURLConnection) state_url.openConnection();
            InputStream in = myConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

            String line = "";
            while(line!= null){
                line = bufferedReader.readLine();
                result+=line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        final ProgressDialog progressDialog = new ProgressDialog(adapter.context);
        progressDialog.setMessage("Please wait.......");
        progressDialog.show();



        try {
            JSONObject object=new JSONObject(result);
            JSONArray array=object.getJSONArray("all_info");

            progressDialog.dismiss();
            for (int i=0; i<array.length(); i++){

                JSONObject data=array.getJSONObject(i);

                Employee_Model model=new Employee_Model();

                model.setFirstname(data.getString("firstname"));
                model.setLastname(data.getString("lastname"));
                model.setPhone(data.getString("phone"));
                model.setDob(data.getString("dob"));
                model.setGender(data.getString("gender"));
                model.setId(data.getString("id"));
                model.setSkillname(data.getString("skillname"));
                model.setExperience(data.getString("experience"));
                model.setSkilllevel(data.getString("skilllevel"));


                list.add(model);
                // adapter that data has updated.
               adapter. notifyDataSetChanged();

            }

            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
