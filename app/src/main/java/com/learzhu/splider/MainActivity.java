package com.learzhu.splider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.learzhu.splider.test.TestSplider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button testBtn;
    private TextView showResultTv;
    private String url = "http://www.oschina.net/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        testBtn = (Button) findViewById(R.id.test_btn);
        testBtn.setOnClickListener(this);
        showResultTv = (TextView) findViewById(R.id.show_result_tv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_btn:
                doTest();
//                testSplider();
                break;
        }
    }

    private void doTest() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                testSplider();
            }
        });
        thread.start();
    }

    private void testSplider() {
//        try {
//            Document mDocument = Jsoup.connect(url).data("query", "Java").userAgent("I'm Jsoup").cookie("Lear", "token").timeout(3000).post();
//            String mS = mDocument.toString();
//            showResultTv.setText(mS);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        TestSplider testSplider = new TestSplider();
        final StringBuilder mDatasByClass = testSplider.getDatasByClass();
        final StringBuilder mDatasByCssQuery = testSplider.getDatasByCssQuery();
        showResultTv.post(new Runnable() {
            @Override
            public void run() {
                showResultTv.setText(mDatasByClass.toString() + " \n*****" + mDatasByCssQuery.toString());
            }
        });
    }
}
