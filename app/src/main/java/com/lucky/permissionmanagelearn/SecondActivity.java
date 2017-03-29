package com.lucky.permissionmanagelearn;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import java.util.List;

/**
 * Created by zfz on 2017/3/29.
 */

public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Button btnGrant=(Button)findViewById(R.id.btn_grant_access);
        btnGrant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permissions=new String[]{
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS
                };
                requestPermission(permissions, new PermissionListener() {
                    @Override
                    public void onGranted() {
                         ToastUtil.showToast(SecondActivity.this,"全部给与授权");
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        StringBuffer stringBuffer=new StringBuffer();
                        for(String permission:permissions){
                            stringBuffer.append(permission+"\n");
                        }
                        ToastUtil.showToast(SecondActivity.this,stringBuffer.toString());
                    }
                });
            }
        });
    }
}
