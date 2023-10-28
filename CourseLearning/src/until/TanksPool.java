package until;

import Game.Bullet;
import tank.EnemyTank;
import tank.Tank;

import java.util.ArrayList;
import java.util.List;

public class TanksPool {
    public static final int Default_Pool_MaxSize= 20;
    public static final int Default_Pool_Size = 20;
    private static List<Tank> tanks = new ArrayList<>();
    static {
        for (int i = 0; i < Default_Pool_Size; i++) {
            tanks.add(new EnemyTank());
        }
    }

    /*
    * 从对象池中获取一个对象
    * */
    public static Tank getTank(){
        Tank enemyTank = null;
        //对象池被掏空了
        if(tanks.size()==0){
            enemyTank = new EnemyTank();
        }else {
            enemyTank = tanks.remove(0);
        }
//        System.out.println("甚于："+pool.size());
        return enemyTank;
    }

    public static void returnTank(Tank enemyTank){
        if (tanks.size()==Default_Pool_MaxSize){
            return;
        }
        tanks.add(enemyTank);
    }
}
