package com.kadircetin.retrofitdatafetcher.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kadircetin.retrofitdatafetcher.databinding.RowLayoutBinding;
import com.kadircetin.retrofitdatafetcher.model.CryptoModel;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {
    private ArrayList<CryptoModel> cryptoModels;
    private String[] colors = {"#a3ff00", "#ff00aa", "#b4a7d6", "#a4c2f4", "#8ee5ee", "#cd950c", "#f5f5f5", "#f47932"};
    public RecyclerViewAdapter(ArrayList<CryptoModel> cryptoModels) {
        this.cryptoModels = cryptoModels;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowLayoutBinding binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RowHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        holder.bind(cryptoModels.get(position),colors,position);
    }

    @Override
    public int getItemCount() {
        return cryptoModels.size();
    }
    public class RowHolder extends RecyclerView.ViewHolder {
        private RowLayoutBinding binding;
        public RowHolder(RowLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(CryptoModel cryptoModel, String[] colors, int position) {
            binding.getRoot().setBackgroundColor(Color.parseColor(colors[position % 8]));
            binding.textName.setText(cryptoModel.currency);
            binding.textPrice.setText(cryptoModel.price);
        }
    }
}
