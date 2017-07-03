package com.lucky.permissionmanagelearn.rxpermission;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lucky.permissionmanagelearn.ToastUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * Created by zfz on 2017/7/3.
 */

public class RxPermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions();
    }


    private void requestPermissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_SMS

        ).subscribe(
                new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {//  点击Allow的时候会调用
                            ToastUtil.showToast(RxPermissionActivity.this, permission.name + "is Granted!");
                        } else if (permission.shouldShowRequestPermissionRationale) {  //当只点击Deny的时候会调用
                            ToastUtil.showToast(RxPermissionActivity.this, permission.name + "should be enabled!, You can open setting to enable it");
                        } else {  //当点击Never Ask 和 Deny的时候会调用
                            ToastUtil.showToast(RxPermissionActivity.this, permission.name + "is Denied!");
                        }
                    }
                }
        );
    }

}


