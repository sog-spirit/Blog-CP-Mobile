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
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPostFragment extends Fragment {
    private EditText titleTextInput, contentTextInput;
    private TextInputLayout titleTextInputLayout, contentTextInputLayout;
    private Spinner postStatusSpinner;
    private Button createPostButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_new_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeView(view);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.post_status, R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        postStatusSpinner.setAdapter(spinnerAdapter);

        createPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleTextInput.getText().toString();
                String content = contentTextInput.getText().toString();
                if (title.isEmpty() || content.isEmpty()) {
                    if (title.isEmpty())
                        titleTextInputLayout.setError("Title is empty");
                    if (content.isEmpty())
                        contentTextInputLayout.setError("Content is empty");
                    return;
                }
                String status = postStatusSpinner.getSelectedItem().toString().toUpperCase();
                createNewPost(title, content, status);
            }
        });
        titleTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (titleTextInput.getText().length() > 0)
                    titleTextInputLayout.setError(null);
            }
        });
        contentTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (contentTextInput.getText().length() > 0)
                    contentTextInputLayout.setError(null);
            }
        });
    }

    private void initializeView(View view) {
        titleTextInput = view.findViewById(R.id.title_textInput);
        titleTextInputLayout = view.findViewById(R.id.title_textInputLayout);
        contentTextInput = view.findViewById(R.id.content_textInput);
        contentTextInputLayout = view.findViewById(R.id.content_textInputLayout);
        postStatusSpinner = view.findViewById(R.id.postStatus_spinner);
        createPostButton = view.findViewById(R.id.createPostButton);
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
                    titleTextInput.setText("");
                    contentTextInput.setText("");
                    postStatusSpinner.setSelection(0);
                    Toast.makeText(getContext(), "Post uploaded successfully", Toast.LENGTH_LONG).show();
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