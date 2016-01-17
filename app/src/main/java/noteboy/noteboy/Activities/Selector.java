package noteboy.noteboy.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
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

import net.soulwolf.widget.materialradio.MaterialRadioGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import noteboy.noteboy.Adapters.RecyclerViewGridAdapter;
import noteboy.noteboy.R;

/**
 * Created by Chirag Shenoy on 30-Dec-15.
 */
public class Selector extends AppCompatActivity implements View.OnClickListener {

    private Bundle b;
    private TextView text;

    private BallView ballView;

    private GridLayoutManager lLayout;

    private LinearLayout select;
    private String superQueryInterface;

    private FloatingActionButton next;
    HashSet<String> branches;
    Toolbar toolbar;
    private Drawer result;
    private ArrayList<String> colleges;
    ArrayList<String> arrayListBranches;

    MaterialRadioGroup materialRadioGroup;

    RecyclerView rView;
    RecyclerViewGridAdapter rcAdapter;
    private int mShortAnimationDuration;
    Typeface branchFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selector);


        init();
        init_drawer();
        populateNavigationDrawer();
        parseQueryOfBranchNames();


    }

    private void init_drawer() {

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("My Notes");

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withSelectedItem(-1)
                .withRootView(R.id.drawer_layout)
                .withHeader(R.layout.nav_header)
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

                            startActivity(intent);
                            finish();
                        }

                        return true;
                    }
                })
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        next.getBackground().setAlpha((int) (255 - 255 * slideOffset));
                        Log.e("Aplha", "" + slideOffset);

                    }
                })
                .build();
    }

    private void openFolder() {
        Uri selectedUri = Uri.parse(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)));
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

    //INIT
    private void init() {

        b = getIntent().getExtras();

        colleges = getIntent().getStringArrayListExtra("all_colleges");
        select = (LinearLayout) findViewById(R.id.llSelector);
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        toolbar.setTitle("");
        branchFont = Typeface.createFromAsset(getAssets(), "fonts/candela.otf");

        materialRadioGroup = (MaterialRadioGroup) findViewById(R.id.materialRadioGroup);
        ballView = (BallView) findViewById(R.id.loaderSelect);
        text = (TextView) findViewById(R.id.frombundle);
        next = (FloatingActionButton) findViewById(R.id.fab);
        next.getBackground().setAlpha(255);

        setSupportActionBar(toolbar);

        arrayListBranches = new ArrayList<>();

        rView = (RecyclerView) findViewById(R.id.rView);
        lLayout = new GridLayoutManager(getApplicationContext(), 3);
        rcAdapter = new RecyclerViewGridAdapter(getApplicationContext(), arrayListBranches, branchFont);
        rView.setAdapter(rcAdapter);

        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);
        arrayListBranches = new ArrayList<>();


        superQueryInterface = (String) b.get("college_name");

        text.setText(superQueryInterface);
        select.setVisibility(View.INVISIBLE);

        branches = new HashSet<>();
        next.setOnClickListener(this);

        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_longAnimTime);
    }

    private void parseQueryOfBranchNames() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(superQueryInterface);
        query.whereNotEqualTo("branch", "bobo");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {

                    for (ParseObject value : scoreList) {
                        branches.add(value.getString("branch"));
                    }

                    arrayListBranches = new ArrayList<String>(branches);

                    rcAdapter = new RecyclerViewGridAdapter(getApplicationContext(), arrayListBranches, branchFont);

                    rView.setAdapter(rcAdapter);
                    crossfade();
//
//
//                    ballView.setVisibility(View.GONE);
//                    select.setVisibility(View.VISIBLE);

                    Log.d("score", "Retrieved " + branches.size() + " Unique branches");
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(Selector.this, SubjectClass.class);


        if (!getYear().equals("0") && rcAdapter.current >= 0) {

            rcAdapter.notifyDataSetChanged();

            Log.e("to intent", "year " + getYear() + " branch " + rcAdapter.itemList.get(rcAdapter.current) + " db " + superQueryInterface);

            i.putExtra("year", getYear());
            i.putExtra("branch", rcAdapter.itemList.get(rcAdapter.current));
            i.putExtra("db", superQueryInterface);
            i.putStringArrayListExtra("all_colleges", colleges);


            startActivity(i);
        } else {
            Snackbar.make(view, "Choose a year and a branch", Snackbar.LENGTH_LONG).show();
            Log.e("snackbar", "Snack");
        }
    }


    public String getYear() {

        View radioButton = materialRadioGroup.findViewById(materialRadioGroup.getCheckedRadioButtonId());
        int radioId = materialRadioGroup.indexOfChild(radioButton);

        switch (radioId) {
            case 0:
                return "1";
            case 1:
                return "2";
            case 2:
                return "3";
            case 3:
                return "4";

        }
        return "0";
    }

    private void crossfade() {

        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        rView.setAlpha(0f);
        rView.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        rView.animate()
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
                        select.setVisibility(View.VISIBLE);

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


