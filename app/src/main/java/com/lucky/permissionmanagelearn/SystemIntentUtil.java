package com.lucky.permissionmanagelearn;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

/**
 * Created by zfz on 2017/3/29.
 */

public class SystemIntentUtil {

    /**
     * 对于原生系统，和其他系统，如果找不到方法，也可以先把用户引导到系统设置页面
     * 获取应用详情页面intent
     *
     * @return
     */
    public static Intent getAppDetailSettingIntent(String packageName) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", packageName, null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", packageName);
        }
        return localIntent;
    }


    /**
     * 华为的权限管理页面
     */
    public static Intent gotoHuaweiPermission(String packageName) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
        intent.setComponent(comp);
        return intent;
    }

    /**
     * 跳转到魅族的权限管理系统
     */
    public static  Intent gotoMeizuPermission(String packageName) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", packageName);
        return intent;
    }

    /**
     * 跳转到miui的权限管理页面
     */
    public static Intent gotoMiuiPermission(String packageName) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.setComponent(componentName);
        intent.putExtra("extra_pkgname",packageName);
        return intent;
    }

    /**
     * API大于23的时候需要显示权限管理
     * */
    public static boolean isPermissionManageNeed(){
        return Build.VERSION.SDK_INT>Build.VERSION_CODES.M;
    }

}
