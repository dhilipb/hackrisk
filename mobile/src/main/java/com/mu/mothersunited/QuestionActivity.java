package com.mu.mothersunited;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.daimajia.swipe.SwipeLayout;
import com.mu.mothersunited.model.Comment;
import com.mu.mothersunited.model.Question;
import com.squareup.picasso.Picasso;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class QuestionActivity extends AppCompatActivity implements CommentView.Listener
{
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

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

    @InjectView(R.id.img_sender)
    ImageView imgSender;

    @InjectView(R.id.list_comments)
    RecyclerView listComments;

    @InjectView(R.id.txt_comment)
    EditText txtComment;

    @InjectView(R.id.btn_send_comment)
    ImageButton btnSendComment;

    private Question question;
    private CommentsAdapter commentsAdapter;
    private MothersUnitedApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        app = (MothersUnitedApplication) getApplication();

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listComments.setLayoutManager(layoutManager);
        commentsAdapter = new CommentsAdapter(this, this);
        listComments.setAdapter(commentsAdapter);
        listComments.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        txtComment.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) { }

            @Override
            public void afterTextChanged(Editable editable)
            {
                btnSendComment.setEnabled(txtComment.length() > 0);
            }
        });
        btnSendComment.setEnabled(false);

        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, btnYes);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, btnNo);
        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener()
        {
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
                if (swipeLayout.getOpenStatus() == SwipeLayout.Status.Open)
                {
                    boolean isLeft = xvel > 0;
                    int startColor = getResources().getColor(isLeft ? R.color.yes_light : R.color.no_light);
                    int endColor = getResources().getColor(R.color.white);
                    ValueAnimator colorAnim = ObjectAnimator.ofInt(swipeLayout, "backgroundColor", startColor, endColor);
                    colorAnim.setDuration(1000);
                    colorAnim.setEvaluator(new ArgbEvaluator());
                    colorAnim.start();
                }
                swipeLayout.close();
            }
        });

        question = getIntent().getExtras().getParcelable("question");
        this.txtTitle.setText(question.title);
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
            Picasso.with(imgSender.getContext()).load("http://graph.facebook.com/" + question.creatorId + "/picture?type=large&width=600&height=600​").into(imgSender);
        }
        txtSenderMonths.setText(question.creatorPregnancyMonth + "m pregnant");
        txtCommentCount.setText("0");
        txtVoteCount.setText(String.valueOf(question.upVotes.size() + question.downVotes.size()));
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        app.getApi().getComments(question.questionId, new Callback<List<Comment>>() {
            @Override
            public void success(List<Comment> comments, Response response)
            {
                commentsAdapter.setComments(comments);
            }

            @Override
            public void failure(RetrofitError error)
            {

            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_send_comment)
    void onSendCommentClicked()
    {
        Comment comment = new Comment(question.questionId, app.getUser().getId(), new Date().getTime(), txtComment.getText().toString());
        final ProgressDialog dialog = ProgressDialog.show(this, null, "Sending comment...");
        app.getApi().createComment(comment, new Callback<Comment>() {
            @Override
            public void success(Comment comment, Response response)
            {
                dialog.dismiss();
                txtComment.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txtComment.getWindowToken(), 0);
            }

            @Override
            public void failure(RetrofitError error)
            {
                dialog.dismiss();
            }
        });
        commentsAdapter.addComment(comment);
    }

    private class CommentsAdapter extends RecyclerView.Adapter<CommentView> {

        private List<Comment> comments;
        private Context context;
        private CommentView.Listener listener;

        private CommentsAdapter(Context context, CommentView.Listener listener)
        {
            this.context = context;
            this.listener = listener;
            this.comments = new ArrayList<>();
        }

        public void setComments(Collection<Comment> comments)
        {
            this.comments = new ArrayList<>(comments);
            notifyDataSetChanged();
        }

        public void addComment(Comment comment)
        {
            this.comments.add(comment);
            notifyDataSetChanged();
        }

        public Comment getItem(int position) {
            return comments.get(position);
        }

        @Override
        public CommentView onCreateViewHolder(ViewGroup parent, int viewType)
        {
            CommentView view = new CommentView(context, parent);
            view.setListener(listener);
            return view;
        }

        @Override
        public void onBindViewHolder(CommentView view, int position)
        {
            view.setComment(getItem(position));
        }

        @Override
        public int getItemCount()
        {
            return comments.size();
        }

    }

    public static class DividerItemDecoration extends RecyclerView.ItemDecoration {

        private static final int[] ATTRS = new int[]{
                android.R.attr.listDivider
        };

        public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

        public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

        private Drawable mDivider;

        private int mOrientation;

        public DividerItemDecoration(Context context, int orientation) {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
            setOrientation(orientation);
        }

        public void setOrientation(int orientation) {
            if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
                throw new IllegalArgumentException("invalid orientation");
            }
            mOrientation = orientation;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent) {
            if (mOrientation == VERTICAL_LIST) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }

        public void drawVertical(Canvas c, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        public void drawHorizontal(Canvas c, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getHeight() - parent.getPaddingBottom();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int left = child.getRight() + params.rightMargin;
                final int right = left + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            if (mOrientation == VERTICAL_LIST) {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            }
        }
    }
}
