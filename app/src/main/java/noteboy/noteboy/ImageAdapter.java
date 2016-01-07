package noteboy.noteboy;

/**
 * Created by kai on 7/1/16.
 */

import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.ImageView;



/**
 * Created by Pavan on 06-01-2016.
 */
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private final ArrayList<String> web;

    // Constructor
    public ImageAdapter(Context c,ArrayList<String> web) {
        mContext = c;

        this.web = web;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    TextView textView;
        ImageView imageView;
        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_item, null);
             textView = (TextView) grid.findViewById(R.id.grid_text);
           imageView = (ImageView)grid.findViewById(R.id.grid_image);

            textView.setText(web.get(position));
            //textView.setText(branchId[position]);
            imageView.setImageResource(mThumbIds[position]);

        } else {
            grid = (View) convertView;
        }

        return grid;
    }



    /* public View getView(int position, View convertView, ViewGroup parent) {
         ImageView imageView;

         if (convertView == null) {
             imageView = new ImageView(mContext);
             imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
             imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
             imageView.setPadding(8, 8, 8, 8);
         }
         else
         {
             imageView = (ImageView) convertView;
         }
         imageView.setImageResource(mThumbIds[position]);
         imageView.setLayoutParams(new GridView.LayoutParams(
                 200, 200));
         return imageView;
     }*/
    public static String[] branchId = {
            "e", "is","me","i","etc"
    };
    // Keep all Images in array
    public static int[] mThumbIds = {
            R.drawable.noteboylogo, R.mipmap.i1,
            R.mipmap.i2, R.mipmap.i3,
            R.mipmap.i4

    };
}