package com.emcan.zella.adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.emcan.zella.R;
import com.emcan.zella.fragments.Current_orders;
import com.emcan.zella.fragments.Last_Orders;


public class last_order_tabs_adapter extends FragmentPagerAdapter {

    private Context mContext;


    public last_order_tabs_adapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;

    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new Current_orders();
        } else {
            return new Last_Orders();

        }
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 2;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getResources().getString(R.string.current);

            case 1:

            return mContext.getResources().getString(R.string.last);

            default:
                return null;
        }
    }

}
