package zhou.com.snail.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import zhou.com.snail.R;
import zhou.com.snail.adapter.base.MultiItemCommonAdapter;
import zhou.com.snail.adapter.base.MultiItemTypeSupport;
import zhou.com.snail.adapter.base.ViewHolder;
import zhou.com.snail.base.BaseFragment;

/**
 * Created by zhou on 2017/12/14.
 */

public class FriendFragment extends BaseFragment {

    @BindView(R.id.recycleView) RecyclerView recyclerView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_friend;
    }

    @Override
    protected void init(View v) {
        initRecycle();
    }

    /**
     *
     */
    private void initRecycle() {
        List<String> data = new ArrayList<>();
        data.add("1");data.add("2");data.add("3");data.add("3");data.add("3");
        data.add("2");data.add("1");data.add("2");data.add("2");data.add("1");
        MultiItemCommonAdapter adapter = new MultiItemCommonAdapter<String>(getContext(), data, new MultiItemTypeSupport<String>() {
            @Override
            public int getLayoutId(int itemType) {
                if (itemType == 1){
                    return R.layout.recycle_text;
                }else if (itemType == 2){
                    return R.layout.recycle_image;
                }
                return R.layout.recycle_video;
            }

            @Override
            public int getItemViewType(int position, String s) {
                if (s.equals("1")){
                    return 1;
                }else if(s.equals("2")){
                    return 2;
                }
                return 3;
            }
        }) {
            @Override
            public void convert(ViewHolder holder, String s, int position) {

            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}
