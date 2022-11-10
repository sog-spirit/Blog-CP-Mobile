package com.example.blog.fragments.homeactivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.blog.LoginActivity;
import com.example.blog.R;
import com.example.blog.apiservice.AppService;
import com.example.blog.databinding.FragmentMyPostsBinding;
import com.example.blog.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPostsFragment extends Fragment implements MyPostsAdapter.OnPostListener {
    private FragmentMyPostsBinding fragmentMyPostsBinding;
    MyPostsAdapter myPostsAdapter;
    private List<HomeModel> myPostsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMyPostsBinding = FragmentMyPostsBinding.inflate(inflater, container, false);
        return fragmentMyPostsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentMyPostsBinding.allPostsRecyclerView.setHasFixedSize(false);
        fragmentMyPostsBinding.allPostsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myPostsList = new ArrayList<>();
        myPostsAdapter = new MyPostsAdapter(myPostsList, getContext(), MyPostsFragment.this);
        fragmentMyPostsBinding.allPostsRecyclerView.setAdapter(myPostsAdapter);
        loadCurrentUserPosts();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCurrentUserPosts();
    }

    private void loadCurrentUserPosts() {
        AppService.appService.getPostsByCurrentUser().enqueue(new Callback<List<HomeModel>>() {
            @Override
            public void onResponse(Call<List<HomeModel>> call, Response<List<HomeModel>> response) {
                if (response.isSuccessful()) {
                    myPostsList.clear();
                    myPostsList.addAll(response.body());
                    myPostsAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getActivity(), "Unable to load post from server! Please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<HomeModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onPostClick(int position) {
        HomeModel post = myPostsList.get(position);
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.putExtra("postId", post.getId());
        intent.putExtra("isEdit", true);
        getContext().startActivity(intent);
    }
}