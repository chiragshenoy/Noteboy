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

    public static ArrayList<String> ItemCopy = new ArrayList();
    public static ArrayList<String> Items = new ArrayList();
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

    MaterialRadioGroup materialRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selector);
        init();
        parseQueryOfBranchNames();
//        setUpGridView();
//        populateAdapter();

        // Toast.makeText(getApplicationContext(), "in selector " + b.get("college_name").toString(), Toast.LENGTH_LONG).show();

    }


    //FUNCTIONS AND CLASSES IN ORDER


    //INIT
    private void init() {
        //get bundle
        b = getIntent().getExtras();
        select = (LinearLayout) findViewById(R.id.llSelector);

        superQUeryInterface = (String) b.get("college_name");
        ballView = (BallView) findViewById(R.id.loaderSelect);
        text = (TextView) findViewById(R.id.frombundle);
        text.setText(superQUeryInterface);
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
                    ballView.setVisibility(View.GONE);
                    select.setVisibility(View.VISIBLE);


                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
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


    public String getTransport() {

        View radioButton = materialRadioGroup.findViewById(materialRadioGroup.getCheckedRadioButtonId());
        int radioId = materialRadioGroup.indexOfChild(radioButton);

        switch (radioId) {
            case 0:
                return "first";
            case 1:
                return "second";
            case 2:
                return "third";
            case 3:
                return "fourth";

        }
        return null;
    }

}


