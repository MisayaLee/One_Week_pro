package com.example.misaya.one_week_pro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.misaya.one_week_pro.One.ContactFragment;
import com.example.misaya.one_week_pro.Two.GetGPSFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tablayout_id;
    private ViewPager viewpager_id;
    private List<String>titles = new ArrayList<>();
    private List<Fragment>fragments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        titles.add("获取手机联系人");
        titles.add("获取当前GPS");



        fragments.add(new ContactFragment());
        fragments.add(new GetGPSFragment());


        MyViewAdapter adapter = new MyViewAdapter(getSupportFragmentManager());
        viewpager_id.setAdapter(adapter);
        tablayout_id.setupWithViewPager(viewpager_id);
    }

    private void initView() {
        tablayout_id = (TabLayout) findViewById(R.id.tablayout_id);
        viewpager_id = (ViewPager) findViewById(R.id.viewpager_id);
    }
    class MyViewAdapter extends FragmentPagerAdapter{

        public MyViewAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
