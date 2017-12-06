package zhou.com.snail.base.other;

/**
 * Created by zhou on 2017/10/25.
 */

public interface PermissionsResultListener {
    void onPermissionGranted();

    void onPermissionDenied();
}
