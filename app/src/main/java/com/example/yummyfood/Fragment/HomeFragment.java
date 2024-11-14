package com.example.yummyfood.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.yummyfood.CartActivity;
import com.example.yummyfood.HomepageUserActivity;
import com.example.yummyfood.Profile_User;
import com.example.yummyfood.R;
import com.example.yummyfood.category_user;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_homepage_user, container, false);

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int itemId = item.getItemId();
                    if (itemId == R.id.home_nav) {

                        return true;
                    } else if (itemId == R.id.cart_nav) {
                        Intent intent = new Intent(getActivity(), CartActivity.class);
                        startActivity(intent);
                        return true;
                    } else if (itemId == R.id.menu_nav) {
                        Intent intent = new Intent(getActivity(), category_user.class);
                        startActivity(intent);
                        return true;
                    } else if (itemId == R.id.person_nav) {
                        Intent intent = new Intent(getActivity(), Profile_User.class);
                        startActivity(intent);
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }

        return view;
    }
}