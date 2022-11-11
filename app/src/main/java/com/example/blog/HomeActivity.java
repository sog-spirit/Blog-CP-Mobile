package com.example.blog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.blog.databinding.ActivityHomeBinding;
import com.example.blog.fragments.homeactivity.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding activityHomeBinding;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        View viewRoot = activityHomeBinding.getRoot();
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        setContentView(viewRoot);
        initializeBottomNavTabs();
        activityHomeBinding.createPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.putExtra("isCreatePost", true);
                HomeActivity.this.startActivity(intent);
            }
        });
    }

    private void initializeBottomNavTabs() {
        activityHomeBinding.tabLayout.addTab(activityHomeBinding.tabLayout.newTab().setIcon(R.drawable.ic_baseline_home_24));
        activityHomeBinding.tabLayout.addTab(activityHomeBinding.tabLayout.newTab().setIcon(R.drawable.ic_baseline_search_24));
        activityHomeBinding.tabLayout.addTab(activityHomeBinding.tabLayout.newTab().setIcon(R.drawable.ic_baseline_view_list_24));
        activityHomeBinding.tabLayout.addTab(activityHomeBinding.tabLayout.newTab().setIcon(R.drawable.ic_baseline_person_24));

        activityHomeBinding.tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        activityHomeBinding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), activityHomeBinding.tabLayout.getTabCount());
        activityHomeBinding.homeViewPager.setAdapter(viewPagerAdapter);

        activityHomeBinding.homeViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(activityHomeBinding.tabLayout));
        activityHomeBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                activityHomeBinding.homeViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}