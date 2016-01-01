package noteboy.noteboy;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.github.glomadrian.loadingballs.BallView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kai on 1/1/16.
 */
public class SubjectClass extends AppCompatActivity {
    Bundle b;
    String year,branch,superQUeryInterface;
    ArrayList<String> subjects;
    BallView ballView;
    RecyclerView.Adapter adapter;
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_layout);
        init();
        querySubjects();

        adapter = new CustomAdapter(subjects);
        mRecyclerView.setAdapter(adapter);
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {

            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//
//
            }
        });

    }

    private void querySubjects() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(superQUeryInterface);
        query.whereEqualTo("branch", branch);
        query.whereEqualTo("year", year);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {

                    for (ParseObject value : scoreList) {
                        subjects.add(value.getString("subject_name"));
                    }
                    adapter.notifyDataSetChanged();
                    ballView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);


                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    private void init() {
        subjects = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_subject);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setVisibility(View.INVISIBLE);
        ballView = (BallView) findViewById(R.id.loaderSubject);
        b = getIntent().getExtras();
        year = (String) b.get("year");
        branch = (String) b.get("branch");
        superQUeryInterface = (String) b.get("db");
    }

}
