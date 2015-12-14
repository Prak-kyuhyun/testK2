package com.example.carrot.testk2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class Review extends AppCompatActivity {

    WebView view1;
    String Title1 = "asdf";
    String Content = "asdf";
    String userName = "asdf";
    String Category = "asdf";

    public String getUserName() {
        return userName;
    }

    public String getTitle1() {
        return Title1;
    }

    public String getContent() {
        return Content;
    }

    public String getCategory() {
        return Category;
    }

    public void setTitle1(String title1) {
        Title1 = title1;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCategory(String category) {
        Category = category;
    }


    private final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // mTextView = (TextView) findViewById(R.id.textView);

        Button btn1 = (Button) findViewById(R.id.button3);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view1.loadUrl("javascript:result()");
            }
        });

        Button btn2 = (Button) findViewById(R.id.button4);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                final ParseObject ReviewList = new ParseObject("ReviewList");
                ReviewList.put("Maker",currentUser.getUsername());
                ReviewList.put("Title", getTitle1());
                ReviewList.put("link", getContent());
                ReviewList.put("category", getCategory());
                ReviewList.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (null == e) {
                            Toast.makeText(getApplicationContext(), "작성이 완료되었습니다.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "작성이 실패.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        view1 = (WebView) findViewById(R.id.webView);
        view1.getSettings().setJavaScriptEnabled(true);
        view1.loadUrl("file:///android_asset/Review.htm");
        view1.setWebChromeClient(new WebChromeClient() {
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                Review.this.startActivityForResult(
                        Intent.createChooser(i, ".."),
                        FILECHOOSER_RESULTCODE);
            }
        });
        view1.getSettings().setPluginState(WebSettings.PluginState.ON);
        view1.getSettings().setBuiltInZoomControls(false);
        view1.getSettings().setSupportZoom(false);
        view1.addJavascriptInterface(new MyWebInterface(), "android");
    }
    private ValueCallback<Uri> mUploadMessage;
    private final static int FILECHOOSER_RESULTCODE = 1;
    @Override
    protected  void onActivityResult(int requestCode, int resultCode,
                                     Intent intent) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode != RESULT_OK ? null
                    : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
    }

    public class MyWebInterface {
        @JavascriptInterface
        public void setMessage(final String arg) {
            handler.post(new Runnable() {
                public void run() {
                    Content = arg;
                    setContent(arg);

                }
            });
        }
        @JavascriptInterface
        public void getTitle(final String arg) {
            handler.post(new Runnable() {
                public void run() {
                    Title1 = arg;
                    setTitle1(arg);
                }
            });
        }
        @JavascriptInterface
        public void getCategory(final String arg) {
            handler.post(new Runnable() {
                public void run() {
                    Category = arg;
                    setCategory(arg);
                }
            });
        }
    }
}
