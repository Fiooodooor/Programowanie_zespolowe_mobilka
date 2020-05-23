package com.votedesk.ui.login;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.votedesk.R;
import com.votedesk.data.model.SingleEnvironment;

import java.util.ArrayList;

public class MainCoordinatorActivity extends AppCompatActivity {
    static private ArrayList<SingleEnvironment> globalEnvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_coordinator);
        //Toolbar toolbar = findViewById(R.id.CoordToolbar);
        //setSupportActionBar(toolbar);

        globalEnvList = new ArrayList<>();
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void setGlobalEnvList(ArrayList<SingleEnvironment> theEnvList) {
        globalEnvList = theEnvList;
    }
    public ArrayList<SingleEnvironment> getGlobalEnvList() {
        return globalEnvList;
    }
}
