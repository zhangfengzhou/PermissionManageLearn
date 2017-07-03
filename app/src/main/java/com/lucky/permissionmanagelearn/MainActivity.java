package com.lucky.permissionmanagelearn;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lucky.permissionmanagelearn.rxpermission.RxPermissionActivity;

/**
 * 注意：权限管理在Android 6.0之后才会有的 6.0之前没有做权限管理设置
 * 所以在应用的时候要考虑手机系统的版本
 * */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG="MainActivity";
    private static final int REQUEST_PERMISSION_CALL_PHONE=1;
    private static final int REQUEST_PERMISSION_READ_WRITE=2;
    private boolean isFirst=true;//是否第一次点击不再提示按钮 Don't ask

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnCallPhone=(Button) findViewById(R.id.btn_call_phone);
        Button btnReadWrite= (Button) findViewById(R.id.btn_read_write);
        Button btnNext=(Button) findViewById(R.id.btn_next);
        Button btnRxPermission = (Button) findViewById(R.id.btn_rxpermission);
        btnCallPhone.setOnClickListener(this);
        btnReadWrite.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnRxPermission.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.btn_call_phone:
               if(!SystemIntentUtil.isPermissionManageNeed()){
                   showToast("当前系统版本号是 "+ Build.VERSION.SDK_INT);
                   return;
               }
               if(!accessPermission(Manifest.permission.CALL_PHONE)){
                   ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_PERMISSION_CALL_PHONE);
               }else{
                    showToast("Call Phone 已经被授权");
               }
               break;
           case R.id.btn_read_write:
               if(!accessPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
                   ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_PERMISSION_READ_WRITE);
               }else{
                   showToast("Read Write  已经被授权");
               }
               break;
           case R.id.btn_next:
               Intent intent=new Intent(this,SecondActivity.class);
               startActivity(intent);
               break;
           case R.id.btn_rxpermission:
               startActivity(new Intent(this, RxPermissionActivity.class));
       }
    }

    public int checkHasPermission(Context context,String permission){
        return ContextCompat.checkSelfPermission(context,permission);
    }

    public boolean accessPermission(String permission){
        int checkResult=checkHasPermission(this,permission);
        if(PackageManager.PERMISSION_GRANTED==checkResult){
            return true;
        }else if(PackageManager.PERMISSION_DENIED==checkResult){
            return false;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_PERMISSION_CALL_PHONE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                   showToast("点击授权 Call Phone被授权成功！");
                }else{
                    //没有给予授权
                    //点击deny按钮
                    if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE)){
                        showToast("点击拒绝按钮");
                    }else{
                        if(isFirst){
                           showToast("第一次点击了不再询问按钮");
                           isFirst=!isFirst;
                        }else{
                            //非第一次点击询问按钮
                           showToast("非第一次点击了不再询问按钮,可以进入设置界面");
                           Intent intent=SystemIntentUtil.getAppDetailSettingIntent(getPackageName());
                           Intent intent1=SystemIntentUtil.gotoMiuiPermission(getPackageName());
                           startActivity(intent1);
                        }
                    }
                }
                break;
            case REQUEST_PERMISSION_READ_WRITE:

                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showToast(String message){
         ToastUtil.showToast(this,message);
    }

}
