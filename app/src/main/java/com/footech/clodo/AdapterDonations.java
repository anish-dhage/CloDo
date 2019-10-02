package com.footech.clodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterDonations extends RecyclerView.Adapter<AdapterDonations.MyViewHolder> {
    private ArrayList<Donations> donList;
    private OnNoteListener mOnNoteListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView donor_name, donor_phone, donor_city;
        OnNoteListener onNoteListener;

        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            donor_name = (TextView) itemView.findViewById(R.id.donor_name);
            donor_phone = (TextView) itemView.findViewById(R.id.donor_phone);
            donor_city = (TextView) itemView.findViewById(R.id.donor_city);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public AdapterDonations(ArrayList<Donations> donList, OnNoteListener onNoteListener) {
        this.donList = donList;
        this.mOnNoteListener = onNoteListener;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_donations, parent, false);
        return new MyViewHolder(itemView, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Donations mdon = donList.get(position);
        holder.donor_name.setText(mdon.getDonor_name());
        holder.donor_city.setText(mdon.getDonor_email_id());
        holder.donor_phone.setText(mdon.getDonor_phone());

    }

    @Override
    public int getItemCount() {
        return donList.size();
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
