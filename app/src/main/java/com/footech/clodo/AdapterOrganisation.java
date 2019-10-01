package com.footech.clodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterOrganisation extends RecyclerView.Adapter<AdapterOrganisation.MyViewHolder> {
    private ArrayList<OrganisationDetails> orgList;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView org_name, org_phone, org_city;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            org_name = (TextView) itemView.findViewById(R.id.name_org);
            org_phone = (TextView) itemView.findViewById(R.id.phone_org);
            org_city = (TextView) itemView.findViewById(R.id.city_org);
        }
    }
    public AdapterOrganisation(ArrayList<OrganisationDetails> orgList) {
        this.orgList = orgList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.org_recycler_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OrganisationDetails org_det = orgList.get(position);
        holder.org_name.setText(org_det.getName());
        holder.org_city.setText(org_det.getCity());
        holder.org_phone.setText(org_det.getPhone());
    }

    @Override
    public int getItemCount() {
        return orgList.size();
    }
}
