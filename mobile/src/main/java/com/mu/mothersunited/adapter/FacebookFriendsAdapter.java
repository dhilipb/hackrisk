package com.mu.mothersunited.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.mu.mothersunited.facebook.FacebookUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhilipb on 31/05/2015.
 */
public class FacebookFriendsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<FacebookUser> friends;

    public FacebookFriendsAdapter() {
        friends = new ArrayList<FacebookUser>();
    }

    public List<FacebookUser> getFriends() {
        return friends;
    }

    public void setFriends(List<FacebookUser> friends) {
        this.friends = friends;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
