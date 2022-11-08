package com.example.blog.fragments.homeactivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    SearchView postSearchView;
    RecyclerView searchResultRecyclerView;

    SearchPostAdapter searchPostAdapter;
    private List<HomeModel> searchPostList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeUiComponent(view);
        loadPosts();
        postSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryPostsByTitle(query);
//                Toast.makeText(getContext(), query, Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals(""))
                    loadPosts();
                return false;
            }
        });
    }

    private void initializeUiComponent(View view) {
        postSearchView = view.findViewById(R.id.postSearchView);
        searchResultRecyclerView = view.findViewById(R.id.searchResult_recyclerView);
        searchResultRecyclerView.setHasFixedSize(true);
        searchResultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchPostList = new ArrayList<>();
        searchPostAdapter = new SearchPostAdapter(searchPostList);
        searchResultRecyclerView.setAdapter(searchPostAdapter);
    }

    private void loadPosts() {
        AppService.appService.getAllPosts().enqueue(new Callback<List<HomeModel>>() {
            @Override
            public void onResponse(Call<List<HomeModel>> call, Response<List<HomeModel>> response) {
                if (response.isSuccessful()) {
                    searchPostList.clear();
                    if (response.body() != null)
                        searchPostList.addAll(response.body());
                    searchPostAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getActivity(), "Unable to query post from server! Please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<HomeModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void queryPostsByTitle(String titleQuery) {
        AppService.appService.getPostsByTitleQuery(titleQuery).enqueue(new Callback<List<HomeModel>>() {
            @Override
            public void onResponse(Call<List<HomeModel>> call, Response<List<HomeModel>> response) {
                if (response.isSuccessful()) {
                    searchPostList.clear();
                    if (response.body() != null)
                        searchPostList.addAll(response.body());
                    searchPostAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getActivity(), "Unable to query post from server! Please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<HomeModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}