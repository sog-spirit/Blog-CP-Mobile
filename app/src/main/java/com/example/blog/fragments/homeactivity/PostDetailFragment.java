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
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.blog.R;
import com.example.blog.apiservice.AppService;
import com.example.blog.databinding.FragmentPostDetailBinding;
import com.example.blog.model.CommentModel;
import com.example.blog.model.HomeModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailFragment extends Fragment {
    private FragmentPostDetailBinding fragmentPostDetailBinding;
    ArrayAdapter<CharSequence> spinnerAdapter;
    int postId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentPostDetailBinding = FragmentPostDetailBinding.inflate(inflater, container, false);
        return fragmentPostDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerAdapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.post_status, R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fragmentPostDetailBinding.postStatusSpinner.setAdapter(spinnerAdapter);

        if (getArguments() != null)
            postId = getArguments().getInt("postId");
        getPostDetail();
        fragmentPostDetailBinding.savePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = fragmentPostDetailBinding.titleTextInput.getText().toString();
                String content = fragmentPostDetailBinding.contentTextInput.getText().toString();
                if (title.isEmpty() || content.isEmpty()) {
                    if (title.isEmpty())
                        fragmentPostDetailBinding.titleTextInputLayout.setError("Title is empty");
                    if (content.isEmpty())
                        fragmentPostDetailBinding.contentTextInputLayout.setError("Content is empty");
                    return;
                }
                String status = fragmentPostDetailBinding.postStatusSpinner.getSelectedItem().toString().toUpperCase();
                updatePost(title, content, status);
            }
        });
        fragmentPostDetailBinding.titleTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (fragmentPostDetailBinding.titleTextInput.getText().length() > 0)
                    fragmentPostDetailBinding.titleTextInputLayout.setError(null);
            }
        });
        fragmentPostDetailBinding.contentTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (fragmentPostDetailBinding.contentTextInput.getText().length() > 0)
                    fragmentPostDetailBinding.contentTextInputLayout.setError(null);
            }
        });
    }

    private void getPostDetail() {
        HashMap<String, Integer> postIdData = new HashMap<>();
        postIdData.put("post_id", postId);
        AppService.appService.getPostDetail(postIdData).enqueue(new Callback<HomeModel>() {
            @Override
            public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {
                if (response.isSuccessful()) {
                    HomeModel post = response.body();
                    fragmentPostDetailBinding.titleTextInput.setText(post.getTitle());
                    fragmentPostDetailBinding.contentTextInput.setText(post.getContent());
                    String status = post.getStatus();

                    int spinnerPosition = spinnerAdapter.getPosition(status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase());
                    fragmentPostDetailBinding.postStatusSpinner.setSelection(spinnerPosition);
                }
                else {
                    Toast.makeText(getContext(), "An error has occurred while getting post detail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HomeModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void updatePost(String title, String content, String status) {
        HashMap<String, Object> postUpdateData = new HashMap<>();
        postUpdateData.put("post_id", postId);
        postUpdateData.put("title", title);
        postUpdateData.put("content", content);
        postUpdateData.put("status", status);
        AppService.appService.updatePost(postUpdateData).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Update post successfully", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getContext(), "An error has occurred while updating post", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}