package zhou.com.snail.ui.fragment;

import android.view.View;

import butterknife.OnClick;
import zhou.com.snail.R;
import zhou.com.snail.base.BaseFragment;
import zhou.com.snail.ui.activity.HerActivity;

/**
 * Created by zhou on 2017/10/22.
 */

public class HomeFragment extends BaseFragment {


    private static final String TAG = "HomeFragment";

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init(View v) {

    }

    @OnClick({R.id.circleImage}) void onClick(View v){
        switch (v.getId()){
            case R.id.circleImage:
                startToActivity(HerActivity.class);
                break;
        }
    }
}
