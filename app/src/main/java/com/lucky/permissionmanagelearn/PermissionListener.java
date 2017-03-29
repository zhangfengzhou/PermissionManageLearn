package com.lucky.permissionmanagelearn;

import java.util.List;

/**
 * Created by zfz on 2017/3/29.
 */

public interface PermissionListener {

    void onGranted();
    void onDenied(List<String> permissions);
}
