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

    public SearchPostAdapter(List<HomeModel> list) {
        searchResultList = list;
    }

    @NonNull
    @Override
    public SearchPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_post_item, parent, false);
//        return new SearchPostHolder(view);
        searchPostItemBinding = SearchPostItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SearchPostHolder(searchPostItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchPostHolder holder, int position) {
//        holder.authorTextView.setText(searchResultList.get(position).getAuthor_name());
//        holder.titleTextView.setText(searchResultList.get(position).getTitle());
        HomeModel postItem = searchResultList.get(position);
        holder.searchPostItemBinding.authorTextView.setText(postItem.getAuthor_name());
        holder.searchPostItemBinding.titleTextView.setText(postItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return searchResultList.size();
    }

    class SearchPostHolder extends RecyclerView.ViewHolder {

        SearchPostItemBinding searchPostItemBinding;

        public SearchPostHolder(@NonNull SearchPostItemBinding binding) {
            super(binding.getRoot());

            this.searchPostItemBinding = binding;
        }
    }
}
