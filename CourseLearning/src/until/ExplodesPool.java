package until;

import Game.Bullet;
import Game.Explode;

import java.util.ArrayList;
import java.util.List;

public class ExplodesPool {
    public static final int Default_Pool_MaxSize= 200;
    public static final int Default_Pool_Size = 200;
    //用于保存所有的爆炸对象
    private static List<Explode> pool = new ArrayList<>();
    //在类加载的时候创建对象池
    static {
        for (int i = 0; i < Default_Pool_Size; i++) {
            pool.add(new Explode());
        }
    }

    //从对象池中获得一个对象
    public static Explode get(){
        if(pool.size()==0){
            return new Explode();
        } else {
            return pool.remove(0);
        }
    }

    //返回对象到对象池中
    public static void theReturn(Explode explode){
        if(pool.size()==Default_Pool_MaxSize){
            return;
        }
        pool.add(explode);
    }
}
