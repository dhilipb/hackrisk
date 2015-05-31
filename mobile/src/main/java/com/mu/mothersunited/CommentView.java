package com.mu.mothersunited;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.mu.mothersunited.model.Comment;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentView extends RecyclerView.ViewHolder
{
    public interface Listener
    {
    }

    @InjectView(R.id.img_sender)
    ImageView imgSender;

    @InjectView(R.id.txt_comment)
    TextView txtComment;

    @InjectView(R.id.txt_time)
    TextView txtTime;

    private Listener listener;
    private Comment comment;

    protected CommentView(final View view)
    {
        super(view);
        ButterKnife.inject(this, view);
    }

    public CommentView(Context context, ViewGroup parent)
    {
        this(LayoutInflater.from(context).inflate(R.layout.card_comment, parent, false));
    }

    public void setListener(Listener listener)
    {
        this.listener = listener;
    }

    @OnClick({R.id.v_content})
    void onContentClicked()
    {

    }

    public void setComment(Comment comment)
    {
        this.comment = comment;
        Picasso.with(imgSender.getContext()).load("http://graph.facebook.com/" + comment.userId + "/picture?type=large&width=600&height=600​").into(imgSender);
        txtComment.setText(comment.text);
        txtTime.setText(SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.SHORT).format(new Date(comment.time)));
    }

}
