package com.mu.mothersunited;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mu.mothersunited.facebook.FacebookUser;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by dhilipb on 31/05/2015.
 */
public class SelectFriendsCardView extends RecyclerView.ViewHolder {

    FacebookUser friend;
    Context context;

    @InjectView(R.id.txt_name)
    TextView txtName;

    @InjectView(R.id.img_friend)
    ImageView imgFriend;

    @InjectView(R.id.image_add)
    ImageView imgAdd;

    @OnClick(R.id.v_content)
    public void addFriend(View view) {
//        int color = view.getContext().getResources().getColor(R.color.yes);
//        view.setBackgroundColor(color);
    }

    public SelectFriendsCardView(View view) {
        super(view);
        ButterKnife.inject(this, view);
    }

    public SelectFriendsCardView(Context context, ViewGroup parent) {
        this(LayoutInflater.from(context).inflate(R.layout.card_selectfriend, parent, false));
        this.context = context;
    }

    public FacebookUser getFriend() {
        return friend;
    }

    public void setFriend(FacebookUser friend) {
        this.friend = friend;

        txtName.setText(friend.getName());
        Picasso.with(imgFriend.getContext()).load("http://graph.facebook.com/" + friend.getId() + "/picture?type=large&width=600&height=600​").placeholder(R.drawable.default_profile_pic).into(imgFriend);
    }

}
