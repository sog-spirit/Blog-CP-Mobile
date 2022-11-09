package com.example.blog.fragments.homeactivity;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blog.databinding.CommentItemBinding;
import com.example.blog.model.CommentModel;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentItemHolder> {
    private List<CommentModel> commentList;
    CommentItemBinding commentItemBinding;

    public CommentAdapter(List<CommentModel> list) {
        this.commentList = list;
    }

    @NonNull
    @Override
    public CommentItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        commentItemBinding = CommentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CommentItemHolder(commentItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentItemHolder holder, int position) {
        CommentModel commentItem = commentList.get(position);
        holder.commentItemBinding.commentUsername.setText(commentItem.getUsername());
        holder.commentItemBinding.commentContent.setText(commentItem.getContent());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class CommentItemHolder extends RecyclerView.ViewHolder {
        CommentItemBinding commentItemBinding;

        public CommentItemHolder(@NonNull CommentItemBinding binding) {
            super(binding.getRoot());

            this.commentItemBinding = binding;
        }
    }
}
