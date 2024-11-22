package com.example.instituteofthesouthpacific;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

public class ViewContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contacts);

        Button btnCall = findViewById(R.id.btnCall);
        btnCall.setOnClickListener(view -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:+17092182259"));
            startActivity(callIntent);
        });

        // Email
        Button btnEmail = findViewById(R.id.btnEmail);
        btnEmail.setOnClickListener(view -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:caleb.cooper36@ed.cna.nl.ca"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear Team,");
            startActivity(Intent.createChooser(emailIntent, "Send Email"));
        });

        // Location
        Button btnLocation = findViewById(R.id.btnLocation);
        btnLocation.setOnClickListener(view -> {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW);
            mapIntent.setData(Uri.parse("geo:0,0?q=College of the North Atlantic Engineering Technology, St. John's, Canada"));
            startActivity(mapIntent);
        });

        ProgressBar progressBar = findViewById(R.id.progressBar);
        WebView webView = findViewById(R.id.webView);

        // WebView Settings
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDomStorageEnabled(true);

        // WebViewClient with Progress Bar to show loading process
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }
        });
            webView.loadUrl("https://www.cna.nl.ca/");

    }

}
