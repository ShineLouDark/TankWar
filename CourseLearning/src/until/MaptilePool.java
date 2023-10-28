package until;

import map.MapTile;

import java.util.ArrayList;
import java.util.List;

public class MaptilePool {
    private static int Pool_Max_Size = 70;
    private static int Pool_Size = 50;
    private static List<MapTile> pool = new ArrayList<>();
    static {
        for (int i = 0; i < pool.size(); i++) {
            pool.add(new MapTile());
        }
    }

    public static MapTile get() {
        MapTile mapTile = null;
        if(pool.size()==0){
            mapTile = new MapTile();
        }else {
            pool.remove(0);
        }
        return mapTile;
    }

    public static void Return(MapTile mapTile){
        if(pool.size() == Pool_Max_Size){
            return;
        }
        pool.add(mapTile);
    }
}
