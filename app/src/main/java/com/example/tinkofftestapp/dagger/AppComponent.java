package com.example.tinkofftestapp.dagger;


import com.example.tinkofftestapp.presentation.main.MainPresenter;
import com.example.tinkofftestapp.presentation.newslist.NewsListPresenter;
import com.example.tinkofftestapp.ui.activity.MainActivity;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NavigationModule.class, NetworkModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);

    Provider<MainPresenter> mainPresenterProvider();

    Provider<NewsListPresenter> newsListPresenterProvider();
}
