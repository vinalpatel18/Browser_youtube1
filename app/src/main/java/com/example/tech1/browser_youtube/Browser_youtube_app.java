package com.example.tech1.browser_youtube;


import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class Browser_youtube_app extends AppCompatActivity {

    //this webview is the main layout on which our youtube page is loaded
    public WebView mywebview;

    //this arraylist is used to store the all the url (history) so that we can call them when we click on back button or for other task
    public ArrayList<String> UrlHistory = new ArrayList<String>();

        private class CrmClient extends WebChromeClient {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        //public MyWebClient() {}ca
       /* @Override
        public Bitmap getDefaultVideoPoster()
        {
            if (Browser_youtube_app.this == null) {
                return null;
            }
            return BitmapFactory.decodeResource(Browser_youtube_app.this.getApplicationContext().getResources(), 2130837573);
        }
        */
       /*   This progress changed will catch the url and verify if it is youtube url or not if it is not youtube url it will redirect to previous page*/

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            Log.d("progresschange URL", view.getUrl());

            String currenturl = view.getUrl();


            Log.d("with  # current",currenturl);
            currenturl= currenturl.replaceFirst("/#","");
            Log.d("without current",currenturl);
            if (UrlHistory.get(UrlHistory.size()-1).equals(currenturl)) {
                Log.d("URLHISTORY current",currenturl);
            } else {
                UrlHistory.add(currenturl);
                Log.d("URLHISTORY",UrlHistory.toString());
            }


            //checking the url has youtube in it or not if not redirect it to default browser

            if (currenturl.contains("youtube")) {

                Log.d("URL", "has youtube");
            } else {
                Log.d("URL", "doenot have youtube");

               // mywebview.stopLoading();
               // mywebview.loadUrl(previoururl);  Log.d("current URL", currenturl);
                // this intent will open the url without youtube in another default browser
                Intent browseintent = new Intent(Intent.ACTION_VIEW,Uri.parse(currenturl));
                startActivity(browseintent);
                Log.d("befremove",UrlHistory.toString());
               UrlHistory.remove(UrlHistory.size()-1);
                Log.d("afrremove",UrlHistory.toString());
                mywebview.loadUrl(UrlHistory.get(UrlHistory.size()-1));
                Log.d("lodremove",UrlHistory.toString());

            }



            super.onProgressChanged(view, newProgress);
        }



        public void onHideCustomView() {
            ((FrameLayout) Browser_youtube_app.this.getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            Browser_youtube_app.this.getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            Browser_youtube_app.this.setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback) {
            if (this.mCustomView != null) {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = Browser_youtube_app.this.getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = Browser_youtube_app.this.getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout) Browser_youtube_app.this.getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            Browser_youtube_app.this.getWindow().getDecorView().setSystemUiVisibility(3846);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//#############################################################################################################################################
        //       Reference Web sites:http://stackoverflow.com/questions/36275299/full-screen-option-not-available-when-loading-youtube-video-in-webview
//                              https://www.codepunker.com/blog/step-by-step-tutorial-on-how-to-create-your-own-android-web-view-application
//                              http://stackoverflow.com/questions/38842869/how-to-fullscreen-youtube-video-in-webview
//                              https://github.com/cprcrack/VideoEnabledWebView
//  Basic understanding         https://developer.chrome.com/multidevice/webview/gettingstarted
//  Basic:                      http://www.technotalkative.com/android-webviewclient-example/
//
//##############################################################################################################################################
        //hide starus bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // hide
        setContentView(R.layout.activity_browser_youtube_app);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.FrameLayout);

      /*  String url_id = "";
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            url_id = bundle.getString("url_id");
        }
        else{
        }
        Log.d("URL bundle ",url_id);

        */
        mywebview = (WebView) findViewById(R.id.activity_browser_youtube_app_webview);
       // newurl = (WebView) findViewById(R.id.activity_browser_youtube_app_webview);
        WebSettings webSettings = mywebview.getSettings();
       // webSettings.setJavaScriptEnabled(true);
        mywebview.setWebViewClient(new WebViewClient());
        mywebview.setWebChromeClient(new CrmClient());
        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.getSettings().setMediaPlaybackRequiresUserGesture(false);
        mywebview.setVerticalScrollBarEnabled(false);
        mywebview.setHorizontalScrollBarEnabled(false);
        mywebview.setWebViewClient(new WebViewClient());
        mywebview.getSettings().setBuiltInZoomControls(true);
        mywebview.getSettings().setSupportZoom(true);

        mywebview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        mywebview.loadUrl("https://www.youtube.com/");
        Log.i("URL address 2 ", mywebview.getUrl());
        UrlHistory.add("https://www.youtube.com/");



    /*    if(url_id.isEmpty()){
            mywebview.loadUrl("https://www.youtube.com/");
                   }
        else{

            mywebview.loadUrl("http://www.youtube.com/watch_popup?v="+ url_id );
        }

        */
        Log.i("URL address 4 ", mywebview.getUrl());
    }


  /*  class CrmClient extends WebChromeClient {

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);
            Log.d("CRMlscreen url",mywebview.getUrl());
            String urll=mywebview.getUrl();

            urll=urll.replaceFirst("m","www");
            urll=urll.replace("watch","watch_popup/");
            Log.d("Crmchanged url",url);
            mywebview.loadUrl(url);
        }
        @Override
        public void onHideCustomView() {
            super.onHideCustomView();
        }
    }
*/
    //Link; http://stackoverflow.com/questions/31788748/webview-youtube-videos-playing-in-background-on-rotation-and-minimise
    // Usinf this onpause and onresume method you can resolve the problem of video continues to play in background.
    // Now you can go to home screen and video will pause and when you resurn back you can resume your video.
    // https://developer.android.com/guide/components/activities/activity-lifecycle.html

    @Override
    protected void onPause() {

        Log.d("onPAuse URL", mywebview.getUrl());
        super.onPause();
        if (mywebview != null) {
            mywebview.onPause();
            mywebview.pauseTimers();
        }
    }

    @Override
    protected void onResume() {
        Log.d("onresume URL", mywebview.getUrl());
        super.onResume();
        if (mywebview != null) {
            mywebview.onResume();
            mywebview.resumeTimers();
        }
    }

    /*@Override
    public void onBackPressed() {
        Log.d("onbackpress URL", mywebview.getUrl());

            UrlHistory.remove(UrlHistory.size() - 1);
            mywebview.loadUrl(UrlHistory.get(UrlHistory.size() - 1));
        Log.d("URLHISTORY back",UrlHistory.toString());

    }
*/
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed(); // if back button is pressed twice it will exit the application and go to home screen
            return;

        }

        Log.d("onbackpress URL", mywebview.getUrl()); //if back button is pressed once it will load back this url and continue

        UrlHistory.remove(UrlHistory.size() - 1); // remove the last saved page from urlhistory array
        mywebview.loadUrl(UrlHistory.get(UrlHistory.size() - 1));
        Log.d("URLHISTORY back",UrlHistory.toString());
        this.doubleBackToExitPressedOnce = true;
        final Toast toast= Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT); // toast is displayed
        toast.show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
                toast.cancel();
            }
        }, 800);
    }



}
