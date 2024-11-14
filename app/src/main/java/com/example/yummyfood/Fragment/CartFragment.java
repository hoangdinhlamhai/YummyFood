package com.example.yummyfood.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Intent;
import android.os.Bundle;
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

public class CartFragment extends Fragment {

    private ViewPager2 viewPager2;
    private View mView;

    public CartFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cart, container, false);

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int itemId = item.getItemId();
                    if (itemId == R.id.cart_nav) {
                        // Stay on the current Fragment (CartFragment)
                        return true;
                    } else if (itemId == R.id.home_nav) {
                        Intent intent = new Intent(getActivity(), HomepageUserActivity.class);
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