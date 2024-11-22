package com.example.instituteofthesouthpacific;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class ViewPublicTransitInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_public_transit_info);

        // Find the WebView by its unique ID
        WebView webView = findViewById(R.id.web);

        if (webView == null) {
            throw new NullPointerException("WebView not found in the layout.");
        }

        // Enable JavaScript
        webView.getSettings().setJavaScriptEnabled(true);

        // Load the URL
        webView.loadUrl("https://www.metrobus.com/home/");

        //Make Webpage fit the screen properly
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setInitialScale(1);


        // Handle URL loading within the WebView
        webView.setWebViewClient(new WebViewClient());
    }
}
