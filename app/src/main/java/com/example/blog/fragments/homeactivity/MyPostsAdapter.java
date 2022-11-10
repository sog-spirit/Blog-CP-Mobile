package com.example.blog.fragments.homeactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blog.databinding.MyPostItemBinding;
import com.example.blog.model.HomeModel;

import java.util.List;

public class MyPostsAdapter extends RecyclerView.Adapter<MyPostsAdapter.MyPostsHolder> {

    private List<HomeModel> myPostsList;
    MyPostItemBinding myPostItemBinding;
    Context context;
    OnPostListener onPostListener;

    public MyPostsAdapter(List<HomeModel> list, Context _context, OnPostListener postListener) {
        myPostsList = list;
        context = _context;
        onPostListener = postListener;
    }

    @NonNull
    @Override
    public MyPostsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        myPostItemBinding = MyPostItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyPostsHolder(myPostItemBinding, onPostListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostsHolder holder, int position) {
        HomeModel postItem = myPostsList.get(position);
        holder.myPostItemBinding.titleTextView.setText(postItem.getTitle());
        holder.myPostItemBinding.contentTextView.setText(postItem.getContent());
        holder.myPostItemBinding.timeTextView.setText(postItem.getCreated_on().toString());
        holder.myPostItemBinding.statusTextView.setText(postItem.getStatus());
    }

    @Override
    public int getItemCount() {
        return myPostsList.size();
    }

    class MyPostsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        MyPostItemBinding myPostItemBinding;
        OnPostListener onPostListener;

        public MyPostsHolder(@NonNull MyPostItemBinding binding, OnPostListener postListener) {
            super(binding.getRoot());

            this.myPostItemBinding = binding;
            this.onPostListener = postListener;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onPostListener.onPostClick(getAdapterPosition());
        }
    }

    public interface OnPostListener {
        void onPostClick(int position);
    }
}
