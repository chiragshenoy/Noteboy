package noteboy.noteboy.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import noteboy.noteboy.Helpers.Mapper;
import noteboy.noteboy.R;

/**
 * Created by Chirag Shenoy on 13-Jan-16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolders> {

    public ArrayList<String> itemList;
    private Context context;
    Map<String, Integer> map = new HashMap<String, Integer>();
    Map<String, Integer> map2 = new HashMap<String, Integer>();

    private final ArrayList<Boolean> selected = new ArrayList<>();
    public int current = -1;
    public int previous;
    int counter = 0;


    public RecyclerViewAdapter(Context context, ArrayList<String> itemList) {
        this.itemList = itemList;
        this.context = context;

        for (int i = 0; i < itemList.size(); i++) {
            selected.add(false);
        }

        Mapper m = new Mapper();
        map = m.getMap();
        map2 = m.getMap2();
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.countryName.setText(itemList.get(position));

        if (!selected.get(position))
            holder.countryPhoto.setImageResource(map.get(itemList.get(position)));
        else
            holder.countryPhoto.setImageResource(map2.get(itemList.get(position)));


    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }


    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        public String getSelected() {
            return "" + (current);
        }


        public TextView countryName;
        public ImageView countryPhoto;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            countryName = (TextView) itemView.findViewById(R.id.grid_text);
            countryPhoto = (ImageView) itemView.findViewById(R.id.grid_image);

        }

        @Override
        public void onClick(View view) {


            if (counter == 0) {
                countryPhoto.setImageResource(map2.get(itemList.get(getPosition())));
                current = getPosition();
                selected.set(getPosition(), true);
                notifyDataSetChanged();
                notifyItemChanged(current);

            } else {
                if (!selected.get(getPosition())) {
                    countryPhoto.setImageResource(map2.get(itemList.get(getPosition())));
                    selected.set(getPosition(), true);
                    previous = current;
                    current = getPosition();
                    selected.set(previous, false);
                    notifyDataSetChanged();
                    notifyItemChanged(current);

                }


            }

            counter++;


            Log.e("Current", "" + current);


        }
    }

}