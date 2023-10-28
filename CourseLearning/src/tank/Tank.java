package tank;
/*
*                    坦克类
* */

import Game.Bullet;
import Game.Explode;
import Game.Gameframe;
import map.MapTile;
import until.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Tank {
    //四个方向
    public static final int Dir_Up=0;
    public static final int Dir_Down=1;
    public static final int Dir_Left=2;
    public static final int Dir_Right=3;
    public static final int Dir_RightUp=4;
    public static final int Dir_RightDown=5;
    public static final int Dir_LeftUp=6;
    public static final int Dir_LeftDown=7;
    //半径
    public static final int RADIUS=20;
    //默认速度 每帧 30ms
    public static final int DEFAULT_SPEED=4;
    //坦克状态
    public static final int State_Stand=0;
    public static final int State_Move=1;
    public static final int State_Die=2;
    //坦克的初始生命
    public static final int Default_hp=1000;

    private int x,y;

    private int hp=Default_hp;
    private int atk;
    public static final int Atk_Max=100;
    public static final int Atk_Min=50;
    private int speed = DEFAULT_SPEED;
    private int dir;    //坦克方向
    private int state=State_Stand;
    private Color color;
    private boolean isEnemy=false;
    private String name;
    BloodBar bar = new BloodBar();

    //炮弹   （坦克炮弹的容器）   不可能只是一个炮弹  但为什么用List可以探索一下
    //此处的泛型是限制列表的类型
    //炮弹列表
    private List<Bullet> bullets = new ArrayList();//表示bullets是储存Bullet对象的列表
    private List<Explode> explodes = new ArrayList<>();

    public Tank(int x, int y, int dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        color=MyUtil.getRandomColor();
        name = MyUtil.getRandomName();
        atk=MyUtil.getRandomNumber(Atk_Min,Atk_Max);
    }

    public Tank(){
        initTank();
    }

    // TODO 为什么这里会有两个tank构造方法
    private void initTank(){
        color=MyUtil.getRandomColor();
        name = MyUtil.getRandomName();
        //atk=MyUtil.getRandomNumber(Atk_Min,Atk_Max);
        atk = 1;
    }


    /*
    *                   坦克的绘制
    * */
    public void draw(Graphics g){
        logic();
        drawImgTank(g);
        drawBullets(g);
        //drawExplode(g);
        drawName(g);
        bar.draw(g);
    }

    //绘制名字
    public void drawName(Graphics g){
        g.setColor(color);
        g.setFont(new Font("宋体",Font.BOLD,12));
        g.drawString(name,x-RADIUS,y-40);
    }

    //子弹的绘制
    private void drawBullets(Graphics g){
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
        //是遍历所有的子弹，将不可见的子弹移除，并返回对象池
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (!bullet.isVisible()){
                Bullet remove = bullets.remove(i);
                BulletsPool.theReturn(remove);
            }
        }
//        System.out.println("tank number:"+bullets.size());
    }

    //使用图片进行控制坦克
    public abstract void drawImgTank(Graphics g);

    //使用系统方法对坦克的绘制
    private void drawTank(Graphics g){

        g.setColor(color);

        //绘制坦克的圆
        g.fillOval(x-RADIUS,y-RADIUS,RADIUS<<1,RADIUS<<1);
        //绘制炮管
        int endX=x;
        int endY=y;
        switch(dir){
            case Dir_Up :
                endY=y-RADIUS*2;
                g.drawLine(x-1,y,endX-1,endY);
                g.drawLine(x+1,y,endX+1,endY);
                break;
            case Dir_Down:
                endY=y+RADIUS*2;
                g.drawLine(x-1,y,endX-1,endY);
                g.drawLine(x+1,y,endX+1,endY);
                break;
            case Dir_Left:
                endX=x-RADIUS*2;
                g.drawLine(x,y-1,endX,endY-1);
                g.drawLine(x,y+1,endX,endY+1);
                break;
            case Dir_Right:
                endX=x+RADIUS*2;
                g.drawLine(x,y-1,endX,endY-1);
                g.drawLine(x,y+1,endX,endY+1);
                break;
        }
        g.drawLine(x,y,endX,endY);
    }

    //坦克的逻辑处理
    private void logic(){
        switch (state){
            case State_Stand:
                break;
            case State_Move:
                move();
                break;
            case State_Die:
                break;
        }
    }

    //坦克移动的功能
    private void move(){
        switch (dir){
            case Dir_Up:
                y-=speed;   //速度为4
                if(y<RADIUS+Gameframe.titleBarH){
                    y=RADIUS+Gameframe.titleBarH;
                }
                break;
            case Dir_Down:
                y+=speed;
                if(y> constant.FrameHigh-RADIUS){
                    y=constant.FrameHigh-RADIUS;
                }
                break;
            case Dir_Left:
                x-=speed;
                if(x<RADIUS){
                    x=RADIUS;
                }
                break;
            case Dir_Right:
                x+=speed;
                if(x>constant.FrameWide-RADIUS){
                    x=constant.FrameWide-RADIUS;
                }
                break;
            case Dir_RightUp:
                x+=speed;
                y-=speed;
                if(x>constant.FrameWide-RADIUS && y<RADIUS+Gameframe.titleBarH){
                    x=constant.FrameWide-RADIUS;
                    y=RADIUS+Gameframe.titleBarH;
                } else if (x>constant.FrameWide-RADIUS) {
                    x=constant.FrameWide-RADIUS;
                } else if (y<RADIUS+Gameframe.titleBarH) {
                    y=RADIUS+Gameframe.titleBarH;
                }
                break;
            case Dir_RightDown:
                x+=speed;
                y+=speed;
                if(x>constant.FrameWide-RADIUS && y> constant.FrameHigh-RADIUS){
                    x=constant.FrameWide-RADIUS;
                    y=constant.FrameHigh-RADIUS;
                } else if (x>constant.FrameWide-RADIUS) {
                    x=constant.FrameWide-RADIUS;
                } else if (y> constant.FrameHigh-RADIUS) {
                    y=constant.FrameHigh-RADIUS;
                }
                break;
            case Dir_LeftUp:
                x-=speed;
                y-=speed;
                if(x<RADIUS && y<RADIUS+Gameframe.titleBarH){
                    x=RADIUS;
                    y=RADIUS+Gameframe.titleBarH;
                } else if (x<RADIUS) {
                    x=RADIUS;
                } else if (y<RADIUS+Gameframe.titleBarH) {
                    y=RADIUS+Gameframe.titleBarH;
                }
                break;
            case Dir_LeftDown:
                x-=speed;
                y+=speed;
                if(x<RADIUS && y> constant.FrameHigh-RADIUS){
                    x=RADIUS;
                    y=constant.FrameHigh-RADIUS;
                } else if (x<RADIUS) {
                    x=RADIUS;
                } else if (y>constant.FrameHigh-RADIUS) {
                    y=constant.FrameHigh-RADIUS;
                }
                break;
        }
    }

    public static int getDir_Up(){
        return Dir_Up;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List getBullets() {
        return bullets;
    }

    public void setBullets(List bullets) {
        this.bullets = bullets;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public void setEnemy(boolean enemy) {
        isEnemy = enemy;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return "Tank{" +
                "x=" + x +
                ", y=" + y +
                ", hp=" + hp +
                ", atk=" + atk +
                ", speed=" + speed +
                ", dir=" + dir +
                ", state=" + state +
                '}';
    }

    /*
    * 坦克的开火的功能
    * 创建了一个子弹对象，子弹对象的属性信息通过坦克的信息获得
    * 然后将创建的子弹添加到坦克管理的容器中。
    * 这里就相当于将子弹上膛
    * 最后的bullets.add还看不懂
    * */
    public void fire(){
        int bulletX = x;
        int bulletY = y;
        switch (dir){
            case Dir_Up:
                bulletY-=RADIUS;
                break;
            case Dir_Down:
                bulletY+=RADIUS;
                break;
            case Dir_Left:
                bulletX-=RADIUS;
                break;
            case Dir_Right:
                bulletX+=RADIUS;
                break;
        }
        Bullet bullet = BulletsPool.get();
        /*TODO
                           我现在还不明白这个，他这个为什么子弹类不能直接调用它本身的属性*/
        bullet.setX(bulletX);
        bullet.setY(bulletY);
        bullet.setDir(dir);
        bullet.setAtk(atk);
        bullet.setColor(color);
        bullet.setVisible(true);
        //bullet = new Bullet(bulletX,bulletY,dir,atk,color);
        bullets.add(bullet);
    }

    //碰撞判断方法  坦克和敌人的子弹碰撞
    public void collideBullets(List<Bullet> bullets){
        for(Bullet bullet : bullets){
            if(MyUtil.isCollide(bullet.getX(),bullet.getY(),RADIUS,x,y)){
                //子弹碰撞后就消失
                bullet.setVisible(false);
                // TODO 爆炸效果的坐标为什么是x，y
                addExplode(x,y);
                //碰撞后的伤害
                hurt(bullet);
            }
        }
    }

    //添加爆炸效果
    public void addExplode(int x,int y){
        Explode explode = ExplodesPool.get();
        explode.setX(x);
        explode.setY(y+RADIUS);
        explode.setVisable(true);
        explode.setIndex(0);
        explodes.add(explode);
    }

    //子弹与砖块碰撞 TODO own do 初步估计错误应该是对于砖块的坐标并没有调用正确，不清楚底层机制是什么
   /* public void bulletsCollideMapTile(MapTile mapTile){
        for (Bullet bullet : bullets) {
            //System.out.println(mapTile.Getx()+"+"+mapTile.Gety()+"+"+mapTile.getRadius());
            System.out.println(mapTile.getX()+mapTile.getRadius()+"+"+mapTile.getRadius()+"+"+bullet.getX()+"+"+bullet.getY());
            if(MyUtil.isCollide(mapTile.getX()+mapTile.getRadius(),mapTile.getY()+mapTile.getRadius(),mapTile.getRadius(),bullet.getX(),bullet.getY())){
                bullet.setVisible(false);
                Explode explode = ExplodesPool.get();
                explode.setX(x);
                explode.setY(y+RADIUS);
                explode.setVisable(true);
                explode.setIndex(0);
                explodes.add(explode);
            }
        }
    }*/

    // 每个坦克对象子弹与砖块碰撞【教程方法】
    public void bulletsCollideMapTile(List<MapTile> mapTiles){
        for (MapTile mapTile : mapTiles) {
            if(mapTile.isCollideBullet(bullets)){
                //添加爆炸效果
                addExplode(mapTile.getX(), mapTile.getY());
                //销毁砖块
                mapTile.setVisable(false);
                MaptilePool.Return(mapTile);
            }
        }
    }

    //坦克与砖块的碰撞 TODO won do 坦砖碰撞
    public void isCollideTile(List<MapTile> mapTiles){
        for (MapTile mapTile : mapTiles) {
            if(MyUtil.isCollide(mapTile.getX(),mapTile.getY(),mapTile.getRadius()+RADIUS-6,x,y)){
                back(mapTile);
            }
        }
    }

    //坦克退回 TODO won do 坦砖碰撞
    private void back(MapTile mapTile){
        switch (dir){
            case Dir_Up :
                y=y+speed;
                break;
            case Dir_Down:
                y=y-speed;
                break;
            case Dir_Left:
                x+=speed;
                break;
            case Dir_Right:
                x-=speed;
                break;
        }
    }

    //坦克受到的伤害
    public void hurt(Bullet bullet){
        int act = bullet.getAtk();
        hp-=act;
        //当坦克血量减少为0，坦克死亡
        if(hp<=0){
            die();
        }
    }

    //坦克死亡 TODO 不太理解为什么要自己删除自己Gameframe里已经删除了对象，为什么这里还要再删除一次。
    public void die(){
        if (isEnemy){
            TanksPool.returnTank(this);
        }else {
            Gameframe.setGameState(constant.State_over);
        }
    }

    //销毁坦克的时候先让所有子弹还回对象池
    public void bulletsReturn(){
        for (Bullet bullet : bullets) {
            BulletsPool.theReturn(bullet);
        }
        bullets.clear();
    }

    //判断坦克是否死亡
    public boolean isDie(){
        return hp<0;
    }

    //绘制当前坦克的所有爆炸效果
    public void drawExplode(Graphics g){
        for (Explode explode : explodes){
            explode.draw(g);
            if(!explode.isVisable()){
                ExplodesPool.theReturn(explode);
            }
        }
    }

    //血条内部类
    class BloodBar{
        public static final int Bar_Length=50;
        public static final int Bar_Hight=5;
        //绘制血条
        public void draw(Graphics g){
            //填充底色
            g.setColor(Color.YELLOW);
            g.fillRect(x-RADIUS,y-RADIUS-Bar_Hight*2,Bar_Length,Bar_Hight);
            //填充血条
            g.setColor(Color.RED);
            g.fillRect(x-RADIUS,y-RADIUS-Bar_Hight*2,hp*Bar_Length/Default_hp,Bar_Hight);
            //绘制方块
            g.setColor(Color.GREEN);
            g.drawRect(x-RADIUS,y-RADIUS-Bar_Hight*2,Bar_Length,Bar_Hight);
        }
    }
}
