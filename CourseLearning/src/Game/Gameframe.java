package Game;

import map.GameMap;
import map.MapTile;
import tank.EnemyTank;
import tank.MyTank;
import tank.Tank;
import until.MyUtil;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static until.constant.*;

public class Gameframe extends Frame implements Runnable {

    /*
    *                         用双缓存解决图片闪烁的问题
    * */
    //定义一张与屏幕大小一致的图片
    private BufferedImage bufImg = new BufferedImage(FrameWide,FrameHigh,BufferedImage.TYPE_4BYTE_ABGR);

    //游戏状态
    private static int gameState;
    //菜单指向
    private int menuIndex;

    private Image overimage = null;

    //标题栏的高度
    public static int titleBarH;

    //定义坦克对象
    private Tank myTank;

    //定义敌人坦克容器
    private List<Tank> enemies = new ArrayList<>();

    //键值的定义
    private  boolean KeyUp=false;
    private  boolean KeyRight=false;
    private  boolean KeyLeft=false;
    private  boolean KeyDown=false;

    private GameMap gameMap;
    private List<MapTile>mapTiles ;

    /*
     * 对窗口进行初始化
     * */
    public Gameframe() {
        intFrame();
        initEventListener();
        //paintComponent(getGraphics());【不能瞎写了】
        //启动用于刷新窗口的线程
        new Thread(this).start();
    }

    /*
     * 对属性进行初始化（绘制框架）
     */
    private void intFrame() {
        setTitle(SetTile);
        setSize(FrameWide, FrameHigh);
        setVisible(true);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        titleBarH=getInsets().top;
        //setDefaultCloseOperation(3);
        //repaint();   用了一个线程来刷新repaint，所以不需要再调用repaint方法了
    }
    /*
     * 对游戏进行初始化
     * */
    private void initGame() {
        gameState = State_menu;
    }

    /*
     * 绘制菜单
     * */
    private void drawMenu(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, FrameWide, FrameHigh);
        g.setColor(Color.WHITE);
        g.setFont(new Font("隶书", Font.BOLD, 50));
        int x = FrameWide - 200 >> 1;
        int y = FrameHigh / 3;
        final int dis = 50;
        for (int i = 0; i < menu.length; i++) {
            if(i==menuIndex){//选中为红色
                g.setColor(Color.RED);
            }
            else{
                g.setColor(Color.WHITE);
            }
            g.drawString(menu[i], x, y + dis * i);
        }
    }
