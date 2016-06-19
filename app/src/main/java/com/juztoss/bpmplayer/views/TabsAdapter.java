package com.juztoss.bpmplayer.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

/**
 * Created by JuzTosS on 6/4/2016.
 */
public class TabsAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener
{
    private int mNumOfLists;
    private int mCurrentPosition = 0;
    private ViewGroup mContainer;
    private Object mCurrentFragment;

    @Override
    public void startUpdate(ViewGroup container)
    {
        super.startUpdate(container);
        mContainer = container;
    }

    public TabsAdapter(FragmentManager supportFragmentManager, int numOfLists)
    {
        super(supportFragmentManager);
        setNumOfLists(numOfLists);
    }

    public void setNumOfLists(int numOfLists)
    {
        mNumOfLists = numOfLists;
        notifyDataSetChanged();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

    }

    @Override
    public void onPageSelected(int newPosition)
    {

        PlaylistFragment fragmentToShow = (PlaylistFragment) instantiateItem(mContainer, newPosition);
        fragmentToShow.onResumeFragment();

        PlaylistFragment fragmentToHide = (PlaylistFragment) instantiateItem(mContainer, mCurrentPosition);
        fragmentToHide.onPauseFragment();

        mCurrentPosition = newPosition;
    }

    @Override
    public Fragment getItem(int position)
    {
        return PlaylistFragment.newInstance(position);
    }

    @Override
    public int getCount()
    {
        return mNumOfLists;
    }

    public PlaylistFragment getCurrentFragment()
    {
        return (PlaylistFragment) instantiateItem(mContainer, mCurrentPosition);
    }
}