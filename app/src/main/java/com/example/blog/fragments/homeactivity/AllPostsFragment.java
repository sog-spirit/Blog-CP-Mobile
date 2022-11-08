package com.example.blog.fragments.homeactivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.blog.R;
import com.example.blog.apiservice.AppService;
import com.example.blog.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllPostsFragment extends Fragment {
    private RecyclerView recyclerView;
    HomeAdapter homeAdapter;
    private List<HomeModel> postList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_all_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeView(view);
        postList = new ArrayList<>();
        homeAdapter = new HomeAdapter(postList, getContext());
        recyclerView.setAdapter(homeAdapter);
        loadPosts();
    }

    private void initializeView(View view) {
        recyclerView = view.findViewById(R.id.allPosts_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void loadPosts() {
        AppService.appService.getAllPosts().enqueue(new Callback<List<HomeModel>>() {
            @Override
            public void onResponse(Call<List<HomeModel>> call, Response<List<HomeModel>> response) {
                if (response.isSuccessful()) {
                    postList.addAll(response.body());
                    homeAdapter.notifyDataSetChanged();
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
}