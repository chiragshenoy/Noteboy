package noteboy.noteboy.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.github.glomadrian.loadingballs.BallView;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import noteboy.noteboy.Adapters.MainPageCustomAdapter;
import noteboy.noteboy.Helpers.ItemClickSupport;
import noteboy.noteboy.R;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> colleges;
    private BallView ballView;
//    private TextView tvloading_colleges;

    private int mShortAnimationDuration;
    private Drawer result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("My Notes");

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withSelectedItem(-1)
                .withHeader(R.layout.nav_header)
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

                        if (position == 1) {
                            openFolder();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), Selector.class);

                            // Pass data object in the bundle and populate details activity.
                            intent.putExtra("college_name", colleges.get(position - 3));
                            intent.putStringArrayListExtra("all_colleges", colleges);
                            result.closeDrawer();

                            startActivity(intent);
                        }

                        return true;
                    }
                })
                .build();

//        tvloading_colleges = (TextView) findViewById(R.id.tvloading_colleges);

        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_longAnimTime);

        colleges = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        ballView = (BallView) findViewById(R.id.loader);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setVisibility(View.INVISIBLE);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Master");
        query.whereNotEqualTo("college_name", "bobo");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> collegeList, ParseException e) {
                if (e == null) {

                    for (ParseObject value : collegeList) {
                        colleges.add(value.getString("college_name"));
                    }
                    mAdapter.notifyDataSetChanged();
                    crossfade();
                    populateNavigationDrawer();
                    Log.d("score", "Retrieved " + collegeList.size() + " College Names");
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

        Typeface collegeFont = Typeface.createFromAsset(getAssets(), "fonts/candela.otf");
        Typeface boldFont = Typeface.createFromAsset(getAssets(), "fonts/alfaslabone.ttf");

        mAdapter = new MainPageCustomAdapter(this, colleges, collegeFont, boldFont);
        mRecyclerView.setAdapter(mAdapter);
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Intent intent = new Intent(getApplicationContext(), Selector.class);

                // Pass data object in the bundle and populate details activity.
                intent.putExtra("college_name", colleges.get(position));
                intent.putStringArrayListExtra("all_colleges", colleges);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MainActivity.this, v, "transition");
                startActivity(intent, options.toBundle());

            }
        });
    }

    private void openFolder() {
        Uri selectedUri = Uri.parse(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(selectedUri, "resource/folder");

        if (intent.resolveActivityInfo(getPackageManager(), 0) != null)
        {
            startActivity(intent);
        }
        else
        {
            // if you reach this place, it means there is no any file
            // explorer app installed on your device
        }
    }

    private void populateNavigationDrawer() {
//        ArrayList<SecondaryDrawerItem> navItems = new ArrayList<>();
        for (String s : colleges) {
            result.addItem(new SecondaryDrawerItem().withName(s));
        }

    }


    private void crossfade() {

        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        mRecyclerView.setAlpha(0f);
        mRecyclerView.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        mRecyclerView.animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration)
                .setListener(null);

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        ballView.animate()
                .alpha(0f)
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ballView.setVisibility(View.GONE);
                    }
                });

//        tvloading_colleges.clearAnimation();
//        tvloading_colleges.animate()
//                .alpha(0f)
//                .setDuration(mShortAnimationDuration)
//                .setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        tvloading_colleges.setVisibility(View.GONE);
//                    }
//                });

    }


}
