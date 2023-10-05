package com.mayin.greendelta.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.mayin.greendelta.EmployeeDetails;
import com.mayin.greendelta.R;

import java.util.ArrayList;



public class Adapter extends RecyclerView.Adapter<Adapter.MY_VIEW> {

    Context context;
    ArrayList<Employee_Model> arrayList;

    public Adapter(Context context, ArrayList<Employee_Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public MY_VIEW onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View view= LayoutInflater.from(context).inflate(R.layout.sample,parent,false);
        final MY_VIEW my_view=new MY_VIEW(view);


        return my_view;
    }

    @Override
    public void onBindViewHolder(@NonNull MY_VIEW holder, int position) {

        holder.firstname.setText(arrayList.get(position).getFirstname().toString());
        holder.lastname.setText(arrayList.get(position).getLastname().toString());
        holder.phone.setText(arrayList.get(position).getPhone().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, EmployeeDetails.class);
                intent.putExtra("id",arrayList.get(position).getId());
                intent.putExtra("firstname",arrayList.get(position).getFirstname());
                intent.putExtra("lastname",arrayList.get(position).getLastname());
                intent.putExtra("dob",arrayList.get(position).getDob());
                intent.putExtra("gender",arrayList.get(position).getGender());
                intent.putExtra("phone",arrayList.get(position).getPhone());
                intent.putExtra("skillname",arrayList.get(position).getSkillname());
                intent.putExtra("experience",arrayList.get(position).getExperience());
                intent.putExtra("skilllevel",arrayList.get(position).getSkilllevel());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void filterList(ArrayList<Employee_Model> filteredList) {

        arrayList = filteredList;
        notifyDataSetChanged();
    }


    public class MY_VIEW extends RecyclerView.ViewHolder{
        TextView firstname,lastname,phone;
        public MY_VIEW(@NonNull View itemView) {
            super(itemView);

            firstname=itemView.findViewById(R.id.firstname);
            lastname=itemView.findViewById(R.id.lastname);
            phone=itemView.findViewById(R.id.phone);

        }
    }
}
