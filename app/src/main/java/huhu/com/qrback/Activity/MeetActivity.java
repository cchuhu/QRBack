package huhu.com.qrback.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import huhu.com.qrback.Adapter.MyPagerAdapter;
import huhu.com.qrback.Fragment.CurrentFragment;
import huhu.com.qrback.Fragment.HistoryFragment;
import huhu.com.qrback.R;

/**
 * 会议记录界面，分为当前和历史
 */
public class MeetActivity extends FragmentActivity {
    //添加会议按钮
    private ImageButton btn_addNew;
    private ViewPager mViewpager;
    //Fragment集合
    private ArrayList<Fragment> fragmentlist = new ArrayList<>();
    private CurrentFragment currentFragment;
    private HistoryFragment historyFragment;
    //适配器
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet);
        initViews();
    }

    /**
     * 初始化资源
     */
    private void initViews() {
        btn_addNew = (ImageButton) findViewById(R.id.btn_add);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        currentFragment = new CurrentFragment();
        historyFragment = new HistoryFragment();
        fragmentlist.add(currentFragment);
        fragmentlist.add(historyFragment);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentlist);
        mViewpager.setAdapter(myPagerAdapter);
        //为添加新会议按钮添加监听
        btn_addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MeetActivity.this, NewMeetActivity.class);
                startActivity(i);
            }
        });


    }
}
