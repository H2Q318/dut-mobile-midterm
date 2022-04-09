package com.midterm.hoangquanghung.viewmodel;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.midterm.hoangquanghung.R;
import com.midterm.hoangquanghung.databinding.ItemBinding;
import com.midterm.hoangquanghung.model.Data;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private List<Data> data;

    public DataAdapter(List<Data> data) {
        this.data = data;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemBinding binding;
        public ViewHolder(@NonNull ItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", data.get(getAdapterPosition()));
                    Navigation.findNavController(view).navigate(R.id.detailFragment, bundle);
                }
            });
        }
    }
    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemBinding binding =
                DataBindingUtil.inflate(layoutInflater,
                        R.layout.item,
                        parent,
                        false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {
        Data dog = data.get(position);
        holder.binding.setData(dog);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}