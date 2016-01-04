package noteboy.noteboy;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Chirag Shenoy on 30-Dec-15.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private ArrayList<String> mDataset;
    Context context;
    Typeface college_font;
    Typeface bold_font;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvCollegeName;
        public TextView tvBoldLetter;


        public ViewHolder(View v) {
            super(v);
            this.tvCollegeName = (TextView) v.findViewById(R.id.college_name);
            this.tvBoldLetter = (TextView) v.findViewById(R.id.bold_letter);

        }
    }

    public CustomAdapter(ArrayList<String> myDataset) {
        mDataset = myDataset;

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CustomAdapter(Context context, ArrayList<String> myDataset, Typeface college_font, Typeface bold_font) {
        mDataset = myDataset;
        this.context = context;
        this.college_font = college_font;
        this.bold_font = bold_font;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tvCollegeName.setText(mDataset.get(position));
        holder.tvCollegeName.setTypeface(college_font);

        char c = mDataset.get(position).charAt(1);
        holder.tvBoldLetter.setText(mDataset.get(position).substring(0, 1));
        holder.tvBoldLetter.setTypeface(bold_font);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}