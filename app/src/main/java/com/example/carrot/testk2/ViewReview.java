package com.example.carrot.testk2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ViewReview extends AppCompatActivity {

    WebView view2;

    String Content;
    String Title;
    String Category;

    TextView tv1;
    TextView tv2;

    String userName;

    EditText mEditText;
    private final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        //Content = intent.getStringExtra("name");
        tv1 = (TextView) findViewById(R.id.textView2);
        tv2 = (TextView) findViewById(R.id.textView3);

        ParseUser currentUser = ParseUser.getCurrentUser();
        userName = currentUser.getUsername();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ReviewList");
        query.whereEqualTo("Maker", userName);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject object : objects) {
                        Content = object.getString("link");
                        Title = object.getString("Title");
                        Category = object.getString("category");
                    }

                   // Log.d("ss", Content);
                    Toast.makeText(ViewReview.this, "불러와짐", Toast.LENGTH_SHORT).show();
                    asdf();
                }

            }
        });

        mEditText = (EditText) findViewById(R.id.editText);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        view2 = (WebView) findViewById(R.id.webView3);
        view2.setWebChromeClient(new WebChromeClient());
        view2.getSettings().setJavaScriptEnabled(true);
        view2.loadUrl("file:///android_asset/View.htm");


    }

    public void asdf(){

        tv1.setText(Title);
        tv2.setText(Category);
        view2.loadUrl("javascript:result('"+Content+"')");
    }

}
