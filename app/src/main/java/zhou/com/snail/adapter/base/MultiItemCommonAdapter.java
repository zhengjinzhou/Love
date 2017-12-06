package zhou.com.snail.adapter.base;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**

 *   多类型通用的Adapter

 * Created by junjianliu

 * time: 2017/6/13.

 */

public abstract class MultiItemCommonAdapter<T> extends BaseCommonAdapter<T> {

    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;

    public MultiItemCommonAdapter(Context mContext, List<T> mDatas,
                                  MultiItemTypeSupport<T> mMultiItemTypeSupport) {
        super(mContext, -1, mDatas);
        this.mMultiItemTypeSupport = mMultiItemTypeSupport;
    }

    @Override
    public int getItemViewType(int position)
    {
        return mMultiItemTypeSupport.getItemViewType(position, mDatas.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
        ViewHolder holder = ViewHolder.get(mContext, parent, layoutId);
        return holder;
    }
}
