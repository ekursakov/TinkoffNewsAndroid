package com.example.tinkofftestapp.presentation.main;

import com.arellomobile.mvp.InjectViewState;
import com.example.tinkofftestapp.presentation.BasePresenter;
import com.example.tinkofftestapp.ui.navigation.Screens;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {
    private final Router router;

    @Inject
    public MainPresenter(Router router) {
        this.router = router;
    }

    @Override
    protected void onFirstViewAttach() {
        router.newRootScreen(Screens.NEWS_LIST);
    }
}

