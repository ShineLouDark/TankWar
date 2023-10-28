package Game;

import tank.Tank;
import until.constant;

import java.awt.*;

/*
*                     子弹类
* */
public class Bullet {
    //子弹速度为坦克的两倍
    public static final int DEFAULT_SPEED = Tank.DEFAULT_SPEED<<1;
    //子弹半径
    public static final int RADIUS=4;

    private int x,y;
    private int speed=DEFAULT_SPEED;
    private int dir;
    private int atk;
    private Color color;
    //子弹是否可见
    private boolean visible = true;

    public Bullet(int x, int y, int dir, int atk, Color color) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.atk = atk;
        this.color=color;
    }

    //给对象池使用
    public Bullet() {}

    /*
    *             炮弹的自身的绘制方法
    * */
    public void draw(Graphics g){
        if(!visible)return;
        g.setColor(color);
        g.fillOval(x-RADIUS,y-RADIUS,RADIUS<<1,RADIUS<<1);
        logic();
    }

    /*
    *          子弹的逻辑
    * */
    private void logic(){
        move();
    }

    private void move(){
        switch (dir){
            case Tank.Dir_Up:
                y-=speed;   //速度为8
                if(y<0){
                    visible = false;
                }
                break;
            case Tank.Dir_Down:
                y+=speed;
                if(y> constant.FrameHigh){
                    visible = false;
                }
                break;
            case Tank.Dir_Left:
                x-=speed;
                if(x<0){
                    visible = false;
                }
                break;
            case Tank.Dir_Right:
                x+=speed;
                if(x>constant.FrameWide){
                    visible = false;
                }
                break;
            case Tank.Dir_RightUp:
                y-=speed;
                x+=speed;
                if(y<0){
                    visible = false;
                } else if (x>constant.FrameWide) {
                    visible=false;
                }
                break;
            case Tank.Dir_RightDown:
                x+=speed;
                y+=speed;
                if(y>constant.FrameHigh){
                    visible=false;
                } else if (x>constant.FrameWide) {
                    visible=false;
                }
                break;
            case Tank.Dir_LeftUp:
                x-=speed;
                y-=speed;
                if(x<0){
                    visible=false;
                } else if (y<0) {
                    visible=false;
                }
                break;
            case Tank.Dir_LeftDown:
                x-=speed;
                y+=speed;
                if(x<0){
                    visible=false;
                } else if (y>constant.FrameHigh) {
                    visible=false;
                }
                break;
        }
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

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color color){
        this.color=color;
    }

    public boolean isVisible(){
        return visible;
    }

    public void setVisible(boolean visible){
        this.visible=visible;
    }
}
