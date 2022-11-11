package com.example.blog.fragments.homeactivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blog.R;
import com.example.blog.databinding.SearchPostItemBinding;
import com.example.blog.model.HomeModel;

import java.util.List;

public class SearchPostAdapter extends RecyclerView.Adapter<SearchPostAdapter.SearchPostHolder> {

    private List<HomeModel> searchResultList;
    SearchPostItemBinding searchPostItemBinding;
    OnPostListener onPostListener;


    public SearchPostAdapter(List<HomeModel> list, OnPostListener listener) {
        this.searchResultList = list;
        this.onPostListener = listener;
    }

    @NonNull
    @Override
    public SearchPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        searchPostItemBinding = SearchPostItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SearchPostHolder(searchPostItemBinding, onPostListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchPostHolder holder, int position) {
        HomeModel postItem = searchResultList.get(position);
        holder.searchPostItemBinding.authorTextView.setText(postItem.getAuthor_name());
        holder.searchPostItemBinding.titleTextView.setText(postItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return searchResultList.size();
    }

    class SearchPostHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        SearchPostItemBinding searchPostItemBinding;
        OnPostListener onPostListener;

        public SearchPostHolder(@NonNull SearchPostItemBinding binding, OnPostListener listener) {
            super(binding.getRoot());

            this.searchPostItemBinding = binding;
            this.onPostListener = listener;
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
