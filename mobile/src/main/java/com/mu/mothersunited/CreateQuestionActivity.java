package com.mu.mothersunited;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.mu.mothersunited.model.Question;

import java.util.ArrayList;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CreateQuestionActivity extends AppCompatActivity
{
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.et_question)
    EditText etQuestion;

    @InjectView(R.id.chk_public)
    CheckBox chkPublic;

    private MenuItem sendMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etQuestion.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                updateSendItem();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // Do nothing.
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                // Do nothing.
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create_question, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        sendMenuItem = menu.findItem(R.id.menu_send);
        updateSendItem();
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_send:
                sendQuestion();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateSendItem() {
        if (sendMenuItem != null) {
            sendMenuItem.setEnabled(etQuestion.getText().toString().length() > 5);
            sendMenuItem.getIcon().setAlpha(etQuestion.getText().toString().length() > 5 ? 255 : 50);
        }
    }

    private void sendQuestion() {
        if (etQuestion.getText().toString().length() < 5) {
            Toast.makeText(this, "Please enter a question", Toast.LENGTH_SHORT).show();
            return;
        }

        MothersUnitedApplication app = (MothersUnitedApplication) getApplication();

        Question question = new Question();
        question.title = etQuestion.getText().toString();
        question.time = new Date().getTime();
        question.creatorId = app.getUser().getId();
        question.creatorName = app.getUser().getName();
        question.creatorAge = app.getUser().getAge();
        question.creatorPregnancyMonth = app.getUser().getPregnancyMonths();
        question.visibleFacebookIds = chkPublic.isChecked() ? null : new ArrayList<String>(app.getUser().getFriends());

        final Dialog dialog = ProgressDialog.show(this, null, "Asking question...");
        ((MothersUnitedApplication) getApplication()).getApi().createQuestion(question, new Callback<Question>() {
            @Override
            public void success(Question question, Response response)
            {
                dialog.dismiss();
                finish();
            }

            @Override
            public void failure(RetrofitError error)
            {
                dialog.dismiss();
                Toast.makeText(CreateQuestionActivity.this, "Oops, that's embarassing, something went wrong :\nPlease try again.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
