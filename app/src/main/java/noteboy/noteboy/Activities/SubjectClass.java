package noteboy.noteboy.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.glomadrian.loadingballs.BallView;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ProgressCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import noteboy.noteboy.Adapters.SubjectCustomAdapter;
import noteboy.noteboy.Helpers.ItemClickSupport;
import noteboy.noteboy.R;

/**
 * Created by kai on 1/1/16.
 */
public class SubjectClass extends AppCompatActivity {
    Bundle b;
    String year, branch, superQUeryInterface;
    ArrayList<String> subjects_name;
    ArrayList<String> subjects_teacher;

    BallView ballView;
    RecyclerView.Adapter adapter;
    RecyclerView mRecyclerView;
    private Drawer result;
    Toolbar toolbar;
    private ArrayList<String> colleges;
    MaterialDialog downloadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_main);
        init();
        init_drawer();
        populateNavigationDrawer();
        querySubjects();

        Typeface subjectFont = Typeface.createFromAsset(getAssets(), "fonts/candela.otf");
        Typeface teacherFont = Typeface.createFromAsset(getAssets(), "fonts/robotocondensedregular.ttf");



        adapter = new SubjectCustomAdapter(subjects_name, subjects_teacher, getApplicationContext(),subjectFont,teacherFont);
        mRecyclerView.setAdapter(adapter);


        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {

            @Override
            public void onItemClicked(RecyclerView recyclerView, final int position, View v) {

                downloadingDialog = new MaterialDialog.Builder(SubjectClass.this)
                        .title(R.string.progress_dialog)
                        .content(subjects_name.get(position))
                        .progress(true, 0)
                        .progressIndeterminateStyle(true)
                        .cancelable(false)
                        .show();

                ParseQuery<ParseObject> query = ParseQuery.getQuery(superQUeryInterface);
                query.whereEqualTo("subject_name", subjects_name.get(position));
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(final List<ParseObject> scoreList, ParseException e) {
                        if (scoreList.size() > 0 && e == null) {


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

                                                downloadingDialog.dismiss();


                                                downloadingDialog = new MaterialDialog.Builder(SubjectClass.this)
                                                        .title("Done!")
                                                        .titleColorRes(R.color.done)
                                                        .positiveColorRes(R.color.primary_dark)
                                                        .negativeColorRes(R.color.primary_dark)
                                                        .content("")
                                                        .show();

                                                downloadingDialog.setTitle("Done!");
                                                downloadingDialog.setContent("Downloaded " + subjects_name.get(position));
                                                downloadingDialog.setCancelable(true);
                                                final View positive = downloadingDialog.getActionButton(DialogAction.POSITIVE);
                                                downloadingDialog.setActionButton(DialogAction.POSITIVE, "Alright");

                                                downloadingDialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                                                positive.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        downloadingDialog.dismiss();
                                                    }
                                                });


                                                final View negative = downloadingDialog.getActionButton(DialogAction.NEGATIVE);

                                                downloadingDialog.setActionButton(DialogAction.NEGATIVE, "View Notes");

                                                downloadingDialog.getActionButton(DialogAction.NEGATIVE).setEnabled(true);
                                                negative.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        downloadingDialog.dismiss();
                                                        Uri selectedUri = Uri.parse(Environment.getExternalStorageDirectory() + "/Noteboy/");
                                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                                        intent.setDataAndType(selectedUri, "resource/folder");

                                                        if (intent.resolveActivityInfo(getPackageManager(), 0) != null) {
                                                            startActivity(intent);
                                                        } else {
                                                            // if you reach this place, it means there is no any file
                                                            // explorer app installed on your device
                                                        }
                                                    }
                                                });

                                            }
                                        }

                                    }, new ProgressCallback() {
                                        public void done(Integer percentDone) {
                                            // Update your progress spinner here. percentDone will be between 0 and 100.
                                            downloadingDialog.setProgress(percentDone);
                                            Log.e("Downloaded", "" + percentDone);
                                        }
                                    });


                            Toast.makeText(getApplicationContext(), subjects_name.get(position), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), subjects_name.get(position), Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });

    }


    private void init_drawer() {

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("My Notes");

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHeader(R.layout.nav_header)
                .withSelectedItem(-1)
                .withRootView(R.id.drawer_layout)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem()
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D

                        if (position == 0) {
                            openFolder();
                        } else {

                            Intent intent = new Intent(getApplicationContext(), Selector.class);

                            // Pass data object in the bundle and populate details activity.
                            intent.putExtra("college_name", colleges.get(position - 3));
                            intent.putStringArrayListExtra("all_colleges", colleges);

                            startActivity(intent);
                            finish();
                        }

                        return true;
                    }
                })
                .build();
    }

    private void openFolder() {
        Uri selectedUri = Uri.parse(Environment.getExternalStorageDirectory() + "/Noteboy/");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(selectedUri, "resource/folder");

        if (intent.resolveActivityInfo(getPackageManager(), 0) != null) {
            startActivity(intent);
        } else {
            // if you reach this place, it means there is no any file
            // explorer app installed on your device
        }
    }
    //FUNCTIONS AND CLASSES IN ORDER

    private void populateNavigationDrawer() {

        for (String s : colleges) {
            result.addItem(new SecondaryDrawerItem().withName(s));
        }

    }

    private void querySubjects() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(superQUeryInterface);
        query.whereEqualTo("branch", branch);
        query.whereEqualTo("year", year);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {

                    for (ParseObject value : scoreList) {
                        subjects_name.add(value.getString("subject_name"));
                        subjects_teacher.add(value.getString("teachers_name"));
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
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        toolbar.setTitle("");
        colleges = getIntent().getStringArrayListExtra("all_colleges");

        subjects_name = new ArrayList<>();
        subjects_teacher = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_subject);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setVisibility(View.INVISIBLE);

        ballView = (BallView) findViewById(R.id.loaderSelect);
        b = getIntent().getExtras();
        year = (String) b.get("year");
        branch = (String) b.get("branch");
        superQUeryInterface = (String) b.get("db");
    }

}
