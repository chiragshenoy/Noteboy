package noteboy.noteboy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Chirag Shenoy on 30-Dec-15.
 */
public class Selector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        b.get("college_name");

        Toast.makeText(getApplicationContext(), "in selector " + b.get("college_name").toString(), Toast.LENGTH_LONG).show();

    }
}
