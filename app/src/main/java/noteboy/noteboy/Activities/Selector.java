package noteboy.noteboy.Activities;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.glomadrian.loadingballs.BallView;
import com.lukedeighton.wheelview.WheelView;

import com.lukedeighton.wheelview.adapter.WheelAdapter;
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

import fr.ganfra.materialspinner.MaterialSpinner;
import noteboy.noteboy.Adapters.ImageAdapter;
import noteboy.noteboy.R;


/**
 * Created by Chirag Shenoy on 30-Dec-15.
 */
public class Selector extends AppCompatActivity implements View.OnClickListener {

    private Bundle b;
    private TextView text;

    private BallView ballView;

    private LinearLayout select;
    private String superQUeryInterface;

    private String branch;
    private Button next;
    HashSet<String> branches;
    Toolbar toolbar;
    private Drawer result;
    private ArrayList<String> colleges;


    MaterialRadioGroup materialRadioGroup;

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

        ballView = (BallView) findViewById(R.id.loaderSelect);
        text = (TextView) findViewById(R.id.frombundle);
        next = (Button) findViewById(R.id.bIntent);

        setSupportActionBar(toolbar);


        superQUeryInterface = (String) b.get("college_name");

        text.setText(superQUeryInterface);
        select.setVisibility(View.INVISIBLE);

        branches = new HashSet<>();
        next.setOnClickListener(this);
    }

    private void parseQueryOfBranchNames() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(superQUeryInterface);
        query.whereNotEqualTo("branch", "bobo");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {

                    for (ParseObject value : scoreList) {
                        branches.add(value.getString("branch"));
                    }

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
        i.putExtra("year", getYear());
        i.putExtra("branch", branch);
        i.putExtra("db", superQUeryInterface);
        startActivity(i);
    }


    public String getYear() {

        View radioButton = materialRadioGroup.findViewById(materialRadioGroup.getCheckedRadioButtonId());
        int radioId = materialRadioGroup.indexOfChild(radioButton);

        switch (radioId) {
            case 0:
                return "one";
            case 1:
                return "two";
            case 2:
                return "three";
            case 3:
                return "four";

        }
        return null;
    }

}


