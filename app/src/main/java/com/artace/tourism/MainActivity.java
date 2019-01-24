package com.artace.tourism;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.artace.tourism.utils.KenBurnsEffect;
import com.goka.kenburnsview.KenBurnsView;
import com.goka.kenburnsview.LoopViewPager;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Integer[] IMAGE_RESOUCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IMAGE_RESOUCE = new Integer[]{
                R.drawable.dashboard_header1,
                R.drawable.dashboard_header2,
                R.drawable.dashboard_header3,
        };
        initializeKenBurnsView();
    }

    private void initializeKenBurnsView(){
        // KenBurnsView
        final KenBurnsEffect kenBurnsView = findViewById(R.id.ken_burns_view);
         kenBurnsView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // File path, or a uri or url
        List<Integer> urls = Arrays.asList(IMAGE_RESOUCE);
        kenBurnsView.initResourceIDs(urls);

        // ResourceID
        //List<Integer> resourceIDs = Arrays.asList(SampleImages.IMAGES_RESOURCE);
        //kenBurnsView.loadResourceIDs(resourceIDs);

        // MIX (url & id)
        //List<Object> mixingList = Arrays.asList(SampleImages.IMAGES_MIX);
        //kenBurnsView.loadMixing(mixingList);

        // LoopViewListener
        LoopViewPager.LoopViewPagerListener listener = new LoopViewPager.LoopViewPagerListener() {
            @Override
            public View OnInstantiateItem(int page) {
                TextView counterText = new TextView(getApplicationContext());
                counterText.setText("");
                counterText.setVisibility(View.GONE);
                return counterText;
            }

            @Override
            public void onPageScroll(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                kenBurnsView.forceSelected(position);
            }

            @Override
            public void onPageScrollChanged(int page) {
            }
        };

        // LoopView
        LoopViewPager loopViewPager = new LoopViewPager(this, urls.size(), listener);

        //LoopViewPager loopViewPager = new LoopViewPager(this, resourceIDs.size(), listener);

        //LoopViewPager loopViewPager = new LoopViewPager(this, mixingList.size(), listener);


        FrameLayout viewPagerFrame = (FrameLayout) findViewById(R.id.view_pager_frame);
        viewPagerFrame.addView(loopViewPager);

        kenBurnsView.setPager(loopViewPager);
    }

}