//    protected void paintComponent(Graphics g) {
//        super.paintComponents(g);
//        drawMenu(g);
//    }
    /*
     * 还看不懂的部分
     * */

    @Override
    public void update(Graphics g1) {
        //得到图片画笔
        Graphics g = bufImg.getGraphics();
        switch (gameState) {
            case State_menu:
                drawMenu(g);
                break;
            case State_help:
                drawHelp(g);
                break;
            case State_about:
                drawAbout(g);
                break;
            case State_start:
                drawStart(g);
                break;
            case State_over:
                drawOver(g);
                break;
        }
        //使用系统画笔，将图片绘制到主界面
        g1.drawImage(bufImg,0,0,null);
    }

    private void drawOver(Graphics g) {
        if(overimage == null){
            overimage = MyUtil.createImage("F:/JavaProject/Practice/CourseLearning/res/over.png");
        }
        int imgW = overimage.getWidth(null);
        int imgH = overimage.getHeight(null);
        g.drawImage(overimage,FrameWide-imgW,FrameHigh-imgH,null);
        g.drawString(Over_1,10,FrameHigh-20);
        g.drawString(Over_2,FrameWide-200,FrameHigh-20);

    }

    private void drawStart(Graphics g) {
        //绘制黑色背景
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, FrameWide, FrameHigh);
        gameMap.draw(g);
        drawEnemy(g);

        myTank.draw(g);
        BulletCollideTank();
        BulletCollideMaptile();
        // TODO wno do 坦砖碰撞
        TankCollideTile();
        drawExplode(g);
    }

    private void drawEnemy(Graphics g){
        for(int i =0;i<enemies.size();i++){
            Tank enemy=enemies.get(i);
            if(enemy.isDie()){
                enemies.remove(i);
                i--;
                continue;
            }
            enemy.draw(g);
        }
    }

    private void drawAbout(Graphics g) {
        
    }

    private void drawHelp(Graphics g) {
        
    }
    /*
    * 事件的监听
    * */
    private void initEventListener(){
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Close the Window");
                System.exit(0);
            }
        });
        //添加按键监听事件
        addKeyListener(new KeyAdapter() {
            //按键按下的时候回调方法
            @Override
            public void keyPressed(KeyEvent e) {
                //获得键值
                int keyCode = e.getKeyCode();
                //不同的游戏状态，不同的处理方法
                switch (gameState) {
                    case State_menu:
                        KeyPressedEventMenu(keyCode);
                        break;
                    case State_help:
                        KeyPressedEventHelp(keyCode);
                        break;
                    case State_about:
                        KeyPressedEventAbout(keyCode);
                        break;
                    case State_start:
                        KeyPressedEventStart(keyCode);
                        break;
                    case State_over:
                        KeyPressedEventOver(keyCode);
                        break;
                }
            }

            private void KeyPressedEventStart(int keyCode) {
                switch (keyCode){
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        myTank.setDir(Tank.Dir_Up);  //   可以通过导包来实现这个静态属性
                        myTank.setState(Tank.State_Move);
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        myTank.setDir(Tank.Dir_Down);
                        myTank.setState(Tank.State_Move);
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        myTank.setDir(Tank.Dir_Left);
                        myTank.setState(Tank.State_Move);
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        myTank.setDir(Tank.Dir_Right);
                        myTank.setState(Tank.State_Move);
                        break;
                    case KeyEvent.VK_SPACE:
                        myTank.fire();
                        break;
                }
                    //同时按下上键与右键
                if (keyCode == KeyEvent.VK_UP) {
                        KeyUp = true;
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                        KeyRight = true;
                }
                if (KeyUp && KeyRight) {
                        myTank.setDir(Tank.Dir_RightUp);
                        myTank.setState(Tank.State_Move);
                }

                    //同时按下上键与左键
                if (keyCode == KeyEvent.VK_UP) {
                        KeyUp = true;
                    } else if (keyCode == KeyEvent.VK_LEFT) {
                        KeyLeft = true;
                }
                if (KeyUp && KeyLeft) {
                        myTank.setDir(Tank.Dir_LeftUp);
                        myTank.setState(Tank.State_Move);
                }


                    //同时按下下键与右键
                if (keyCode == KeyEvent.VK_DOWN) {
                        KeyDown = true;
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                        KeyRight = true;
                }
                if (KeyDown && KeyRight) {
                        myTank.setDir(Tank.Dir_RightDown);
                        myTank.setState(Tank.State_Move);
                }


                    //同时按下下键与左键
                if (keyCode == KeyEvent.VK_DOWN) {
                        KeyDown = true;
                } else if (keyCode == KeyEvent.VK_LEFT) {
                        KeyLeft = true;
                }
                if (KeyDown && KeyLeft) {
                    myTank.setDir(Tank.Dir_LeftDown);
                    myTank.setState(Tank.State_Move);
                }
            }

            private void KeyPressedEventAbout(int keyCode) {

            }

            private void KeyPressedEventHelp(int keyCode) {

            }
            //菜单状态下的按键处理
            private void KeyPressedEventMenu(int keyCode) {
                switch (keyCode){
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        menuIndex--;//默认值是0
                        if (menuIndex < 0){
                            menuIndex = menu.length - 1;
                        }
                        //repaint();
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        menuIndex++;
                        if(menuIndex > menu.length-1){
                            menuIndex=0;
                        }
                        //repaint();
                        break;
                    case KeyEvent.VK_ENTER:
                        newGame();
                        break;
                }
            }

            private void KeyPressedEventOver(int keyCode) {
                switch (keyCode){
                    case KeyEvent.VK_ESCAPE :
                        System.exit(0);
                    case KeyEvent.VK_ENTER:
                        setGameState(State_menu);
                        RestGame();
                }
            }

            //按键松开的时候回调内容
            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if(gameState == State_start){
                    keyReleasedEventStart(keyCode);
                }
            }
        });
    }

    private void RestGame(){
        menuIndex=0;
        //先移除对象的子弹
        myTank.bulletsReturn();
        myTank=null;
        //移除敌人坦克子弹
        for (Tank enemy : enemies) {
            enemy.bulletsReturn();
        }
        //清空敌人坦克列表
        enemies.clear();
        //清空地图资源
        gameMap = null;
    }

    //按键松开的时候游戏的处理办法
    private void keyReleasedEventStart(int keyCode) {
        switch (keyCode){
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                myTank.setState(Tank.State_Stand);
                KeyRight=false;
                KeyUp=false;
                KeyLeft=false;
                KeyDown=false;
                break;
        }
    }

    /*
    * 开始新游戏的方法
    * */
    private void newGame(){
        gameState = State_start;
        //创建坦克对象，敌人的坦克对象
        myTank = new MyTank(400,200,Tank.Dir_Down);//初始化坦克对象
        gameMap = new GameMap();

        //使用单独的线程用于控制产生敌人的坦克
        new Thread(){
            @Override
            public void run() {
                while(true){
                if(enemies.size()<Enemy_Max_Count){
                    Tank enemy = EnemyTank.createEnemy();
                    enemies.add(enemy);
                }
                try {
                    Thread.sleep(Enemy_Born_Interval);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                if(gameState!=State_start){
                    break;
                }
            }
        }
        }.start();
    }

    @Override
    public void run() {
        while (true){
            repaint();
            try {
                Thread.sleep(Repaint_interval);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //敌人坦克与我方坦克的子弹碰撞
    //敌人坦克的子弹与我方坦克的碰撞
    private void BulletCollideTank(){
        //我方坦克与敌方的子弹碰撞
        for(Tank EnemyTank : enemies){
            myTank.collideBullets(EnemyTank.getBullets());
        }
        //敌方坦克与我方的子弹碰撞
        for(Tank EnemyTank : enemies){
            EnemyTank.collideBullets(myTank.getBullets());
        }
    }

   /* //所有子弹与砖块的碰撞
    private void BulletCollideMaptile(){
        mapTiles=gameMap.getMapTiles();
        //我方坦克子弹与砖块的碰撞
        for (MapTile mapTile : mapTiles) {
            // TODO 用来实验我以前自己写的错误方法
            //System.out.println(mapTile.getX());
            //myTank.bulletsCollideMapTile(mapTile);
            if(mapTile.isCollideBullet(myTank.getBullets())){
                //销毁砖块，添加爆炸效果 TODO tile的坐标需要调整一下
                myTank.addExplode(mapTile.getX(),mapTile.getY());
            }
        }
        //敌人坦克子弹与砖块碰撞
        for (MapTile mapTile : mapTiles) {
            for (Tank enemy : enemies) {
                if(mapTile.isCollideBullet(enemy.getBullets())){
                    //销毁砖块，添加爆炸效果
                }
            }
        }
    }*/
    //所有子弹与砖块的碰撞
   private void BulletCollideMaptile(){
       mapTiles = gameMap.getMapTiles();
       myTank.bulletsCollideMapTile(mapTiles);
       for (Tank enemy : enemies) {
           enemy.bulletsCollideMapTile(mapTiles);
       }
       //移除所有的不可见的块
       gameMap.ClearDestroyTile();
   }

   //所有坦克与砖块碰撞 TODO won do 坦砖碰撞
    private void TankCollideTile(){
       mapTiles = gameMap.getMapTiles();
       myTank.isCollideTile(mapTiles);
        for (Tank enemy : enemies) {
            enemy.isCollideTile(mapTiles);
        }
    }

    //绘制所有坦克的爆炸效果
    private void drawExplode(Graphics g){
        myTank.drawExplode(g);
        for (Tank EnemyTank : enemies){
            EnemyTank.drawExplode(g);
        }
    }

    public static void setGameState(int gameState) {
        Gameframe.gameState = gameState;
    }

    public static int getGameState() {
        return gameState;
    }
}