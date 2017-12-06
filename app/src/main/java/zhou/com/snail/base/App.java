package zhou.com.snail.base;

import android.app.Application;

import zhou.com.snail.bean.PersonalBean;
import zhou.com.snail.bean.SelectBean;
import zhou.com.snail.bean.UserInfo;

/**
 * Created by zhou on 2017/10/21.
 */

public class App extends Application{

    private UserInfo userInfo;

    private PersonalBean personalBean;

    private SelectBean selectBean;

    public SelectBean getSelectBean() {
        return selectBean;
    }

    public void setSelectBean(SelectBean selectBean) {
        this.selectBean = selectBean;
    }

    public PersonalBean getPersonalBean() {
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    private static App app;

    public static App getInstence() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
