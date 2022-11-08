package com.example.blog.fragments.homeactivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    int numberOfTabs;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AllPostsFragment();
            case 1:
                return new SearchFragment();
            case 2:
                return new NewPostFragment();
            case 3:
                return new MyPostsFragment();
            case 4:
                return new ProfileFragment();
        }
        return new AllPostsFragment();
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
