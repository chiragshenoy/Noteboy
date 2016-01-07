package noteboy.noteboy;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.glomadrian.loadingballs.BallView;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kai on 1/1/16.
 */
public class SubjectClass extends AppCompatActivity {
    Bundle b;
    String year, branch, superQUeryInterface;
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

        adapter = new CustomAdapter(subjects, getApplicationContext());
        mRecyclerView.setAdapter(adapter);
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {

            @Override
            public void onItemClicked(RecyclerView recyclerView, final int position, View v) {

                ParseQuery<ParseObject> query = ParseQuery.getQuery(superQUeryInterface);
                query.whereEqualTo("subject_name", subjects.get(position));
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(final List<ParseObject> scoreList, ParseException e) {
                        if (scoreList.size() > 0) {


                            final ParseFile fileObject = (ParseFile) scoreList.get(0)
                                    .get("notes_file");
                            fileObject
                                    .getDataInBackground(new GetDataCallback() {

                                        public void done(byte[] data,
                                                         ParseException e) {
                                            if (e == null) {
                                                Log.d("test",
                                                        "We've got data in data.");

                                                String folder_main = "Noteboy";

                                                File f = new File(Environment.getExternalStorageDirectory(), folder_main);
                                                if (!f.exists()) {
                                                    f.mkdirs();
                                                }
                                                try {
                                                    String path = f.getAbsolutePath() + File.separator + scoreList.get(0).get("subject_name");
                                                    FileOutputStream stream = new FileOutputStream(path);
                                                    stream.write(data);
                                                } catch (FileNotFoundException e1) {
                                                    Toast.makeText(getApplicationContext(), "Error saving file.Contact Noteboy", Toast.LENGTH_LONG).show();
                                                    e1.printStackTrace();
                                                } catch (IOException e1) {
                                                    e1.printStackTrace();
                                                }

                                            }
                                        }

                                    }, new ProgressCallback() {
                                        public void done(Integer percentDone) {
                                            // Update your progress spinner here. percentDone will be between 0 and 100.

                                        }
                                    });


                            Toast.makeText(getApplicationContext(), subjects.get(position), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), subjects.get(position), Toast.LENGTH_LONG).show();
                        }
                    }
                });


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
