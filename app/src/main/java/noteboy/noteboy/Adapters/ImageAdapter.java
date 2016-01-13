package noteboy.noteboy.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import noteboy.noteboy.R;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> branches;
    Map<String, Integer> map = new HashMap<String, Integer>();
    Map<String, Integer> map2 = new HashMap<String, Integer>();

    static class ViewHolder {
        ImageView imageView;
        TextView textView;

    }

    public ImageAdapter(Context context, ArrayList<String> branches) {
        this.mContext = context;
        this.branches = branches;
        Log.e("arralistbranches", branches.toString());

        map = new HashMap<String, Integer>();
        map.put("cse", R.drawable.cse);
        map.put("ise", R.drawable.ise);
        map.put("ece", R.drawable.ece);
        map.put("mech", R.drawable.mech);
        map.put("it", R.drawable.it);
        map.put("ml", R.drawable.ml);
        map.put("bt", R.drawable.ece);
        map.put("mca", R.drawable.mca);
        map.put("mba", R.drawable.mba);
        map.put("eee", R.drawable.eee);
        map.put("civil", R.drawable.civil);
        map.put("arch", R.drawable.arch);
        map.put("chem", R.drawable.chem);
        map.put("tce", R.drawable.tce);
        map.put("iem", R.drawable.iem);

        map2.put("cse", R.drawable.cseselected);
        map2.put("ise", R.drawable.iseselected);
        map2.put("ece", R.drawable.eceselected);
        map2.put("mech", R.drawable.mechselected);
        map2.put("it", R.drawable.itselected);
        map2.put("ml", R.drawable.mlselected);
        map2.put("bt", R.drawable.eceselected);
        map2.put("mca", R.drawable.mcaselected);
        map2.put("mba", R.drawable.mbaselected);
        map2.put("eee", R.drawable.eeeselected);
        map2.put("civil", R.drawable.civilselected);
        map2.put("arch", R.drawable.archselected);
        map2.put("chem", R.drawable.chemselected);
        map2.put("tce", R.drawable.tceselected);
        map2.put("iem", R.drawable.iemselected);


    }

    @Override
    public int getCount() {
        return branches.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // Convert DP to PX
    // Source: http://stackoverflow.com/a/8490361
    public int dpToPx(int dps) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        int pixels = (int) (dps * scale + 0.5f);

        return pixels;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        final ViewHolder viewHolder;
        TextView textView;

        // Want the width/height of the items
        // to be 120dp
        int wPixel = dpToPx(64);
        int hPixel = dpToPx(64);


        if (convertView == null) {
            // If convertView is null then inflate the appropriate layout file
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.grid_image);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.grid_text);
            viewHolder.imageView.setSelected(false);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        // Set height and width constraints for the image view
//        imageView.setLayoutParams(new LinearLayout.LayoutParams(wPixel, hPixel));

        // Set the content of the image based on the provided URI

        Log.e("what", branches.get(position));

        if (!viewHolder.imageView.isSelected()) {
            viewHolder.imageView.setImageResource(map.get(branches.get(position)));
        }


        viewHolder.textView.setText(branches.get(position));

        viewHolder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.imageView.setPadding(8, 8, 8, 8);
        viewHolder.imageView.setCropToPadding(true);

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageView) v).setImageResource(map2.get(branches.get(position)));
                ((ImageView) v).setSelected(true);
            }
        });

        return convertView;
    }

}