package com.example.a_hashimoto.viewpager.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a_hashimoto.viewpager.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class PagerSampleActivity extends AppCompatActivity {

    BehaviorSubject<Long> behaviorSubject = BehaviorSubject.create();

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, PagerSampleActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_activity);

        Observable.just(System.currentTimeMillis())
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        behaviorSubject.onNext(aLong);
                    }
                });


        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new MyPagerViewAdapter(getSupportFragmentManager()));
    }

    private static class MyPagerViewAdapter extends FragmentPagerAdapter {


        public MyPagerViewAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public Fragment getItem(int position) {
//            int color = (int) (-16777216 + Math.pow(256, 3) - Math.pow(256, 2));
//            Log.d("fragmentColor", color + "");
            switch (position) {
                case 0:
                    return ColorFragment.newInstance(Color.RED);
//                    ColorFragment fragment = new ColorFragment();
//                    fragment.replaceView(LayoutInflater.from(fragment.getContext()),
//                    Color.RED);
//                    return fragment;
                case 1:
                    return ColorFragment.newInstance(Color.BLUE);
                case 2:
                    return ColorFragment.newInstance(Color.GREEN);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public static class ColorFragment extends Fragment {

        public static final String ARGS_COLOR = "color";

        TextView textView;
        private PagerSampleActivity activity;

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            activity = (PagerSampleActivity) context;
        }

        public static ColorFragment newInstance(int color) {
            ColorFragment fragment = new ColorFragment();
            Bundle args = new Bundle();
            args.putInt(ARGS_COLOR, color);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            int color = getArguments().getInt(ARGS_COLOR);
            final View view = getView();
//            getColorFromServer(color)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new DisposableObserver<Integer>() {
//                @Override
//                public void onNext(@NonNull Integer integer) {
////                    view.setBackgroundColor(integer);
//                }
//
//                @Override
//                public void onError(@NonNull Throwable e) {
//
//                }
//
//                @Override
//                public void onComplete() {
//
//                }
//            });

            activity.behaviorSubject
                    .zipWith(getColorFromServer(color), new BiFunction<Long, Integer, Pair<Long, Integer>>() {
                        @Override
                        public Pair<Long, Integer> apply(@NonNull Long aLong, @NonNull Integer integer) throws Exception {
                            Pair<Long, Integer> pair = new Pair<>(aLong, integer);
                            return pair;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new DisposableObserver<Pair<Long, Integer>>() {
                @Override
                public void onNext(@NonNull Pair<Long, Integer> pair) {
                    textView.setText(pair.first + "");
                    view.setBackgroundColor(pair.second);
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//            return super.onCreateView(inflater, container, savedInstanceState);
            LinearLayout linearLayout = new LinearLayout(inflater.getContext());
            textView = new TextView(getContext());
            linearLayout.addView(textView);
//            linearLayout.setBackgroundColor(color);
            return linearLayout;
        }

//        public View replaceView(LayoutInflater inflater, int color) {
//            return linearLayout;
//        }


        @Override
        public void onDetach() {
            super.onDetach();
            activity = null;
        }

        public static Observable<Integer> getColorFromServer(int color) {
            return Observable.just(color)
                    .delay(1, TimeUnit.SECONDS);
        }
    }
}
