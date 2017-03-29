package com.lucky.permissionmanagelearn;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zfz on 2017/3/29.
 */

public class BaseActivity extends AppCompatActivity {

    private PermissionListener mPermissionListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void requestPermission(String[] permissions,PermissionListener listener){
        mPermissionListener=listener;
        List<String> permissionList=new ArrayList<>();
        for(String permission:permissions){
            int checkResult=ContextCompat.checkSelfPermission(this,permission);
            if (checkResult !=PackageManager.PERMISSION_GRANTED){
                permissionList.add(permission);
            }
        }
        if(!permissionList.isEmpty()){
            String[] unGrantedPermissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this,unGrantedPermissions,1);
        }else{
            listener.onGranted();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                   List<String> unGrantedPermissions=new ArrayList<>();
                   for(int index=0;index<grantResults.length;index++){
                       int result=grantResults[index];
                       if(PackageManager.PERMISSION_DENIED==result){
                          unGrantedPermissions.add(permissions[index]);
                       }
                   }
                   if(unGrantedPermissions.isEmpty()){
                      //全部授权
                       mPermissionListener.onGranted();
                   }else{
                      //有部分没有给予授权
                      mPermissionListener.onDenied(unGrantedPermissions);
                   }

                }
                break;
        }
    }
}
