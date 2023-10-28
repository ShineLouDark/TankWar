package map;

import Game.Bullet;
import until.BulletsPool;
import until.MyUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MapTile {
    private static ImageIcon tileImage;
    private static Image image;
    public static int tileW;
    public static int a;
    private boolean isVisable = true;
    static {
        tileImage = new ImageIcon("F:/JavaProject/Practice/CourseLearning/res/tile.png");
        image = MyUtil.createImage("F:/JavaProject/Practice/CourseLearning/res/tile.png");
        tileW = tileImage.getIconHeight();
        a=image.getHeight(null);
    }

    public static int Radius = tileW/2;

    public MapTile() {
    }

    private int x,y;
    private int X=x;
    private int Y=y+Radius;

    //TODO 为什么在Tank类中的bulletsCollideMapTile方法没有获取成功x的值
    public int Getx(){
        return X;
    }
    public int Gety(){
        return Y;
    }

    // TODO 用来实验我以前的错误方法
    public int getA(){
        return  a;
    }
    public int getX() {
        return x+Radius;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y+Radius;
    }

    public int getRadius(){
        return Radius;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isVisable() {
        return isVisable;
    }

    public void setVisable(boolean visable) {
        isVisable = visable;
    }

    public MapTile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void drawTile(Graphics g){
        if(!isVisable){
            return;
        }
        Image image = tileImage.getImage();
        g.drawImage(image,x,y,null);
    }

    //判断子弹是否与砖块碰撞
    public boolean isCollideBullet(List<Bullet> bullets){
        if(!isVisable){
            return false;
        }
        for (Bullet bullet : bullets) {
            if(MyUtil.isCollide(x+Radius,y+Radius,Radius,bullet.getX(),bullet.getY())){
                bullet.setVisible(false);
                BulletsPool.theReturn(bullet);
                return true;
            }
        }
        return false;
    }
}
