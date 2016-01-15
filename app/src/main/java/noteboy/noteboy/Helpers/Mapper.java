package noteboy.noteboy.Helpers;

import java.util.HashMap;
import java.util.Map;

import noteboy.noteboy.R;

/**
 * Created by Chirag Shenoy on 13-Jan-16.
 */
public class Mapper {

    static Map<String, Integer> map = new HashMap<String, Integer>();
    static Map<String, Integer> map2 = new HashMap<String, Integer>();

    public Mapper() {
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

    public Map<String,Integer> getMap2() {
        return map2;
    }

    public Map<String,Integer> getMap() {
        return map;
    }
}
