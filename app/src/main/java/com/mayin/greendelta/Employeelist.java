package com.mayin.greendelta;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mayin.greendelta.UI.Adapter;
import com.mayin.greendelta.UI.Employee_Model;
import com.mayin.greendelta.UI.JSON_TASK;

import java.util.ArrayList;


public class Employeelist extends Fragment {

    public static RecyclerView recyclerView;
    EditText editText;
    public static ArrayList<Employee_Model> list=new ArrayList<>();
    public static Adapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_employeelist, container, false);
        editText=view.findViewById(R.id.search_id);
        recyclerView=view.findViewById(R.id.emp_rc);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                //your code on swipe refresh
                //we are checking networking connectivity

                JSON_TASK task=new JSON_TASK();
                task.execute();
                list.clear();
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        list.clear();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        if (isConnected()){

            //  Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
        }else {

            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


        adapter=new Adapter(getActivity(), list);
        JSON_TASK task=new JSON_TASK();
        task.execute();




        return view;
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

    private void filter(String text) {
        ArrayList<Employee_Model> filteredList = new ArrayList<>();

        for (Employee_Model item : list) {
            if (item.getFirstname().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
    }
}