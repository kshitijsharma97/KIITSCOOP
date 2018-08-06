package in.kiitscoop.kiitscoop.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.kiitscoop.kiitscoop.R;
import in.kiitscoop.kiitscoop.TABS.DEPARTMENT;
import in.kiitscoop.kiitscoop.TABS.EVENTS;
import in.kiitscoop.kiitscoop.TABS.HOME;
import in.kiitscoop.kiitscoop.TABS.NOTICE;
import in.kiitscoop.kiitscoop.TABS.OTHER;
import in.kiitscoop.kiitscoop.TABS.PLACEMENT;
import in.kiitscoop.kiitscoop.TABS.SOCIETIES;

/**
 * {@link CategoryAdapter} is a {@link FragmentPagerAdapter} that can provide the layout for
 * each list item based on a data source which is a list of {@link Word} objects.
 */
public class CategoryAdapter extends FragmentPagerAdapter {

    /**
     * Context of the app
     */
    private Context mContext;

    /**
     * Create a new {@link CategoryAdapter} object.
     *
     * @param context is the context of the app
     * @param fm      is the fragment manager that will keep each fragment's state in the com.example.android.miwok.adapter
     *                across swipes.
     */
    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HOME();
        } else if (position == 1) {
            return new DEPARTMENT();
        } else if (position == 2) {
            return new SOCIETIES();
        } else if(position==3) {
            return new EVENTS();
        } else if(position==4){
            return  new PLACEMENT();
        }else if (position==5){
            return new NOTICE();
        }else{
            return  new OTHER();
        }
    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "HOME";
        } else if (position == 1) {
            return mContext.getString(R.string.category_department);
        } else if (position == 2) {
            return mContext.getString(R.string.category_soc);
        } else if (position == 3) {
            return mContext.getString(R.string.category_Events);
        }else if(position==4){
            return mContext.getString(R.string.category_place);
        } else if (position==5){
        return mContext.getString(R.string.category_notice);
    }else {
            return mContext.getString(R.string.category_other);
        }

 }
}