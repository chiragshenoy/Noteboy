package noteboy.noteboy.Adapters;

/**
 * Created by Chirag Shenoy on 13-Jan-16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import noteboy.noteboy.R;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView countryName;
    public ImageView countryPhoto;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        countryName = (TextView) itemView.findViewById(R.id.grid_text);
        countryPhoto = (ImageView) itemView.findViewById(R.id.grid_image);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}