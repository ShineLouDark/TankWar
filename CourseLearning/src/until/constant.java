package until;

import java.awt.*;

/*
* 游戏常量类
* */
public class constant {
    /*
    * 游戏窗口
    * */
    public static final String SetTile ="坦克大战";
    public static final int FrameWide=1150;//1150
    public static final int FrameHigh=700;//700

    //动态获取系统屏幕的高
    public static final int Screen_Width = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int Screen_High = Toolkit.getDefaultToolkit().getScreenSize().height;
    //其实这没必要

    /*
    * 游戏菜单
    * */
    public static final int State_menu=0;
    public static final int State_start=3;
    public static final int State_help=1;
    public static final int State_about =2;
    public static final int State_over=4;

    public static final String Over_1="ESC键退出游戏";
    public static final String Over_2="ENTER键回到主菜单";

    public static final String[] menu={
            "游戏开始","继续游戏","游戏帮助","游戏关于","游戏退出"
    };
    //重新绘制的间隔时间【线程的休眠时间】
    public static final int Repaint_interval =20;
    //最大敌人数量
    public static final int Enemy_Max_Count = 10;
    public static final int Enemy_Born_Interval = 1000;

    //AI移动的时间
    public static final int Enemy_AI_Interval = 3000;
    public static final double Enemy_Fire_Percent = 0.05;
}
