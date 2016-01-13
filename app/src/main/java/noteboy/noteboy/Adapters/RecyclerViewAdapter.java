package noteboy.noteboy.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import noteboy.noteboy.R;

/**
 * Created by Chirag Shenoy on 13-Jan-16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private ArrayList<String> itemList;
    private Context context;
    Map<String, Integer> map = new HashMap<String, Integer>();
    Map<String, Integer> map2 = new HashMap<String, Integer>();


    public RecyclerViewAdapter(Context context, ArrayList<String> itemList) {
        this.itemList = itemList;
        this.context = context;
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
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.countryName.setText(itemList.get(position));
        holder.countryPhoto.setImageResource(map.get(itemList.get(position)));
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}