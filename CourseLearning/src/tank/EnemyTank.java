package tank;

import Game.Gameframe;
import until.MyUtil;
import until.TanksPool;
import until.constant;

import java.awt.*;

/*
*                 敌人坦克类
* */

public class EnemyTank extends Tank{

    //图片数组
    private static Image[] enemyImg;
    private long AITime;
    static {
        //敌方坦克
        enemyImg=new Image[4];
        enemyImg[0]=Toolkit.getDefaultToolkit().createImage("F:/JavaProject/Practice/CourseLearning/res/ul.png");
        enemyImg[1]=Toolkit.getDefaultToolkit().createImage("F:/JavaProject/Practice/CourseLearning/res/dl.png");
        enemyImg[2]=Toolkit.getDefaultToolkit().createImage("F:/JavaProject/Practice/CourseLearning/res/ll.png");
        enemyImg[3]=Toolkit.getDefaultToolkit().createImage("F:/JavaProject/Practice/CourseLearning/res/rl.png");

    }

    private EnemyTank(int x, int y, int dir) {
        super(x, y, dir);
        //一旦敌人创建便开始计时
        AITime = System.currentTimeMillis();

    }

    public EnemyTank(){
        AITime = System.currentTimeMillis();
    }


    //用于创建敌人坦克
    public static Tank createEnemy(){
        int x = MyUtil.getRandomNumber(0,2) == 0 ? RADIUS<<1 : constant.FrameWide-RADIUS*2;
        int y = Gameframe.titleBarH + RADIUS;
        int dir =Dir_Down;
        //Tank enemy = new EnemyTank(x,y,Dir_Down);
        //TODO
        Tank enemy = TanksPool.getTank();
        enemy.setEnemy(true);
        enemy.setDir(dir);
        enemy.setX(x);
        enemy.setY(y);
        enemy.setState(State_Move);
        enemy.setHp(Tank.Default_hp);
        return enemy;
    }

    public void drawImgTank(Graphics g){
        g.drawImage(enemyImg[getDir()],getX()-RADIUS,getY()-RADIUS,null);
        AI();
    }

    //TODO
    public void AI(){
        if(System.currentTimeMillis()-AITime > constant.Enemy_AI_Interval){
            super.setDir(MyUtil.getRandomNumber(Dir_Up,Dir_Right+1));
            super.setState( MyUtil.getRandomNumber(0,2) == 0 ? State_Move : State_Stand);
            AITime = System.currentTimeMillis();
        }
        if(Math.random()<constant.Enemy_Fire_Percent){
            super.fire();
        }
    }
}
