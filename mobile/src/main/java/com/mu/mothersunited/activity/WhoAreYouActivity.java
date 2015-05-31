package com.mu.mothersunited.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.mu.mothersunited.MothersUnitedApplication;
import com.mu.mothersunited.R;
import com.mu.mothersunited.facebook.FacebookUser;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class WhoAreYouActivity extends AppCompatActivity {

    private MothersUnitedApplication app;

    @InjectView(R.id.btnMumFirstTime)
    Button mButtonFirstTime;

    @InjectView(R.id.btnMumAlready)
    Button mButtonMumAlready;

    @InjectView(R.id.btnDoctor)
    Button mButtonDoctor;


    @OnClick({R.id.btnMumFirstTime, R.id.btnMumAlready, R.id.btnDoctor})
    public void chooseMe(Button view) {

        CharSequence text = view.getText();
        FacebookUser user = app.getFacebookUser();

        if (text.equals(mButtonFirstTime.getText())) {

        } else if (text.equals(mButtonMumAlready.getText())) {

        } else if (text.equals(mButtonDoctor.getText())) {

        }

        startActivity(new Intent(this, SelectFriendsActivity.class));
        finish();
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_are_you);
        ButterKnife.inject(this);

        app = (MothersUnitedApplication) getApplication();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_who_are_you, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
