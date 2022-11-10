package com.example.blog.fragments.homeactivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.blog.LoginActivity;
import com.example.blog.R;
import com.example.blog.apiservice.AppService;
import com.example.blog.databinding.FragmentCommentBinding;
import com.example.blog.model.CommentModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentFragment extends Fragment implements CommentAdapter.OnCommentListener {
    private FragmentCommentBinding fragmentCommentBinding;
    CommentAdapter commentAdapter;
    private List<CommentModel> commentList;
    int postId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentCommentBinding = FragmentCommentBinding.inflate(inflater, container, false);
        return fragmentCommentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null)
            postId = getArguments().getInt("postId");
        fragmentCommentBinding.commentRecyclerView.setHasFixedSize(false);
        fragmentCommentBinding.commentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList, CommentFragment.this);
        fragmentCommentBinding.commentRecyclerView.setAdapter(commentAdapter);
        loadThisPostComments();
        fragmentCommentBinding.sendCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentContent = fragmentCommentBinding.commentEditText.getText().toString();
                if (commentContent.isEmpty()) {
                    Toast.makeText(getContext(), "Comment cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, Object> newCommentData = new HashMap<>();
                newCommentData.put("post", postId);
                newCommentData.put("content", commentContent);
                AppService.appService.createComment(newCommentData).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            fragmentCommentBinding.commentEditText.setText("");
                            Toast.makeText(getContext(), "Comment created!", Toast.LENGTH_LONG).show();
                            loadThisPostComments();
                        }
                        else {
                            Toast.makeText(getContext(), "Error occurred when creating comment", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadThisPostComments();
    }

    private void loadThisPostComments() {
        AppService.appService.getThisPostComments(postId).enqueue(new Callback<List<CommentModel>>() {
            @Override
            public void onResponse(Call<List<CommentModel>> call, Response<List<CommentModel>> response) {
                if (response.isSuccessful()) {
                    commentList.clear();
                    commentList.addAll(response.body());
                    commentAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(), "Unable to load post from server! Please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CommentModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onCommentClick(int position) {
        CommentModel comment = commentList.get(position);
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.putExtra("commentId", comment.getId());
        intent.putExtra("isCommentDetail", true);
        getContext().startActivity(intent);
    }
}