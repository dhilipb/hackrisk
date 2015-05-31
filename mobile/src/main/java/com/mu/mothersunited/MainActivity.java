package com.mu.mothersunited;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.mu.mothersunited.model.Question;
import com.mu.mothersunited.model.User;
import com.mu.mothersunited.model.Vote;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, QuestionCardView.Listener
{
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.tabs)
    TabLayout tabLayout;

    @InjectView(R.id.list_questions)
    RecyclerView listQuestions;

    @InjectView(R.id.v_loading)
    ViewGroup vLoading;

    private TabLayout.Tab privateTab;
    private TabLayout.Tab publicTab;
    private TabLayout.Tab currentTab;
    private QuestionsAdapter questionsAdapter;
    private MothersUnitedApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setTitle("");
        app = (MothersUnitedApplication) getApplication();

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listQuestions.setLayoutManager(layoutManager);
        questionsAdapter = new QuestionsAdapter(this, this);
        listQuestions.setAdapter(questionsAdapter);

        tabLayout.setOnTabSelectedListener(this);
        privateTab = tabLayout.newTab().setText("Private");
        tabLayout.addTab(privateTab);
        publicTab = tabLayout.newTab().setText("Public");
        tabLayout.addTab(publicTab);

        showLoading(true);
        loadQuestions();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        loadQuestions();
    }

    private void clearQuestions()
    {
        questionsAdapter.clear();
    }

    private void loadQuestions() {
        app.getApi().getQuestions(currentTab == privateTab ? app.getUser().getId() : null, new Callback<List<Question>>() {
            @Override
            public void success(List<Question> questions, Response response)
            {
                showLoading(false);
                questionsAdapter.setQuestions(questions);
            }

            @Override
            public void failure(RetrofitError error)
            {

            }
        });
    }

    private void showLoading(boolean show) {
        vLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab)
    {
        currentTab = tab;
        clearQuestions();
        loadQuestions();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab)
    {
        // Do nothing.
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab)
    {
        // Do nothing. FUTURE: reload
    }

    @OnClick(R.id.fab)
    void onFabClicked() {
        startActivity(new Intent(this, CreateQuestionActivity.class));
    }

    @Override
    public void onQuestionClicked(Question question)
    {
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("question", question);
        startActivity(intent);
    }

    @Override
    public void onVoted(Question question, boolean upVote)
    {
        User user = new User(app.getUser().getId(), "Expecting");
        boolean newVote = true;
        if (upVote && !question.upVotes.contains(user)) {
            question.upVotes.add(user);
            question.downVotes.remove(user);
        } else if (!upVote && !question.downVotes.contains(user)) {
            question.downVotes.add(user);
            question.upVotes.remove(user);
        } else {
            newVote = false;
        }

        if (newVote) {
            MothersUnitedApplication app = (MothersUnitedApplication) getApplication();
            app.getApi().vote(new Vote(question.questionId, app.getUser().getId(), upVote), new Callback<Question>() {
                @Override
                public void success(Question question, Response response)
                {

                }

                @Override
                public void failure(RetrofitError error)
                {

                }
            });
        }
        questionsAdapter.notifyDataSetChanged();
    }

    private class QuestionsAdapter extends RecyclerView.Adapter<QuestionCardView> {

        private List<Question> questions;
        private Context context;
        private QuestionCardView.Listener listener;

        private QuestionsAdapter(Context context, QuestionCardView.Listener listener)
        {
            this.context = context;
            this.listener = listener;
            this.questions = new ArrayList<>();
        }

        public void setQuestions(Collection<Question> questions)
        {
            this.questions = new ArrayList<>(questions);
            notifyDataSetChanged();
        }

        public Question getItem(int position) {
            return this.questions.get(position);
        }

        @Override
        public QuestionCardView onCreateViewHolder(ViewGroup parent, int viewType)
        {
            QuestionCardView view = new QuestionCardView(context, parent);
            view.setListener(listener);
            return view;
        }

        @Override
        public void onBindViewHolder(QuestionCardView view, int position)
        {
            view.setQuestion(getItem(position));
        }

        @Override
        public int getItemCount()
        {
            return questions.size();
        }

        public void clear() {
            this.questions.clear();
            this.notifyDataSetChanged();
        }

    }

}
