package noteboy.noteboy;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.antonionicolaspina.revealtextview.RevealTextView;
import com.github.glomadrian.loadingballs.BallView;
import com.lukedeighton.wheelview.WheelView;

import com.lukedeighton.wheelview.WheelView;
import com.lukedeighton.wheelview.adapter.WheelAdapter;
import com.lukedeighton.wheelview.adapter.WheelArrayAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import fr.ganfra.materialspinner.MaterialSpinner;


/**
 * Created by Chirag Shenoy on 30-Dec-15.
 */
public class Selector extends AppCompatActivity implements View.OnClickListener {


    String[] ITEMS={"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"};
    ArrayList<String> Items = new ArrayList();
    Bundle b;
    TextView text;
    WheelView wheelView;
    Drawable[] dArray;
    MaterialSpinner spinner;
    BallView ballView;

    ArrayAdapter<String> adapter;
    LinearLayout select;
    String superQUeryInterface;
    String posiYear;String branch;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectorxml);
        init();
        parseQueryOfBranchNames();
        setUpSpinner();
        populateAdapter();
        wheelViewListeners();

        // Toast.makeText(getApplicationContext(), "in selector " + b.get("college_name").toString(), Toast.LENGTH_LONG).show();

    }



    //FUNCTIONS AND CLASSES IN ORDER


    //INIT
    private void init() {
        //get bundle
        b = getIntent().getExtras();
       superQUeryInterface = (String) b.get("college_name");
        wheelView = (WheelView) findViewById(R.id.wheelview);
        ballView = (BallView) findViewById(R.id.loaderSelect);
        text = (TextView)findViewById(R.id.frombundle);
        text.setText(superQUeryInterface);
        select = (LinearLayout)findViewById(R.id.llSelector);
        select.setVisibility(View.INVISIBLE);
        posiYear = String.valueOf(1);

        next = (Button)findViewById(R.id.bIntent);
        next.setOnClickListener(this);
    }

    private void parseQueryOfBranchNames() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(superQUeryInterface);
        query.whereNotEqualTo("branch", "bobo");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {

                    for (ParseObject value : scoreList) {
                        Items.add(value.getString("branch"));
                        ;
                    }
                    HashSet hs = new HashSet(Items);
                    Items.clear();
                    Items.addAll(hs);
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

//Setting up spinner
    private void setUpSpinner() {
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (MaterialSpinner) findViewById(R.id.spinner1);
        spinner.setAdapter(adapter);
    }



//Populating adapter of the wheel

    private void populateAdapter() {
        //populate the adapter, that knows how to draw each item (as you would do with a ListAdapter)
        wheelView.setAdapter(new WheelAdapter() {
            @Override
            public Drawable getDrawable(int position) {
                dArray = new Drawable[]{getResources().getDrawable(R.mipmap.i11),
                        getResources().getDrawable(R.mipmap.i22),getResources().getDrawable(R.mipmap.i33),
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
                posiYear = String.valueOf(position+1);

            }
        });

        wheelView.setOnWheelItemClickListener(new WheelView.OnWheelItemClickListener() {
            @Override
            public void onWheelItemClick(WheelView parent, int position, boolean isSelected) {
                Toast.makeText(getApplicationContext(), "Selected " + position, Toast.LENGTH_LONG).show();

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               if(i>-1) {
                   String s = Items.get(i);
                   Toast.makeText(getApplicationContext(), "Selected " +s, Toast.LENGTH_LONG).show();
                   branch = s;
               }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        Intent i = new Intent(Selector.this , SubjectClass.class);
        i.putExtra("year", posiYear);
        i.putExtra("branch",branch);
        i.putExtra("db",superQUeryInterface);
        startActivity(i);
    }
}
