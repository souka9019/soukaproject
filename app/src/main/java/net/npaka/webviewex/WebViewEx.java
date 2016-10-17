package net.npaka.webviewex;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WebViewEx extends Activity {
	private WebView webview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = (LinearLayout)getLayoutInflater().inflate(R.layout.main,
       		 null);
        setContentView(layout);
        
        webview = (WebView)findViewById(R.id.webView1);

        //webview = new WebView(this);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(mClient);
        //Attach the custom interface to the view
        webview.addJavascriptInterface(new MyJavaScriptInterface(), "BRIDGE");
  
        webview.loadUrl("file:///android_asset/sample.html");
        //webview.loadUrl("http://www.baidu.com");

    }

    private static final String JS_SETELEMENT = "javascript:document.getElementById('%s').value='%s'";
    private static final String JS_GETELEMENT = 
                    "javascript:window.BRIDGE.storeElement('%s',document.getElementById('%s').value)";


    private static final String ELEMENTID = "emailAddress";

    private WebViewClient mClient = new WebViewClient()  {
        @Override
        //public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //    return false;
        //}


        //@Override
        public void onPageFinished(WebView view, String url) {
        	SharedPreferences prefs = getSharedPreferences("WebViewEx",MODE_PRIVATE);
            //When page loads, inject address into page using JavaScript
            //SharedPreferences.Editor edit = prefs.edit();
            //edit.putString("text", "testtest");
            //edit.commit();
            //android.util.Log.e("debug", prefs.getString(ELEMENTID, ""));
            webview.loadUrl(String.format(JS_SETELEMENT, ELEMENTID, prefs.getString("text", "")));
        }
    };

    private class MyJavaScriptInterface {
        //Store an element in preferences
        @SuppressWarnings("unused")
        public void gotoURL(final String url){
        	Handler mHandler = new Handler();
        	mHandler.post(new Runnable() {  
        		public void run() {  
        	    	webview.loadUrl(String.format(JS_GETELEMENT,ELEMENTID,ELEMENTID));   
        			webview.loadUrl(url);  
        		}  
        	});  

        }
        
        public void storeElement(String id, String element) {
            SharedPreferences.Editor edit = getSharedPreferences("WebViewEx",MODE_PRIVATE).edit();
            edit.putString("text", element);
            edit.commit();
            //TextView textview = (TextView)findViewById(R.id.text1);
            //textview.setText(element.toString());
        	//Handler mHandler = new Handler();
        	//mHandler.post(new Runnable() {  
        	//	public void run() {  
        	//		Toast.makeText(WebViewEx.this, "Sorry, buddy", Toast.LENGTH_SHORT).show();
        			//mWebView.loadUrl("javascript:wave()");  
        	//	}  
        	//});  

            //If element is valid, raise a Toast
        	Toast.makeText(WebViewEx.this, "emailAddress has saved", Toast.LENGTH_SHORT).show();

        }
    }
    
    public void onClick1(View v) {
        //Before leaving the page, attempt to retrieve the email using JavaScript
    	webview.loadUrl(String.format(JS_GETELEMENT,ELEMENTID,ELEMENTID));    
        webview.loadUrl("file:///android_asset/sample1.html");
    }
    public void onClick2(View v) {
        //Before leaving the page, attempt to retrieve the email using JavaScript
    	webview.loadUrl(String.format(JS_GETELEMENT,ELEMENTID,ELEMENTID));    
        webview.loadUrl("file:///android_asset/sample.html");
    }
}
