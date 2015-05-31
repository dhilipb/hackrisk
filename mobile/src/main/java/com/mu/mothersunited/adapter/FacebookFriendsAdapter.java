package com.mu.mothersunited.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.mu.mothersunited.SelectFriendsCardView;
import com.mu.mothersunited.facebook.FacebookUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhilipb on 31/05/2015.
 */
public class FacebookFriendsAdapter extends RecyclerView.Adapter<SelectFriendsCardView> {

    List<FacebookUser> friends;
    private Context context;

    public FacebookFriendsAdapter(Context context) {
        this.context = context;
        this.friends = new ArrayList<>();
    }

    public List<FacebookUser> getFriends() {
        return friends;
    }

    public void setFriends(List<FacebookUser> friends) {
        this.friends = friends;
        notifyDataSetChanged();
    }

    @Override
    public SelectFriendsCardView onCreateViewHolder(ViewGroup parent, int viewType) {
        SelectFriendsCardView view = new SelectFriendsCardView(context, parent);
        return view;
    }

    @Override
    public void onBindViewHolder(SelectFriendsCardView view, int i) {
        view.setFriend(this.friends.get(i));
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }


    public void clear() {
        this.friends.clear();
        this.notifyDataSetChanged();
    }
}
