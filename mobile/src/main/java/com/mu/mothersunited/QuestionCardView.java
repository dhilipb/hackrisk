package com.mu.mothersunited;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.daimajia.swipe.SwipeLayout;
import com.mu.mothersunited.model.Question;
import com.squareup.picasso.Picasso;

public class QuestionCardView extends RecyclerView.ViewHolder implements SwipeLayout.SwipeListener
{
    public interface Listener
    {
        public void onQuestionClicked(Question question);

        public void onVoted(Question question, boolean upVote);
    }

    @InjectView(R.id.txt_title)
    TextView txtTitle;

    @InjectView(R.id.txt_sender_name)
    TextView txtSenderName;

    @InjectView(R.id.txt_sender_months)
    TextView txtSenderMonths;

    @InjectView(R.id.txt_sender_age)
    TextView txtSenderAge;

    @InjectView(R.id.txt_comment_count)
    TextView txtCommentCount;

    @InjectView(R.id.txt_vote_count)
    TextView txtVoteCount;

    @InjectView(R.id.v_swipe)
    SwipeLayout swipeLayout;

    @InjectView(R.id.btn_yes)
    TextView btnYes;

    @InjectView(R.id.btn_no)
    TextView btnNo;

    @InjectView(R.id.v_content)
    CardView vContent;

    @InjectView(R.id.img_sender)
    ImageView imgSender;

    private Listener listener;
    private Question question;
    private boolean ignoreClick;

    protected QuestionCardView(final View view)
    {
        super(view);
        ButterKnife.inject(this, view);

        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, btnYes);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, btnNo);
        swipeLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!ignoreClick)
                {
                    onContentClicked();
                }
                ignoreClick = false;
            }
        });
        swipeLayout.addSwipeListener(this);
    }

    public QuestionCardView(Context context, ViewGroup parent)
    {
        this(LayoutInflater.from(context).inflate(R.layout.card_question, parent, false));
    }

    @Override
    public void onStartOpen(SwipeLayout swipeLayout)
    {

    }

    @Override
    public void onOpen(SwipeLayout swipeLayout)
    {

    }

    @Override
    public void onStartClose(SwipeLayout swipeLayout)
    {

    }

    @Override
    public void onClose(SwipeLayout swipeLayout)
    {

    }

    @Override
    public void onUpdate(SwipeLayout swipeLayout, int leftOffset, int rightOffset)
    {
    }

    @Override
    public void onHandRelease(SwipeLayout swipeLayout, float xvel, float yvel)
    {
        if (swipeLayout.getOpenStatus() == SwipeLayout.Status.Open && xvel != 0)
        {
            boolean isLeft = xvel > 0;

            int startColor = vContent.getContext().getResources().getColor(isLeft ? R.color.yes_light : R.color.no_light);
            int endColor = vContent.getContext().getResources().getColor(R.color.white);
            final ValueAnimator colorAnim = ObjectAnimator.ofInt(this.vContent, "cardBackgroundColor", startColor, endColor);
            colorAnim.setDuration(1000);
            colorAnim.setEvaluator(new ArgbEvaluator());
            colorAnim.start();

            if (listener != null)
                listener.onVoted(question, isLeft);
        }
        swipeLayout.close();
        ignoreClick = xvel == 0;
    }

    public void setListener(Listener listener)
    {
        this.listener = listener;
    }

    @OnClick({R.id.v_content, R.id.v_content_inner})
    void onContentClicked()
    {
        if (listener != null)
            listener.onQuestionClicked(this.question);
    }

    public void setQuestion(Question question)
    {
        this.question = question;
        this.txtTitle.setText(question.title);
        imgSender.setImageResource(R.drawable.default_profile_pic);
        if (question.visibleFacebookIds == null || question.visibleFacebookIds.size() == 0)
        {
            txtSenderName.setVisibility(View.GONE);
            txtSenderAge.setText(String.valueOf(question.creatorAge) + "y");
        }
        else
        {
            txtSenderName.setText(question.creatorName);
            txtSenderName.setVisibility(View.VISIBLE);
            txtSenderAge.setText("");
            Picasso.with(imgSender.getContext()).load("http://graph.facebook.com/" + question.creatorId + "/picture?type=large&width=600&height=600​").placeholder(R.drawable.default_profile_pic).into(imgSender);
        }
        txtSenderMonths.setText(question.creatorPregnancyMonth + "m pregnant");
        txtCommentCount.setText("0");
        txtVoteCount.setText(String.valueOf(question.upvotes.size() + question.downvotes.size()));
    }

}
