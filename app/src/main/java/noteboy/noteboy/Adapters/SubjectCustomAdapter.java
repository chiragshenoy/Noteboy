package noteboy.noteboy.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import noteboy.noteboy.R;

/**
 * Created by Chirag Shenoy on 30-Dec-15.
 */
public class SubjectCustomAdapter extends RecyclerView.Adapter<SubjectCustomAdapter.ViewHolder> {
    private ArrayList<String> subjects_name;
    private ArrayList<String> subjects_teachers;

    Context context;
    Typeface subjectFont;
    Typeface teacherFont;
    private int lastPosition = -1;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView cardView;
        public TextView tvsubjectname;
        public ImageView ivIcon;
        public TextView tvsubjectTeacher;

        public ViewHolder(View v) {
            super(v);
            this.cardView = (CardView) v.findViewById(R.id.card_view);
            this.tvsubjectname = (TextView) v.findViewById(R.id.subject_name);
            this.ivIcon = (ImageView) v.findViewById(R.id.subject_icon);
            this.tvsubjectTeacher = (TextView) v.findViewById(R.id.subject_teacher);
        }
    }

    public SubjectCustomAdapter(ArrayList<String> myDataset, ArrayList<String> subjects_teachers, Context context) {
        this.subjects_name = myDataset;
        this.subjects_teachers = subjects_teachers;
        this.context = context;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SubjectCustomAdapter(ArrayList<String> subjects_name, ArrayList<String> subjects_teachers, Context context, Typeface subjectFont, Typeface teacherFont) {
        this.subjects_name = subjects_name;
        this.context = context;
        this.subjectFont = subjectFont;
        this.teacherFont = teacherFont;
        this.subjects_teachers = subjects_teachers;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public SubjectCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_row, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tvsubjectname.setTypeface(subjectFont);
        if (subjects_name.get(position).length() > 28) {
            holder.tvsubjectname.setTextSize(12);
        }

        holder.tvsubjectname.setText(subjects_name.get(position));

        holder.tvsubjectTeacher.setTypeface(teacherFont);
        holder.tvsubjectTeacher.setText(subjects_teachers.get(position));

        holder.ivIcon.setImageResource(R.mipmap.ic_pdf);

        setAnimation(holder.cardView, position);
    }

//    private int getIcon(int position) {
//
//        if (subjects_name.get(position).contains("pdf")) {
//            Log.e("Yes", "pdf");
//            return R.mipmap.ic_pdf;
//        } else if (subjects_name.get(position).contains("doc") || subjects_name.get(position).contains("docx")) {
//            return R.mipmap.ic_doc;
//        } else if (subjects_name.get(position).contains("ppt") || subjects_name.get(position).contains("pptx")) {
//            return R.mipmap.ic_ppt;
//        } else if (subjects_name.get(position).contains("xls")) {
//            return R.mipmap.ic_xls;
//        }
//
//        return R.drawable.ic_action_assignment;
//    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            animation.setStartOffset(position * 120);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return subjects_name.size();
    }
}