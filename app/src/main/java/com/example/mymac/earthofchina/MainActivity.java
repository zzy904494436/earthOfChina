package com.example.mymac.earthofchina;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;

public class MainActivity extends AppCompatActivity {

    private TextureMapView mapView ;
    private BaiduMap baiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        mapView = (TextureMapView) findViewById(R.id.mapView);
        //渲染自定义 的布局
        View view = LayoutInflater.from(this).inflate(R.layout.cus_layout,null);

        //获得百度地图的控件器
        baiduMap =mapView.getMap();
        //定义中心点
        LatLng centerPoint = new LatLng(38.889965,-77.009064);
        //定义缩放级别和设置地图中心店
        MapStatus mapStatus = new MapStatus.Builder().target(centerPoint).zoom(18).build();
        MapStatusUpdate mapUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        //更新地图的显示
        baiduMap.setMapStatus(mapUpdate);


        //指定添加的位置和范围
        MapViewLayoutParams.Builder builder  = new MapViewLayoutParams.Builder();
        builder.point(new Point(500,800))
                .width(700)
                .height(700);
        MapViewLayoutParams params = builder.build();

        //添加到地图上
        mapView.addView(view,params);

        //把比例尺英藏
       // mapView.showScaleControl(false);
       // mapView.showZoomControls(false);

        //监听 地图是否加载完成
        baiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //设定比例尺和缩放控件的位置
                mapView.setScaleControlPosition(new Point(200,100));
                mapView.setZoomControlsPosition(new Point(800,300));
            }
        });
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mappu:
                baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapwei:
                baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.maptra:
                baiduMap.setTrafficEnabled(true);
                break;
            case R.id.mapclosetra:
                baiduMap.setTrafficEnabled(false);
                break;
            case R.id.mapheat:
                baiduMap.setBaiduHeatMapEnabled(true);
                break;
            case R.id.mapcloseheat:
                baiduMap.setBaiduHeatMapEnabled(false);
                break;
            case R.id.menumarker:
                //创建标注点
                LatLng markerPoint = new LatLng(38.889965,-77.009064);
                //创建标注图标
                BitmapDescriptor markIcon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
                //创建标注图层
                OverlayOptions overlay = new MarkerOptions()
                        .position(markerPoint)
                        .icon(markIcon)
                        .zIndex(9)
                        .draggable(true);
                //添加到地图上
                baiduMap.addOverlay(overlay);
                break;

            case R.id.annimenumarker:
                //创建标注点
                LatLng markerPoint1 = new LatLng(38.889965,-77.009064);
                //创建标注图标
                BitmapDescriptor markIcon1 = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
                //创建标注图层
                OverlayOptions overlay1 = new MarkerOptions()
                        .position(markerPoint1)
                        .icon(markIcon1)
                        .animateType(MarkerOptions.MarkerAnimateType.drop);
                //添加到地图上
                baiduMap.addOverlay(overlay1);
                break;
            case R.id.annimenumarker2:
                //创建标注点

                break;
            case R.id.clean:
                baiduMap.clear();

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
