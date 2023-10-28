package Game;

import until.MyUtil;

import java.awt.*;

/*
*            用来控制爆炸效果的类
*/
public class Explode {
    public static final int Explode_Frame_count=4;
    private static Image [] ExplodeImages;
    //爆炸效果的坐标
    private int x;
    private int y;
    //爆炸效果的当前帧数
    private int index;
    //播放完后就消失
    private boolean isVisable ;
    private int explodeWidth;
    private int explodeHeigh;

    //TODO 对象池使用
    public Explode() {}

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //导入资源
    static {
        ExplodeImages = new Image[Explode_Frame_count];
        for (int i = 0; i < ExplodeImages.length; i++) {
            ExplodeImages[i]=MyUtil.createImage("F:/JavaProject/Practice/CourseLearning/res/boom_"+i+".png");
        }
    }

    //将图片画出来
    public void draw(Graphics g){
        //对爆炸效果图片的宽高确定
        if(explodeHeigh <= 0){
            explodeHeigh = ExplodeImages[0].getHeight(null);
            explodeWidth = ExplodeImages[0].getWidth(null)>>1;
        }
        if(!isVisable){
            return;
        }
        g.drawImage(ExplodeImages[index],x-explodeWidth,y-explodeHeigh,null);
        index++;
        if(index == Explode_Frame_count){
            isVisable=false;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isVisable() {
        return isVisable;
    }

    public void setVisable(boolean visable) {
        isVisable = visable;
    }
}
