package com.tencent.qcloud.tuicore.component.fragments;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

public class BaseFragment extends Fragment {

    public void forward(Fragment fragment, boolean hide) {
        forward(getId(), fragment, null, hide);
    }

    public void forward(int viewId, Fragment fragment, String name, boolean hide) {
        if (getFragmentManager() == null){
            return;
        }
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        if (hide) {
            trans.hide(this);
            trans.add(viewId, fragment);
        } else {
            trans.replace(viewId, fragment);
        }

        trans.addToBackStack(name);
        trans.commitAllowingStateLoss();
    }

    public void backward() {
        if (getFragmentManager() == null){
            return;
        }
        getFragmentManager().popBackStack();
    }
}
