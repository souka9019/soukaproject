package net.npaka.appwidgetex;
import java.util.*;
import android.app.*;
import android.appwidget.AppWidgetManager;
import android.content.*;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.RemoteViews;
     
//ホームウィジェットを制御するサービス
public class AppWidgetService extends Service {
    private static final String ACTION_BTNCLICK1 =
        "net.npaka.AppWidgetService.ACTION_BTNCLICK1";
    private static final String ACTION_BTNCLICK2 =
            "net.npaka.AppWidgetService.ACTION_BTNCLICK2";
    private static final String ACTION_IMGCLICK =
            "net.npaka.AppWidgetService.ACTION_IMGCLICK";
    private int idx=1;
    
    //サービス開始時に呼ばれる
    @Override
    public void onStart(Intent intent,int startId) {
        super.onStart(intent, startId);
        
        //リモートビューの生成(3)
        RemoteViews view=new RemoteViews(getPackageName(),R.layout.appwidget);

        //ペンディングインテントの設定(4)
        Intent newintent1=new Intent();
        newintent1.setAction(ACTION_BTNCLICK1);
        PendingIntent pending1=PendingIntent.getService(this,0,newintent1,0);
        view.setOnClickPendingIntent(R.id.button1,pending1);
        
        //ペンディングインテントの設定(4)
        Intent newintent2=new Intent();
        newintent2.setAction(ACTION_BTNCLICK2);
        PendingIntent pending2=PendingIntent.getService(this,0,newintent2,0);
        view.setOnClickPendingIntent(R.id.button2,pending2);
        
        //アプリ内のアクティビティを呼び出すインテントの生成(1)
        Intent intent3=new Intent();
        intent3.setAction(ACTION_IMGCLICK);
        PendingIntent pending3=PendingIntent.getService(this,0,intent3,0);
        view.setOnClickPendingIntent(R.id.imageview1,pending3);

        
        //振るボタンがクリックされた時の処理(5)
        if (ACTION_BTNCLICK1.equals(intent.getAction())) {
            btnClicked(view,"BTN1_CLICK");
        } else if (ACTION_BTNCLICK2.equals(intent.getAction())) {
            btnClicked(view,"BTN2_CLICK");
        } else if (ACTION_IMGCLICK.equals(intent.getAction())) {
            btnClicked(view,"IMG_CLICK");
        } 
        
        //ホームスクリーンウィジェットの画面更新(6)
        AppWidgetManager manager=AppWidgetManager.getInstance(this);
        ComponentName widget=new ComponentName(
            "net.npaka.appwidgetex",
            "net.npaka.appwidgetex.AppWidgetEx");
        manager.updateAppWidget(widget,view);
    }
     
    //バインダを返す
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
     
    //振るボタンがクリックされた時の処理(4)
    public void btnClicked(RemoteViews view, String text){
        int[] ids={
            R.drawable.dice1,R.drawable.dice2,R.drawable.dice3,
            R.drawable.dice4,R.drawable.dice5,R.drawable.dice6};
        
        if(text.equals("BTN1_CLICK") ){
        	idx=rand(6);
        	view.setImageViewResource(R.id.imageview1,ids[idx]);
        } else if(text.equals("BTN2_CLICK") ){
        	idx=0;
            view.setImageViewResource(R.id.imageview1,R.drawable.dice1);
        } else if(text.equals("IMG_CLICK")){
        	//view.setImageViewResource(R.id.imageview1,R.drawable.dice6);
            //アプリ内のアクティビティを呼び出すインテントの生成(1)
            Intent intent3=new Intent(this,
            		net.npaka.appwidgetex.MyActivity.class);
            intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
            //intent3.setComponent(new ComponentName("net.npaka.appwidgetex","MyActivity"));
            //intent3.removeCategory(Intent.CATEGORY_DEFAULT);
            //intent3.addCategory(Intent.CATEGORY_LAUNCHER);
            //intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent3.putExtra("text","選んだ数字は"+(idx+1));
            this.startActivity(intent3);
        }
    }
    
    //乱数の取得
    private static Random rand=new Random();
    public static int rand(int num) {
        return (rand.nextInt()>>>1)%num;
    }    
}