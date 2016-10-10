package com.lake.waterlake;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import com.lake.waterlake.home.FrameGridView;

import com.lake.waterlake.util.RequestParameter;
import com.lake.waterlake.network.AsyncHttpPost;
import com.lake.waterlake.network.RequestResultCallback;
import com.lake.waterlake.network.BaseRequest;
import com.lake.waterlake.network.DefaultThreadPool;
import com.lake.waterlake.util.WSFunction;

import java.util.ArrayList;
import java.util.List;
import org.json.*;

public class MainActivity extends  Activity implements View.OnClickListener{
    /**
     *
     */
    private FrameGridView gridview;

    private EditText LoginName,LoginPwd;

    private Button LoginIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_frame);
        LoginIn    =  (Button)findViewById(R.id.sign_in_button);
        LoginName  =  (EditText)findViewById(R.id.login_name);
        LoginPwd   =  (EditText)findViewById(R.id.password);

        initwsSession();// init session
        LoginIn.setOnClickListener(this);
        //initwsLogin();
        System.out.println("-----------onCreate-----------");
    }

    @Override
    public  void  onResume(){
        super.onResume();
        SharedPreferences sf =
                getSharedPreferences(ApplicationGlobal.SHARED_PRENFENCE_NAME, Context.MODE_PRIVATE);
        String account = sf.getString(ApplicationGlobal.SHARED_PRENFENCE_ACCOUNT,null);
        String password =  sf.getString(ApplicationGlobal.SHARED_PRENFENCE_PASSWORD,null);
        if(account != null || password !=null){
            LoginName.setText(account);
            LoginPwd.setText(password);
        }

        System.out.println("-----------onResume-----------");
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("-----------onStart-----------");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        System.out.println("-----------onRestart-----------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("-----------onStop-----------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("-----------onDestroy-----------");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button://登陆
               login();
                break;
        }
    }


    /**
     *  登陆
     */
    public void login(){
        // 判断session 是否有值
        if(ApplicationGlobal.WSSessionId!= null ){
            checkLogin(LoginName,LoginPwd);

        }else{

        }
    }

    public  void  checkLogin(EditText LoginName,EditText LoginPwd){
        List<RequestParameter>  parameters =
                WSFunction.getParameters(ApplicationGlobal.WSSessionId, "waterLake.login",
                        new String[]{"userName","userpwd"},
                        new String[]{LoginName.getText().toString(),LoginPwd.getText().toString()});
        AsyncHttpPost   httpget = new AsyncHttpPost(ApplicationGlobal.URL_read, parameters,
                new RequestResultCallback() {
                    @Override
                    public void onSuccess(String str) {
                        try {
                            JSONArray jarray = new JSONArray(str);
                            String userId = ((JSONObject)jarray.get(0)).getString("USER_ID");
                            String userName = ((JSONObject)jarray.get(0)).getString("USER_NAME");

                            // if true
                            // login  sucess
                            SharedPreferences sf =
                                    getSharedPreferences(ApplicationGlobal.SHARED_PRENFENCE_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sf.edit();
                            editor.putString(ApplicationGlobal.SHARED_PRENFENCE_ACCOUNT, userName);
                            editor.putString(ApplicationGlobal.SHARED_PRENFENCE_PASSWORD, userName);
                            editor.commit();
                            // handler send data
                            Message msg =  new Message();
                            msg.what=110;
                            msg.obj =true;
                            mHandler.sendMessage(msg);

                        } catch (Exception e) {e.printStackTrace();}
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
     * 异步回调,更新页面数据
     */
    Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 110:
                    Boolean jumpFlag = (Boolean)msg.obj;
                    if (jumpFlag){

                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, RadioMainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{

                    }
                    break;
                case 112:

                    break;
            }
        };
    };

    /**
     * 初始化通用平台登陆session
     */
    public static void initwsSession() {
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


}
