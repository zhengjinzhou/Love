package zhou.com.snail.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by zhou on 2017/10/21.
 */

public abstract class BaseFragment extends Fragment {

    protected abstract int getLayout();

    protected abstract void init(View v);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, rootView);                    //绑定framgent
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        init(view);
    }

    protected void startToActivity(Class<?> lazz){
        Intent intent = new Intent(getActivity(),lazz);
        startActivity(intent);
    }
}