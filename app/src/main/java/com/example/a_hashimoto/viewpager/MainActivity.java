package com.example.a_hashimoto.viewpager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a_hashimoto.viewpager.ViewPager.PagerSampleActivity;
import com.example.a_hashimoto.viewpager.ViewPager.StatePagerSampleActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button pagerButton = (Button) findViewById(R.id.fragment_pager_adapter);
        Button statePagerButton = (Button) findViewById(R.id.fragment_state_pager_adapter);
        pagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(PagerSampleActivity.createIntent(getApplicationContext()));
            }
        });
        statePagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(StatePagerSampleActivity.createIntent(getApplicationContext()));
            }
        });
    }
}
