package com.example.blog.fragments.homeactivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blog.LoginActivity;
import com.example.blog.R;
import com.example.blog.databinding.HomePostItemBinding;
import com.example.blog.model.HomeModel;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder> {
    private List<HomeModel> postList;
    HomePostItemBinding homePostItemBinding;
    Context context;

    public HomeAdapter(List<HomeModel> list, Context _context) {
        postList = list;
        context = _context;
    }

    @NonNull
    @Override
    public HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        homePostItemBinding = HomePostItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HomeHolder(homePostItemBinding);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_post_item, null);
//        return new HomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHolder holder, int position) {
        HomeModel postItem = postList.get(position);
        holder.homePostItemBinding.authorTextView.setText(postItem.getAuthor_name());
        holder.homePostItemBinding.titleTextView.setText(postItem.getTitle());
        holder.homePostItemBinding.timeTextView.setText(postItem.getCreated_on().toString());
        holder.homePostItemBinding.contentTextView.setText(postItem.getContent());
        holder.homePostItemBinding.commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LoginActivity.class);
                intent.putExtra("postId", postItem.getId());
                intent.putExtra("isComment", true);
                context.startActivity(intent);
            }
        });
//        holder.authorTextView.setText(postList.get(position).getAuthor_name());
//        holder.timeTextView.setText(postList.get(position).getCreated_on().toString());
//        holder.contentTextView.setText(postList.get(position).getContent());
//        holder.titleTextView.setText(postList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class HomeHolder extends RecyclerView.ViewHolder {
        HomePostItemBinding homePostItemBinding;

        public HomeHolder(@NonNull HomePostItemBinding binding) {
            super(binding.getRoot());

            this.homePostItemBinding = binding;
        }
    }
}