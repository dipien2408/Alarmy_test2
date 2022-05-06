package com.android.alarmy_test2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.android.alarmy_test2.NewsTags.HoroscopeFragment;
import com.android.alarmy_test2.NewsTags.SummaryFragment;
import com.android.alarmy_test2.NewsTags.TodayNewsFragment;
import com.android.alarmy_test2.NewsTags.WeatherFragment;
import com.android.alarmy_test2.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;



public class NewsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news, container, false);

        ViewPager2 viewPager = v.findViewById(R.id.view_pager);
        TabLayout tabLayout = v.findViewById(R.id.tab_layout);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this.getActivity());
        viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Tóm lược");
                    break;
                case 1:
                    tab.setText("Thời tiết hôm nay");
                    break;
                case 2:
                    tab.setText("Tử vi");
                    break;
                case 3:
                    tab.setText("Tin tức hôm nay");
                    break;
            }
        }).attach();

        return v;
    }

    private class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new SummaryFragment();

                case 1:
                    return new WeatherFragment();

                case 2:
                    return new HoroscopeFragment();

                case 3:
                    return new TodayNewsFragment();

                default:
                    return new SummaryFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }
}
