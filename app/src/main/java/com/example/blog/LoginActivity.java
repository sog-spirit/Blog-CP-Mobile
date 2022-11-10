package com.example.blog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.blog.databinding.ActivityLoginBinding;
import com.example.blog.fragments.homeactivity.CommentAdapter;
import com.example.blog.fragments.homeactivity.CommentDetailFragment;
import com.example.blog.fragments.homeactivity.CommentFragment;
import com.example.blog.fragments.homeactivity.PostDetailFragment;
import com.example.blog.fragments.loginactivity.LoginFragment;
import com.example.blog.fragments.loginactivity.RegisterFragment;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View viewRoot = activityLoginBinding.getRoot();
        setContentView(viewRoot);

        boolean isComment = getIntent().getBooleanExtra("isComment", false);
        boolean isEdit = getIntent().getBooleanExtra("isEdit", false);
        boolean isCommentDetail = getIntent().getBooleanExtra("isCommentDetail", false);
        if (isComment)
            setCurrentFragment(new CommentFragment());
        else if (isEdit)
            setCurrentFragment(new PostDetailFragment());
        else if (isCommentDetail)
            setCurrentFragment(new CommentDetailFragment());
        else
            setCurrentFragment(new LoginFragment());
    }

    public void setCurrentFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        if (fragment instanceof RegisterFragment) {
            fragmentTransaction.addToBackStack(null);
        }
        if (fragment instanceof CommentFragment) {
            int postId = getIntent().getIntExtra("postId", -1);

            Bundle bundle = new Bundle();
            bundle.putInt("postId", postId);
            fragment.setArguments(bundle);
        }
        if (fragment instanceof PostDetailFragment) {
            int postId = getIntent().getIntExtra("postId", -1);

            Bundle bundle = new Bundle();
            bundle.putInt("postId", postId);
            fragment.setArguments(bundle);
        }
        if (fragment instanceof CommentDetailFragment) {
            int commentId = getIntent().getIntExtra("commentId", -1);

            Bundle bundle = new Bundle();
            bundle.putInt("commentId", commentId);
            fragment.setArguments(bundle);
        }
        fragmentTransaction.replace(activityLoginBinding.frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}