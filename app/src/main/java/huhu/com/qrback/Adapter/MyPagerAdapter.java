package huhu.com.qrback.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Huhu on 5/5/16.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragmentArrayList;

    public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentArrayList) {
        super(fm);
        this.fragmentManager = fm;
        this.fragmentArrayList = fragmentArrayList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }
}
