package com.learzhu.splider;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.learzhu.splider.net.HttpClientUtil;
import com.learzhu.splider.test.TestSplider;

import org.jsoup.helper.StringUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button testBtn, loginBtn;
    private TextView showResultTv;
    private EditText userNameEt, userPwdEt;
    private String url = "http://www.oschina.net/";
    private String loginUrl = "https://www.e-shenhua.com/ec/index.jsp?locale=zh_CN&_requestid=8221//com/shenhua/userprofiling/ShenHuaProfileFormHandler.login";
    private static final String TAG = "MainActivity";
    /*用户名密码*/
    private String userName, userPwd;

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
        showResultTv.setMovementMethod(ScrollingMovementMethod.getInstance());
        loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
        userNameEt = (EditText) findViewById(R.id.user_name_et);
        userPwdEt = (EditText) findViewById(R.id.user_pwd_et);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_btn:
                doTest();
//                testSplider();
                break;
            case R.id.login_btn:
                doLogin();
                break;
        }
    }

    /**
     * 用户登录
     */
    private void doLogin() {
        userName = userNameEt.getText().toString();
        userPwd = userPwdEt.getText().toString();
        Log.i(TAG, "username " + userName + " userPwd " + userPwd);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClientUtil httpClient = new HttpClientUtil();
                HttpClientUtil.MultipartForm form = httpClient.new MultipartForm();
                //设置form属性、参数
//                form.setAction("http://192.168.1.5:9001/api/Mobile/FileUpload/UploadFile");
                form.setAction("https://www.e-shenhua.com/ec/myaccount/login.jsp?DPSLogout=true/com/shenhua/userprofiling/ShenHuaProfileFormHandler.login");
//                String path = Environment.getExternalStorageDirectory().getPath() + "/DCIM" + "/20151120_051052.jpg";
//                Log.e("11", path);
//                File file = new File(path);
//                form.addFileField("file", file);
//                form.addNormalField("ID", "301201604");
                form.addNormalField("atg_store_registerLoginEmailAddress", userName);
                form.addNormalField("atg_store_registerLoginPassword", userPwd);
//                form.addNormalField("password", userPwd);
                final String resultcode = httpClient.submitForm(form);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showResultTv.setText(StringUtil.normaliseWhitespace(resultcode));
                    }
                });
              /*  new Thread(new Runnable() {
                    @Override
                    public void run() {

                    }
                }).start();*/
                showResultTv.post(new Runnable() {
                    @Override
                    public void run() {
                        showResultTv.setText(StringUtil.normaliseWhitespace(resultcode));
                    }
                });
//                showResultTv.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                });
            }
        }).start();
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
        final TestSplider testSplider = new TestSplider();
        final StringBuilder mDatasByClass = testSplider.getDatasByClass();
        final StringBuilder mDatasByCssQuery = testSplider.getDatasByCssQuery();
        showResultTv.post(new Runnable() {
            @Override
            public void run() {
//                showResultTv.setText(mDatasByClass.toString() + " \n*****" + mDatasByCssQuery.toString());
                showResultTv.setText(testSplider.getDataSpliterByClass());
            }
        });
    }
}
