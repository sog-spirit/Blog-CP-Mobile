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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.blog.R;
import com.example.blog.apiservice.AppService;
import com.example.blog.databinding.FragmentNewPostBinding;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPostFragment extends Fragment {
    private FragmentNewPostBinding fragmentNewPostBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentNewPostBinding = FragmentNewPostBinding.inflate(inflater, container, false);
        return fragmentNewPostBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.post_status, R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fragmentNewPostBinding.postStatusSpinner.setAdapter(spinnerAdapter);

        fragmentNewPostBinding.createPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = fragmentNewPostBinding.titleTextInput.getText().toString();
                String content = fragmentNewPostBinding.contentTextInput.getText().toString();
                if (title.isEmpty() || content.isEmpty()) {
                    if (title.isEmpty())
                        fragmentNewPostBinding.titleTextInputLayout.setError("Title is empty");
                    if (content.isEmpty())
                        fragmentNewPostBinding.contentTextInputLayout.setError("Content is empty");
                    return;
                }
                String status = fragmentNewPostBinding.postStatusSpinner.getSelectedItem().toString().toUpperCase();
                createNewPost(title, content, status);
            }
        });
        fragmentNewPostBinding.titleTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (fragmentNewPostBinding.titleTextInput.getText().length() > 0)
                    fragmentNewPostBinding.titleTextInputLayout.setError(null);
            }
        });
        fragmentNewPostBinding.contentTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (fragmentNewPostBinding.contentTextInput.getText().length() > 0)
                    fragmentNewPostBinding.contentTextInputLayout.setError(null);
            }
        });
    }

    private void createNewPost(String title, String content, String status) {
        HashMap<String, String> newPostData = new HashMap<>();
        newPostData.put("title", title);
        newPostData.put("content", content);
        newPostData.put("status", status);
        AppService.appService.createPost(newPostData).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Post uploaded successfully", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
                else {
                    Toast.makeText(getContext(), "An error has occurred while creating new post", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}