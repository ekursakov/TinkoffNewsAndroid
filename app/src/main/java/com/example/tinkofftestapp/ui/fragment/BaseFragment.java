package com.example.tinkofftestapp.ui.fragment;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.example.tinkofftestapp.ui.activity.MainActivity;

public class BaseFragment extends MvpAppCompatFragment {
    protected void setToolbarTitle(String text) {
        ((MainActivity) getActivity()).setToolbarTitle(text);
    }
}
