package noteboy.noteboy;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.lukedeighton.wheelview.WheelView;

import com.lukedeighton.wheelview.WheelView;
import com.lukedeighton.wheelview.adapter.WheelAdapter;
import com.lukedeighton.wheelview.adapter.WheelArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.ganfra.materialspinner.MaterialSpinner;


/**
 * Created by Chirag Shenoy on 30-Dec-15.
 */
public class Selector extends AppCompatActivity {
    String[] ITEMS={"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"};
    Bundle b;
    WheelView wheelView;
    Drawable[] dArray;
    MaterialSpinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectorxml);
        init();
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
        b.get("college_name");

        //Wheel View
        wheelView = (WheelView) findViewById(R.id.wheelview);
    }

//Setting up spinner
    private void setUpSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEMS);
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
                        getResources().getDrawable(R.mipmap.i22),getResources().getDrawable(R.mipmap.three),
                        getResources().getDrawable(R.mipmap.four)};

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
                   String s = ITEMS[i];
                   Toast.makeText(getApplicationContext(), "Selected " +s, Toast.LENGTH_LONG).show();
               }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }









    }
