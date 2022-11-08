package com.example.blog.fragments.homeactivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blog.R;
import com.example.blog.model.HomeModel;

import java.util.List;

public class SearchPostAdapter extends RecyclerView.Adapter<SearchPostAdapter.SearchPostHolder> {

    private List<HomeModel> searchResultList;

    public SearchPostAdapter(List<HomeModel> list) {
        searchResultList = list;
    }

    @NonNull
    @Override
    public SearchPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_post_item, parent, false);
        return new SearchPostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchPostHolder holder, int position) {
        holder.authorTextView.setText(searchResultList.get(position).getAuthor_name());
        holder.titleTextView.setText(searchResultList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return searchResultList.size();
    }

    class SearchPostHolder extends RecyclerView.ViewHolder {

        private TextView authorTextView, titleTextView;

        public SearchPostHolder(@NonNull View itemView) {
            super(itemView);

            authorTextView = itemView.findViewById(R.id.author_TextView);
            titleTextView = itemView.findViewById(R.id.title_textView);
        }
    }
}
