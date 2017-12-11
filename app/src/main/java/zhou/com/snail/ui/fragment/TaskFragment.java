package zhou.com.snail.ui.fragment;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zhou.com.snail.R;
import zhou.com.snail.adapter.base.CommonAdapter;
import zhou.com.snail.adapter.base.ViewHolder;
import zhou.com.snail.base.BaseFragment;
import zhou.com.snail.view.WaterWaveView;

/**
 * Created by zhou on 2017/10/22.
 */

public class TaskFragment extends BaseFragment{

    @BindView(R.id.recycle) RecyclerView recycle;
    @BindView(R.id.scrollView) NestedScrollView scrollView;
    @BindView(R.id.wave_view) WaterWaveView mWaterWaveView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_task;
    }

    @Override
    protected void init(View v) {
        mWaterWaveView.setmWaterLevel(0.3F);
        mWaterWaveView.startWave();
        recycle.setNestedScrollingEnabled(false);

        initRecycle();
    }

    /**
     * 初始化recyclerview
     */
    private void initRecycle() {
        List<BaseData> data = new ArrayList<>();
        data.add(new BaseData(R.drawable.task_10,false,"今日签到",5));
        data.add(new BaseData(R.drawable.task_5,false,"今日签到",5));
        data.add(new BaseData(R.drawable.task_6,false,"分享任务",5));
        data.add(new BaseData(R.drawable.task_9,false,"今日签到",5));
        data.add(new BaseData(R.drawable.task_8,false,"今日签到",5));
        data.add(new BaseData(R.drawable.task_7,false,"今日签到",5));
        data.add(new BaseData(R.drawable.task_7,false,"今日签到",5));
        data.add(new BaseData(R.drawable.task_7,false,"今日签到",5));

        CommonAdapter adapter = new CommonAdapter<BaseData>(getContext(), R.layout.recycle_task, data) {
            @Override
            public void convert(ViewHolder holder, BaseData baseData, int position) {

            }
        };
        recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        recycle.setAdapter(adapter);
    }


    /**
     * 自定义bean
     */
    private class BaseData{

        private int icon;
        private boolean isComplete;
        private String name;
        private int gold;

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public boolean isComplete() {
            return isComplete;
        }

        public void setComplete(boolean complete) {
            isComplete = complete;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }

        public BaseData(int icon, boolean isComplete, String name, int gold) {
            this.icon = icon;
            this.isComplete = isComplete;
            this.name = name;
            this.gold = gold;
        }

        public BaseData() {

        }
    }
}
