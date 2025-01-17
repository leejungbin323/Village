package com.example.village.screen.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.village.R;
import com.example.village.screen.post.Post;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    HomeViewModel viewModel;
    Context context;
    public HomeAdapter(Context context) {
        this.viewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(HomeViewModel.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.post_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productTitleTv.setText(viewModel.getProductArray().get(position).title);
        holder.sellerLocationTv.setText(viewModel.getProductArray().get(position).location);
        holder.productPriceTv.setText(viewModel.getProductArray().get(position).price);
        holder.homeImageIv.setImageURI(viewModel.getProductArray().get(position).HomeImageuri);
        Glide.with(holder.itemView)
                .load(viewModel.getProductArray().get(position).HomeImageuri)
                .into(holder.homeImageIv);
        holder.wholeLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, Post.class);
            Log.e("select","selected position : "+String.valueOf(position+1)+"real position"+viewModel.getProductArray().get(position).postNum);
            intent.putExtra("postNumber",viewModel.getProductArray().get(position).postNum);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return viewModel.getProductArray() != null ? viewModel.getProductArray().size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout wholeLayout;
        ImageView homeImageIv;
        TextView productTitleTv;
        TextView sellerLocationTv;
        TextView productPriceTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            wholeLayout = itemView.findViewById(R.id.wholeLayout);
            homeImageIv = itemView.findViewById(R.id.homeImageIv);
            productTitleTv = itemView.findViewById(R.id.productTitleTv);
            sellerLocationTv = itemView.findViewById(R.id.sellerLocationTv);
            productPriceTv = itemView.findViewById(R.id.productPriceTv);
        }
    }
}
