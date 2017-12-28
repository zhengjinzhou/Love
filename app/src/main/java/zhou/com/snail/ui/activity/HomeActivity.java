package zhou.com.snail.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.OnClick;
import zhou.com.snail.R;
import zhou.com.snail.base.AppManager;
import zhou.com.snail.base.BaseActivity;
import zhou.com.snail.ui.fragment.FriendFragment;
import zhou.com.snail.ui.fragment.HomeFragment;
import zhou.com.snail.ui.fragment.MeFragment;
import zhou.com.snail.ui.fragment.TaskFragment;
import zhou.com.snail.util.ToastUtil;

import static zhou.com.snail.R.id.tv_me;

public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";

    @BindView(R.id.fragment_content)
    FrameLayout fragmentContent;
    private HomeFragment homeFragment;
    private MeFragment meFragment;
    private Fragment[] fragments;
    private int currentTabIndex = 0;
    private FriendFragment friendFragment;

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void init() {
        initFragment();
    }


    private void initFragment() {
        homeFragment = new HomeFragment();
        meFragment = new MeFragment();
        friendFragment = new FriendFragment();

        fragments = new Fragment[]{homeFragment, friendFragment, meFragment};
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_content, homeFragment)
                .hide(homeFragment)
                .add(R.id.fragment_content, friendFragment)
                .hide(friendFragment)
                .add(R.id.fragment_content, meFragment)
                .hide(meFragment)
                .show(homeFragment)
                .commit();
    }

    @OnClick({R.id.tv_home, R.id.tv_me, R.id.tv_friend})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_home:
                showTabFragment(0);
                break;
            case R.id.tv_friend:
                showTabFragment(1);
                break;
            case R.id.tv_me:
                showTabFragment(2);
                break;
        }
    }

    private void showTabFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[currentTabIndex]);
        if (!fragments[position].isAdded()) {
            transaction.add(R.id.fragment_content, fragments[position]);
        }
        transaction.show(fragments[position]);
        transaction.commit();
        currentTabIndex = position;
    }

    private long firstTime = 0;
    private boolean isExit = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                ToastUtil.show(getApplicationContext(), "再按一次退出程序");
                firstTime = secondTime;
            } else {
                AppManager.getAppManager().AppExit(this);
                finish();
            }
        }
        return false;
    }
}
