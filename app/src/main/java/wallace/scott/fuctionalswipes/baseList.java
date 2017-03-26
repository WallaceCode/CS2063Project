package wallace.scott.fuctionalswipes;

import java.util.ArrayList;

/**
 * Created by Scott on 2017-03-26.
 */

public class baseList {
    private static ArrayList<String[]> list;

    public baseList(){
        String[] temp = {"Rank","Score","Player Name"};
        list = new ArrayList<String[]>();
        list.add(0, temp);
    }

    public void addToList(int position, String[] newEntry){
        list.add(position, newEntry);
    }

    public String[] getItem(int position){
        return list.get(position);
    }

    public int getSize(){
        return list.size();
    }
}
