package noteboy.noteboy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.glomadrian.loadingballs.BallView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> colleges;
    private BallView ballView;
//    private TextView tvloading_colleges;

    private int mShortAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

//        tvloading_colleges = (TextView) findViewById(R.id.tvloading_colleges);

        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_longAnimTime);

        colleges = new ArrayList<>();

        setContentView(R.layout.activity_main);

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
//                    mRecyclerView.setVisibility(View.VISIBLE);
//                    ballView.setVisibility(View.GONE);
                    crossfade();
                    Log.d("score", "Retrieved " + collegeList.size() + " College Names");
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

        Typeface collegeFont = Typeface.createFromAsset(getAssets(), "fonts/candela.otf");
        Typeface boldFont = Typeface.createFromAsset(getAssets(), "fonts/alfaslabone.ttf");

        mAdapter = new CustomAdapter(this, colleges, collegeFont, boldFont);
        mRecyclerView.setAdapter(mAdapter);
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Intent intent = new Intent(getApplicationContext(), Selector.class);

                // Pass data object in the bundle and populate details activity.
                intent.putExtra("college_name", colleges.get(position));
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MainActivity.this, v, "transition");
                startActivity(intent, options.toBundle());

            }
        });
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
