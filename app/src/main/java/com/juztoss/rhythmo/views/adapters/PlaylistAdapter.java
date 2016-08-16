package com.juztoss.rhythmo.views.adapters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juztoss.rhythmo.R;
import com.juztoss.rhythmo.models.Composition;
import com.juztoss.rhythmo.models.Playlist;
import com.juztoss.rhythmo.models.songsources.SortType;
import com.juztoss.rhythmo.presenters.RhythmoApp;
import com.juztoss.rhythmo.services.PlaybackService;
import com.juztoss.rhythmo.views.activities.PlayerActivity;

/**
 * Created by JuzTosS on 4/20/2016.
 */
public class PlaylistAdapter extends RecyclerView.Adapter<SongElementHolder> implements Playlist.IUpdateListener, IOnItemClickListener
{
    private RhythmoApp mApp;
    private PlayerActivity mActivity;

    private Playlist mPlaylist;
    private Cursor mCurentCursor;

    BroadcastReceiver mUpdateUIReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            updateList();
        }
    };
    private IOnItemClickListener mOnItemClickListener;

    public PlaylistAdapter(PlayerActivity activity, Playlist playlist)
    {
        super();
        mCurentCursor = playlist.getList();
        mPlaylist = playlist;
        mActivity = activity;
        mApp = (RhythmoApp) activity.getApplicationContext();
    }


    @Override
    public void onPlaylistItemClick(int position, int action, Composition composition)
    {
        if (mOnItemClickListener != null)
        {
            mOnItemClickListener.onPlaylistItemClick(position, action, composition);
        }
    }

    @Override
    public SongElementHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = (LayoutInflater.from(mActivity));
        View row = inflater.inflate(R.layout.song_list_element, null);
        View header = inflater.inflate(R.layout.song_list_folder_header, null);
        return new SongElementHolder(row, header, this, mPlaylist.getSource().isModifyAvailable());
    }

    @Override
    public void onBindViewHolder(SongElementHolder holder, int position)
    {
        if (position == getItemCount() - 1)
        {
            holder.setVisible(false);
            return;
        }

        holder.setVisible(true);

        mCurentCursor.moveToPosition(position);
        final long songId = mCurentCursor.getLong(0);
        Composition composition = mApp.getComposition(songId);

        if (composition == null)
        {
            holder.setVisible(false);
            return;
        }

        boolean folderMode = false;

        if (mPlaylist.getSource().getSortType() == SortType.DIRECTORY)
        {
            int prevPosition = position - 1;
            if (prevPosition < 0)
            {
                folderMode = true;
            }
            else
            {
                mCurentCursor.moveToPosition(prevPosition);
                long prevSongId = mCurentCursor.getLong(0);
                Composition prevComposition = mApp.getComposition(prevSongId);

                if (prevComposition != null)
                    folderMode = !prevComposition.getFolderPath().equals(composition.getFolderPath());
            }
        }
        holder.update(composition, position, mActivity.playbackService(), folderMode);
    }

    @Override
    public int getItemCount()
    {
        if (mCurentCursor != null)
            return mCurentCursor.getCount() + 1;
        else
            return 0;
    }

    public void updateList()
    {
        mCurentCursor = mPlaylist.getList();
        notifyDataSetChanged();
    }

    public void bind()
    {
        mPlaylist.addUpdateListener(this);
        LocalBroadcastManager.getInstance(mApp).registerReceiver(mUpdateUIReceiver, new IntentFilter(PlaybackService.UPDATE_UI_ACTION));
    }

    public void unbind()
    {
        mPlaylist.removeUpdateListener(this);
        LocalBroadcastManager.getInstance(mApp).unregisterReceiver(mUpdateUIReceiver);
    }

    @Override
    public void onPlaylistUpdated()
    {
        updateList();
        mActivity.updateTabNames();
    }

    public void setOnItemClickListener(IOnItemClickListener onItemClickListener)
    {
        mOnItemClickListener = onItemClickListener;
    }
}