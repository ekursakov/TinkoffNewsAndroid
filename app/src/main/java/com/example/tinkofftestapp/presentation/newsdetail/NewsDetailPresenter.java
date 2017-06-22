package com.example.tinkofftestapp.presentation.newsdetail;

import com.arellomobile.mvp.InjectViewState;
import com.example.tinkofftestapp.data.ErrorMessageResolver;
import com.example.tinkofftestapp.data.NewsRepository;
import com.example.tinkofftestapp.presentation.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class NewsDetailPresenter extends BasePresenter<NewsDetailView> {
    private final NewsRepository newsRepository;
    private final Router router;
    private final ErrorMessageResolver errorMessageResolver;

    private String newsId;

    @Inject
    public NewsDetailPresenter(NewsRepository newsRepository,
                               Router router,
                               ErrorMessageResolver errorMessageResolver) {
        this.newsRepository = newsRepository;
        this.router = router;
        this.errorMessageResolver = errorMessageResolver;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    @Override
    protected void onFirstViewAttach() {
        loadData(false);
    }

    public void retry() {
        getViewState().hideFatalError();
        loadData(false);
    }

    public void onSwipeToRefresh() {
        loadData(true);
    }

    private void loadData(final boolean swipeToRefresh) {
        if (swipeToRefresh) {
            getViewState().setRefreshing(true);
        } else {
            getViewState().setLoading(true);
        }

        destroyOnDispose(newsRepository.getNewsContent(newsId, swipeToRefresh)
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    if (swipeToRefresh) {
                        getViewState().setRefreshing(false);
                    } else {
                        getViewState().setLoading(false);
                    }
                })
                .subscribe(newsContent -> {
                    getViewState().setContent(newsContent);
                }, e -> {
                    Timber.w(e);
                    String errorMessage = errorMessageResolver.getErrorMessage(e);
                    if (swipeToRefresh) {
                        router.showSystemMessage(errorMessage);
                    } else {
                        getViewState().setContent(null);
                        getViewState().showFatalError(errorMessage);
                    }
                }));
    }

}
