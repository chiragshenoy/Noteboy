package noteboy.noteboy.Activities;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import noteboy.noteboy.Adapters.RecyclerViewAdapter;
import noteboy.noteboy.R;

import com.github.clans.fab.FloatingActionButton;

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

    private com.github.clans.fab.FloatingActionButton next;
    HashSet<String> branches;
    Toolbar toolbar;
    private Drawer result;
    private ArrayList<String> colleges;
    ArrayList<String> arrayListBranches;

    MaterialRadioGroup materialRadioGroup;

    RecyclerView rView;
    RecyclerViewAdapter rcAdapter;

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
                            intent.putExtra("college_name", colleges.get(position - 2));
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
        startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
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

        materialRadioGroup = (MaterialRadioGroup) findViewById(R.id.materialRadioGroup);
        ballView = (BallView) findViewById(R.id.loaderSelect);
        text = (TextView) findViewById(R.id.frombundle);
        next = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab);

        setSupportActionBar(toolbar);

        arrayListBranches = new ArrayList<>();

        rView = (RecyclerView) findViewById(R.id.rView);
        lLayout = new GridLayoutManager(getApplicationContext(), 3);
        rcAdapter = new RecyclerViewAdapter(getApplicationContext(), arrayListBranches);
        rView.setAdapter(rcAdapter);

        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);
        arrayListBranches = new ArrayList<>();


        superQueryInterface = (String) b.get("college_name");

        text.setText(superQueryInterface);
        select.setVisibility(View.INVISIBLE);

        branches = new HashSet<>();
        next.setOnClickListener(this);
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

                    rcAdapter = new RecyclerViewAdapter(getApplicationContext(), arrayListBranches);

                    rView.setAdapter(rcAdapter);

                    ballView.setVisibility(View.GONE);
                    select.setVisibility(View.VISIBLE);

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
        rcAdapter.notifyDataSetChanged();

        Log.e("to intent", "year " + getYear() + " branch " + rcAdapter.current + " db " + superQueryInterface);

        i.putExtra("year", getYear());
        i.putExtra("branch", rcAdapter.itemList.get(rcAdapter.current));
        i.putExtra("db", superQueryInterface);
        startActivity(i);
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
        return null;
    }

}


