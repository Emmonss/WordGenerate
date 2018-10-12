package com.example.emmons.wordgenerate;

import com.example.emmons.Function.Word_to_Doc;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by Emmons on 2018/9/29 0029.
 */

public class Html_show_Activity extends AppCompatActivity {

    private String savePath = "/mnt/sdcard/WordGenerate/file/html/";
    private String name = "temp";

    private Word_to_Doc wc = new Word_to_Doc();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);


        Intent intent=getIntent();
        String docPath = intent.getStringExtra("path");
        wc.opendoc(this,docPath,name,savePath);
        //Toast.makeText(Html_show_Activity.this,docPath,Toast.LENGTH_LONG).show();



        WebView webView = (WebView)this.findViewById(R.id.web);
        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webView.loadUrl("file:/"+savePath+name+".html");

    }
}
