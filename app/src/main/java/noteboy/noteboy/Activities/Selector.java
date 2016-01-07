package noteboy.noteboy.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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

    public static ArrayList<String> ItemCopy = new ArrayList();
    public static  ArrayList<String> Items = new ArrayList();
    private Bundle b;
    private TextView text;
    private WheelView wheelView;
    private Drawable[] dArray;
    private MaterialSpinner spinner;
    private BallView ballView;

    private ImageAdapter adapter;
    private LinearLayout select;
    private String superQUeryInterface;
    private String posiYear;
    private String branch;
    private Button next;
    GridView gridview;
    int count = 0;
    int current_selected;
    int previous_selected;
    View previous_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selector);
        init();
        parseQueryOfBranchNames();
        setUpGridView();
        populateAdapter();
        wheelViewListeners();

        // Toast.makeText(getApplicationContext(), "in selector " + b.get("college_name").toString(), Toast.LENGTH_LONG).show();
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int current_selected;
                if(count ==0 ){
                    count++;
                    current_selected = position;
                    previous_selected = position;
                    previous_view = v;

                }
                current_selected = position;

                ImageView imageView = (ImageView) v.findViewById(R.id.grid_image);
                imageView.setImageResource(ImageAdapter.mThumbIds[4]);

                if(previous_selected != current_selected){
                    View pv = previous_view;
                    ImageView rekt = (ImageView) pv.findViewById(R.id.grid_image);
                    rekt.setImageResource(ImageAdapter.mThumbIds[previous_selected]);
                }
                previous_selected = current_selected;
                previous_view = v;
                branch = Items.get(position);
            }
        });
    }





    //FUNCTIONS AND CLASSES IN ORDER


    //INIT
    private void init() {
        //get bundle
        b = getIntent().getExtras();
        superQUeryInterface = (String) b.get("college_name");
        wheelView = (WheelView) findViewById(R.id.wheelview);
        ballView = (BallView) findViewById(R.id.loaderSelect);
        text = (TextView) findViewById(R.id.frombundle);
        text.setText(superQUeryInterface);
        select = (LinearLayout) findViewById(R.id.llSelector);
        select.setVisibility(View.INVISIBLE);
        posiYear = String.valueOf(1);

        next = (Button) findViewById(R.id.bIntent);
        next.setOnClickListener(this);
    }

    private void parseQueryOfBranchNames() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(superQUeryInterface);
        query.whereNotEqualTo("branch", "bobo");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    int x =0;
                    for (ParseObject value : scoreList) {
                        Items.add(value.getString("branch"));
                        ItemCopy.add(value.getString("branch"));
                        x++;
                    }
                    HashSet hs = new HashSet(Items);
                    Items.clear();
                    Items.addAll(hs);
                    gridview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    ballView.setVisibility(View.GONE);
                    select.setVisibility(View.VISIBLE);


                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    private void setUpGridView() {

        adapter = new ImageAdapter(Selector.this,Items);
        gridview = (GridView) findViewById(R.id.gridview);



    }

//Populating adapter of the wheel

    private void populateAdapter() {
        //populate the adapter, that knows how to draw each item (as you would do with a ListAdapter)
        wheelView.setAdapter(new WheelAdapter() {
            @Override
            public Drawable getDrawable(int position) {
                dArray = new Drawable[]{getResources().getDrawable(R.mipmap.i11),
                        getResources().getDrawable(R.mipmap.i22), getResources().getDrawable(R.mipmap.i33),
                        getResources().getDrawable(R.mipmap.i44)};

                //return drawable here man
                return dArray[position];
            }

            @Override
            public int getCount() {
                return 4;
                //return the count man
            }

            @Override
            public Object getItem(int position) {
                return dArray[position];
            }
        });

    }


    //Event listeners for wheel.. Wheel transition and wheel item selection listenres exist
    private void wheelViewListeners() {
        //a listener for receiving a callback for when the item closest to the selection angle changes
        wheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectListener() {
            @Override
            public void onWheelItemSelected(WheelView parent, Drawable itemDrawable, int position) {
                //get the item at this position
                posiYear = String.valueOf(position + 1);

            }
        });

        wheelView.setOnWheelItemClickListener(new WheelView.OnWheelItemClickListener() {
            @Override
            public void onWheelItemClick(WheelView parent, int position, boolean isSelected) {
                Toast.makeText(getApplicationContext(), "Selected " + position, Toast.LENGTH_LONG).show();

            }
        });


    }


    @Override
    public void onClick(View view) {
        Intent i = new Intent(Selector.this, SubjectClass.class);
        i.putExtra("year", posiYear);
        i.putExtra("branch", branch);
        i.putExtra("db", superQUeryInterface);
        startActivity(i);
    }
}


