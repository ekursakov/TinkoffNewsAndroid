package com.example.tinkofftestapp.presentation.newslist;

import com.arellomobile.mvp.InjectViewState;
import com.example.tinkofftestapp.data.NewsRepository;
import com.example.tinkofftestapp.presentation.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class NewsListPresenter extends BasePresenter<NewsListView> {
    private final NewsRepository newsRepository;

    @Inject
    public NewsListPresenter(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    protected void onFirstViewAttach() {
        getViewState().setLoading(true);
        newsRepository.getNewsList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsList -> {
                    getViewState().setItems(newsList);

                    getViewState().setLoading(false);
                }, e -> {
                    // TODO: error
                    getViewState().setLoading(false);
                });
    }

    public void onSwipeToRefresh() {
        // TODO
        getViewState().setRefreshing(false);
    }
}
