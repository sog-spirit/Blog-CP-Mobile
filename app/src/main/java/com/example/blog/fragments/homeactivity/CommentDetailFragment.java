package com.example.blog.fragments.homeactivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.blog.apiservice.AppService;
import com.example.blog.databinding.FragmentCommentDetailBinding;
import com.example.blog.model.CommentModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentDetailFragment extends Fragment {
    private FragmentCommentDetailBinding fragmentCommentDetailBinding;
    int commentId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentCommentDetailBinding = FragmentCommentDetailBinding.inflate(inflater, container, false);
        return fragmentCommentDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            commentId = getArguments().getInt("commentId");
        }
        getCommentDetail();
        fragmentCommentDetailBinding.saveCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = fragmentCommentDetailBinding.contentTextInput.getText().toString();
                if (content.isEmpty()) {
                    fragmentCommentDetailBinding.contentTextInputLayout.setError("Comment content is empty");
                    return;
                }
                updateComment(commentId, content);
            }
        });
        fragmentCommentDetailBinding.deleteCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteComment();
                getActivity().finish();
            }
        });
        fragmentCommentDetailBinding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        fragmentCommentDetailBinding.contentTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (fragmentCommentDetailBinding.contentTextInput.getText().length() > 0)
                    fragmentCommentDetailBinding.contentTextInputLayout.setError(null);
            }
        });
    }

    private void getCommentDetail() {
        AppService.appService.getCommentById(commentId).enqueue(new Callback<CommentModel>() {
            @Override
            public void onResponse(Call<CommentModel> call, Response<CommentModel> response) {
                if (response.isSuccessful()) {
                    CommentModel comment = response.body();
                    fragmentCommentDetailBinding.contentTextInput.setText(comment.getContent());
                }
                else {
                    Toast.makeText(getContext(), "Unable to load comment detail from server", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CommentModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void updateComment(int commentId, String content) {
        HashMap<String, Object> updateCommentData = new HashMap<>();
        updateCommentData.put("comment_id", commentId);
        updateCommentData.put("content", content);
        AppService.appService.updateComment(updateCommentData).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Comment edit successfully", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getContext(), "Unable to edit comment", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private void deleteComment() {
        HashMap<String, Integer> commentIdData = new HashMap<>();
        commentIdData.put("comment_id", commentId);
        AppService.appService.deleteComment(commentIdData).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Comment deleted successfully", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getContext(), "Unable to delete comment", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}