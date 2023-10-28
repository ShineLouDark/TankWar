package until;

import Game.Bullet;

import java.util.ArrayList;
import java.util.List;

/*
*                       子弹对象池
* */
public class BulletsPool {
    public static final int Default_Pool_MaxSize= 200;
    public static final int Default_Pool_Size = 200;
    //用于保存所有的子弹容器
    private static List<Bullet> pool = new ArrayList<>();
    //在类加载的时候创建200个子弹对象添加到容器中
    static {
        for (int i = 0; i < Default_Pool_Size; i++) {
            pool.add(new Bullet());
        }
    }

    /*
    *   从对象池中获取一个子弹对象
    * */
    public static Bullet get(){
        Bullet bullet = null;
        //对象池被掏空了
        if(pool.size()==0){
            bullet = new Bullet();
        }else {
            bullet = pool.remove(0);
        }
//        System.out.println("甚于："+pool.size());
        return bullet;
    }

    public static void theReturn(Bullet bullet){
        //子弹达到最大数量，便不再归还
        if(pool.size() == Default_Pool_MaxSize){
            return;
        }
        pool.add(bullet);
    }
}
