package com.example.blog.fragments.homeactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blog.R;
import com.example.blog.model.HomeModel;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder> {
    private List<HomeModel> postList;
    Context context;

    public HomeAdapter(List<HomeModel> list, Context _context) {
        postList = list;
        context = _context;
    }

    @NonNull
    @Override
    public HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_post_item, null);
        return new HomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHolder holder, int position) {
        holder.authorTextView.setText(postList.get(position).getAuthor_name());
        holder.timeTextView.setText(postList.get(position).getCreated_on().toString());
        holder.contentTextView.setText(postList.get(position).getContent());
//        holder.titleTextView.setText(postList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class HomeHolder extends RecyclerView.ViewHolder {
        private TextView authorTextView, timeTextView, contentTextView, titleTextView;
        private ImageButton commentButton;

        public HomeHolder(@NonNull View itemView) {
            super(itemView);

            authorTextView = itemView.findViewById(R.id.author_TextView);
//            titleTextView = itemView.findViewById(R.id.title_TextView);
            timeTextView = itemView.findViewById(R.id.time_TextView);
            contentTextView = itemView.findViewById(R.id.content_textView);
            commentButton = itemView.findViewById(R.id.commentButton);
        }
    }
}