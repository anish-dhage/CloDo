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
    private OnNoteListener mOnNoteListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView org_name, org_phone, org_city;
        OnNoteListener onNoteListener;

        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            org_name = (TextView) itemView.findViewById(R.id.name_org);
            org_phone = (TextView) itemView.findViewById(R.id.phone_org);
            org_city = (TextView) itemView.findViewById(R.id.city_org);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public AdapterOrganisation(ArrayList<OrganisationDetails> orgList, OnNoteListener onNoteListener) {
        this.orgList = orgList;
        this.mOnNoteListener = onNoteListener;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.org_recycler_layout, parent, false);


        return new MyViewHolder(itemView, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OrganisationDetails org_det = orgList.get(position);
        holder.org_name.setText(org_det.getName());
        holder.org_city.setText(org_det.getCity());
        holder.org_phone.append(org_det.getPhone());

    }

    @Override
    public int getItemCount() {
        return orgList.size();
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
