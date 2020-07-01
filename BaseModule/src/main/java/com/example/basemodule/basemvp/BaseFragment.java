package com.example.basemodule.basemvp;

import androidx.fragment.app.Fragment;

import com.example.basemodule.utils.log.Log;

/**
 * create by zy on 2019/10/24
 * </p>
 */
public class BaseFragment extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
        Log.d(this.getClass().getSimpleName(), "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(this.getClass().getSimpleName(), "onPause");
    }

    public boolean hasExist() {
        return isAdded() && !isDetached();
    }

}
