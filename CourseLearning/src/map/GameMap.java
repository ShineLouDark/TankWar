package map;

import Game.Bullet;
import Game.Gameframe;
import tank.Tank;
import until.MaptilePool;
import until.MyUtil;
import until.constant;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameMap {
    public static final int Mapx = Tank.RADIUS*3;
    public static final int Mapy = Tank.RADIUS*3;
    public static final int MapW = constant.FrameWide-Tank.RADIUS*6;
    public static final int MapH = constant.FrameHigh-Tank.RADIUS*8- Gameframe.titleBarH;
    private List<MapTile> mapTiles = new ArrayList<>();

    //地图的构造方法
    public GameMap(){
        initMap();
    }

    //初始化地图
    public void initMap(){
        final int count = 50;
        for (int i = 0; i < count; i++) {
            MapTile mapTile = MaptilePool.get();
            int x = MyUtil.getRandomNumber(Mapx,Mapx+MapW-MapTile.tileW);
            int y = MyUtil.getRandomNumber(Mapy,Mapy+MapH-MapTile.tileW);
            if(isTileCollide(mapTiles,x,y)){
                i--;
                continue;
            }
            mapTile.setX(x);
            mapTile.setY(y);
            mapTiles.add(mapTile);
        }
    }

    // TODO 这里的算法有问题，后期和over时的图片一样需要调整 [MapTile.tileW have a problem]
    // TODO 无法获取真实的TileW值 [我在MapTile中将TileW做成静态值]
    // TODO 使用ImageIcon的对象可以获取到图片的长
    private boolean isTileCollide(List<MapTile> mapTiles,int x,int y){
        for (MapTile mapTile : mapTiles) {
            int Tilex= mapTile.getX();
            int TileY=mapTile.getY();
            //System.out.println("cxv"+'+'+MapTile.tileW+'+'+Tilex+'+'+x);
            if(Math.abs(Tilex- mapTile.getRadius()-x)<MapTile.tileW && Math.abs(TileY- mapTile.getRadius()-y)<MapTile.tileW){
                //System.out.println("sdada");
                 return true;
            }
        }
        return false;
    }

    //画出地图
    public void draw(Graphics g){
        for (MapTile mapTile : mapTiles) {
            mapTile.drawTile(g);
        }
    }

    public List<MapTile> getMapTiles() {
        return mapTiles;
    }

    //移除所有的不可见的砖块
    public void ClearDestroyTile(){
        for (int i = 0; i < mapTiles.size(); i++) {
            MapTile mapTile = mapTiles.get(i);
            if(!mapTile.isVisable()){
                mapTiles.remove(i);
            }
        }
    }
}


