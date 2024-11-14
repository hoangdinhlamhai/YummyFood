package com.example.yummyfood.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.yummyfood.HomepageUserActivity;
import com.example.yummyfood.R;
import com.example.yummyfood.Profile_User;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class UserFragment extends Fragment {

    private ViewPager2 viewPager2;
    private View mView;

    public UserFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Sử dụng fragment_user.xml làm layout cho Fragment này
        View view = inflater.inflate(R.layout.activity_me_user, container, false);

        // Tìm BottomNavigationView trong layout
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.person_nav) {

                    Intent intent = new Intent(getActivity(), Profile_User.class);
                    startActivity(intent);
                    return true;
                } else {

                    return false;
                }
            });
        }

        return view;
    }
}
