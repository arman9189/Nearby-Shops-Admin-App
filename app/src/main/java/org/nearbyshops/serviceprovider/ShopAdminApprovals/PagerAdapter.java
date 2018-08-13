package org.nearbyshops.serviceprovider.ShopAdminApprovals;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.nearbyshops.serviceprovider.ShopAdminApprovals.Fragment.FragmentShopAdmins;

/**
 * Created by sumeet on 22/11/16.
 */


public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        // getItem is called to instantiate the fragment for the given page.
        // Return a FragmentShopAdmins (defined as a static inner class below).

        if(position==0)
        {
            return FragmentShopAdmins.newInstance(FragmentShopAdmins.MODE_ACCOUNTS_DISABLED);
        }
        else if(position==1)
        {
            return FragmentShopAdmins.newInstance(FragmentShopAdmins.MODE_ACCOUNTS_WAITLISTED);

        }
        else if(position==2)
        {
            return FragmentShopAdmins.newInstance(FragmentShopAdmins.MODE_ACCOUNTS_ENABLED);
        }

        return FragmentShopAdmins.newInstance(FragmentShopAdmins.MODE_ACCOUNTS_ENABLED);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return titleDisabled;
            case 1:
                return titleWaitlisted;
            case 2:
                return titleEnabled;
        }
        return null;
    }





    private String titleDisabled = "Disabled ( 0 / 0 )";
    private String titleWaitlisted = "Waitlisted ( 0 / 0 )";
    private String titleEnabled = "Enabled (0/0)";
//    private String titleDetachedItems = "Detached Items (0/0)";


    public void setTitle(String title, int tabPosition)
    {
        if(tabPosition == 0){

            titleDisabled = title;
        }
        else if (tabPosition == 1)
        {

            titleWaitlisted = title;
        }else if(tabPosition == 2)
        {
            titleEnabled = title;

        }else if(tabPosition == 3)
        {
//            titleDetachedItems = title;
        }


        notifyDataSetChanged();
    }




}
