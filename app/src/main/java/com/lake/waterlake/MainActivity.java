package com.lake.waterlake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.lake.waterlake.home.FrameGridAdapter;
import com.lake.waterlake.home.FrameGridView;
import com.lake.waterlake.util.RequestParameter;
import com.lake.waterlake.network.AsyncHttpPost;
import com.lake.waterlake.network.RequestResultCallback;
import com.lake.waterlake.network.BaseRequest;
import com.lake.waterlake.network.DefaultThreadPool;

import java.util.ArrayList;
import java.util.List;

import org.json.*;

public class MainActivity extends AppCompatActivity {
    /**
     * ewe
     */
    private FrameGridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_frame);

//        gridview = (FrameGridView) findViewById(R.id.mygridView);
//        gridview.setAdapter(new FrameGridAdapter(this));
        initwsLogin();


// fuyou sasdfadsf
//        ImageView loginimage = (ImageView)findViewById(R.id.imageView);
//
//        loginimage.setBackgroundDrawable(getResources().getDrawable(R.mipmap.ic_launcher));

        // loginimage.setImageDrawable();

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    /**
     * 初始化通用平台登陆session
     */
    public static void initwsLogin() {
        List<RequestParameter> parameters = new ArrayList<RequestParameter>();
        parameters.add(new RequestParameter("userName", ApplicationGlobal.loginwsName));
        parameters.add(new RequestParameter("password", ApplicationGlobal.loginwspwd));
        System.out.println("session 0--> 连接成功 ！！！！！");
        AsyncHttpPost httpget = new AsyncHttpPost(ApplicationGlobal.URL_login, parameters,

                new RequestResultCallback() {
                    @Override
                    public void onSuccess(String str) {
                        try {
                            JSONObject jsonObj = new JSONObject(str);
                            ApplicationGlobal.WSSessionId = jsonObj.getString("login");

                    	System.out.println("session --> 连接成功 ！！！！！");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        e.printStackTrace();
                    }
                });
        DefaultThreadPool.getInstance().execute(httpget);
        BaseRequest.getBaseRequests().add(httpget);
    }

    /**
     * 测试方式测试数据是否可以按照新方式调用
     */
    public static void test() {
        List<RequestParameter> parameters = new ArrayList<RequestParameter>();
        parameters.add(new RequestParameter("userName", ApplicationGlobal.loginwsName));
        parameters.add(new RequestParameter("password", ApplicationGlobal.loginwspwd));
        System.out.println("session 0--> 连接成功 ！！！！！");
        AsyncHttpPost httpget = new AsyncHttpPost(ApplicationGlobal.URL_login, parameters,

                new RequestResultCallback() {
                    @Override
                    public void onSuccess(String str) {
                        try {
                            JSONObject jsonObj = new JSONObject(str);
                            ApplicationGlobal.WSSessionId = jsonObj.getString("login");

                            System.out.println("session --> 连接成功 ！！！！！");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        e.printStackTrace();
                    }
                });
        DefaultThreadPool.getInstance().execute(httpget);
        BaseRequest.getBaseRequests().add(httpget);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
