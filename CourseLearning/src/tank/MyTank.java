package tank;

import until.MyUtil;

import java.awt.*;

/*
*                   自己坦克类
* */

public class MyTank extends Tank{

    //坦克的图片数组
    private static Image[] tankImg;

    //对图片进行初始化
    static {
        tankImg=new Image[8];
        tankImg[0]= MyUtil.createImage("F:/JavaProject/Practice/CourseLearning/res/u.png");
        tankImg[1]=MyUtil.createImage("F:/JavaProject/Practice/CourseLearning/res/d.png");
        tankImg[2]=MyUtil.createImage("F:/JavaProject/Practice/CourseLearning/res/l.png");
        tankImg[3]=MyUtil.createImage("F:/JavaProject/Practice/CourseLearning/res/r.png");
        tankImg[4]=MyUtil.createImage("F:/JavaProject/Practice/CourseLearning/res/r.png");
        tankImg[5]=MyUtil.createImage("F:/JavaProject/Practice/CourseLearning/res/r.png");
        tankImg[6]=MyUtil.createImage("F:/JavaProject/Practice/CourseLearning/res/r.png");
        tankImg[7]=MyUtil.createImage("F:/JavaProject/Practice/CourseLearning/res/r.png");

    }
    public MyTank(int x, int y, int dir) {
        super(x, y, dir);
    }

    @Override
    public void drawImgTank(Graphics g) {
        g.drawImage(tankImg[getDir()],getX()-RADIUS,getY()-RADIUS,null);
    }
}
