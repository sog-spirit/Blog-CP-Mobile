package com.example.blog.fragments.homeactivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blog.databinding.CommentItemBinding;
import com.example.blog.model.CommentModel;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentItemHolder> {
    private List<CommentModel> commentList;
    CommentItemBinding commentItemBinding;
    OnCommentListener onCommentListener;

    public CommentAdapter(List<CommentModel> list, OnCommentListener onCommentListener) {
        this.commentList = list;
        this.onCommentListener = onCommentListener;
    }

    @NonNull
    @Override
    public CommentItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        commentItemBinding = CommentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CommentItemHolder(commentItemBinding, onCommentListener);
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

    class CommentItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CommentItemBinding commentItemBinding;
        OnCommentListener onCommentListener;

        public CommentItemHolder(@NonNull CommentItemBinding binding, OnCommentListener listener) {
            super(binding.getRoot());

            this.commentItemBinding = binding;
            this.onCommentListener = listener;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onCommentListener.onCommentClick(getAdapterPosition());
        }
    }

    public interface OnCommentListener {
        void onCommentClick(int position);
    }
}
