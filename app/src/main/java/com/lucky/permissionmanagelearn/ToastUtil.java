package com.lucky.permissionmanagelearn;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zfz on 2017/3/29.
 */

public class ToastUtil {

   public static void showToast(Context context,String message){
       Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
   }
}
