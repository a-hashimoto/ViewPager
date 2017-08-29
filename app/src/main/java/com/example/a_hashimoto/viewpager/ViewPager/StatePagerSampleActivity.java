package com.example.a_hashimoto.viewpager.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.a_hashimoto.viewpager.R;

import java.util.Random;

/**
 * Created by a-hashimoto on 2017/08/28.
 */

public class StatePagerSampleActivity extends AppCompatActivity {

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, StatePagerSampleActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.state_sample_activity);

        ViewPager viewPager = (ViewPager) findViewById(R.id.state_view_pager);
        viewPager.setAdapter(new SampleViewPagerAdapter(getSupportFragmentManager()));
    }

    private static class SampleViewPagerAdapter extends FragmentStatePagerAdapter {

        private final Random random = new Random();

        public SampleViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            int color = (0xFF << 24) | random.nextInt(0x01000000);
            Log.d("fragmentColor", color + "");
            return SampleViewPagerFragment.newInstance(color);
        }

        @Override
        public int getCount() {
            return 30;
        }
    }

    public static class SampleViewPagerFragment extends Fragment {

        private static final String ARGS_COLOR = "color";
        public static final String KEY_COLOR = "color";
        private int color;

        public static SampleViewPagerFragment newInstance(int color) {
            SampleViewPagerFragment fragment = new SampleViewPagerFragment();
            Bundle args = new Bundle();
            args.putInt(ARGS_COLOR, color);
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            if (savedInstanceState != null) {
                color = savedInstanceState.getInt(KEY_COLOR);
            } else {
                color = getArguments() == null ? Color.WHITE
                        : getArguments().getInt(ARGS_COLOR, Color.WHITE);
            }
            LinearLayout linearLayout = new LinearLayout(inflater.getContext());
            linearLayout.setBackgroundColor(color);
            return linearLayout;
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putInt(KEY_COLOR, color);
        }
    }

}
