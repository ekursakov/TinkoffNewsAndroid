package com.example.tinkofftestapp.presentation.newslist;

import com.arellomobile.mvp.InjectViewState;
import com.example.tinkofftestapp.data.ErrorMessageResolver;
import com.example.tinkofftestapp.data.NewsRepository;
import com.example.tinkofftestapp.data.model.News;
import com.example.tinkofftestapp.presentation.BasePresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class NewsListPresenter extends BasePresenter<NewsListView> {
    private static final Comparator<News> NEWS_COMPARATOR
            = (o1, o2) -> o1.getPublicationDate().compareTo(o2.getPublicationDate());

    private final NewsRepository newsRepository;
    private final Router router;
    private final ErrorMessageResolver errorMessageResolver;

    @Inject
    public NewsListPresenter(NewsRepository newsRepository,
                             Router router,
                             ErrorMessageResolver errorMessageResolver) {
        this.newsRepository = newsRepository;
        this.router = router;
        this.errorMessageResolver = errorMessageResolver;
    }

    @Override
    protected void onFirstViewAttach() {
        loadNews(false);
    }

    public void retry() {
        getViewState().hideFatalError();
        loadNews(false);
    }

    public void onSwipeToRefresh() {
        loadNews(true);
    }

    private void loadNews(final boolean swipeToRefresh) {
        if (swipeToRefresh) {
            getViewState().setRefreshing(true);
        } else {
            getViewState().setLoading(true);
        }

        destroyOnDispose(newsRepository.getNewsList(swipeToRefresh)
                .map(newsList -> {
                    List<News> sortedList = new ArrayList<>(newsList);
                    Collections.sort(sortedList, Collections.reverseOrder(NEWS_COMPARATOR));
                    return sortedList;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    if (swipeToRefresh) {
                        getViewState().setRefreshing(false);
                    } else {
                        getViewState().setLoading(false);
                    }
                })
                .subscribe(newsList -> {
                    getViewState().setItems(newsList);
                }, e -> {
                    Timber.w(e);
                    String errorMessage = errorMessageResolver.getErrorMessage(e);
                    if (swipeToRefresh) {
                        router.showSystemMessage(errorMessage);
                    } else {
                        getViewState().setItems(null);
                        getViewState().showFatalError(errorMessage);
                    }
                }));
    }

}
