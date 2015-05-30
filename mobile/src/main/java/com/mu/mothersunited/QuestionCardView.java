package com.mu.mothersunited;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.mu.mothersunited.model.Question;

public class QuestionCardView extends RecyclerView.ViewHolder
{
    public interface Listener {

    }

    @InjectView(R.id.txt_title)
    TextView txtTitle;

    private Listener listener;
    private Question question;

    protected QuestionCardView(View view)
    {
        super(view);
        ButterKnife.inject(this, view);
    }

    public QuestionCardView(Context context, ViewGroup parent)
    {
        this(LayoutInflater.from(context).inflate(R.layout.card_question, parent, false));
    }

    public void setListener(Listener listener)
    {
        this.listener = listener;
    }

    @OnClick(R.id.v_content)
    void onContentClicked()
    {
        if (listener != null) {

        }
    }

    public void setQuestion(Question question) {
        this.question = question;
        this.txtTitle.setText(question.title);
    }

}
