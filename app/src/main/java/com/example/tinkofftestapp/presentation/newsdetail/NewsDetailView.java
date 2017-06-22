package com.example.tinkofftestapp.presentation.newsdetail;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.tinkofftestapp.data.model.NewsContent;
import com.example.tinkofftestapp.util.AddToEndSingleByTagStateStrategy;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface NewsDetailView extends MvpView {

    void setLoading(boolean isLoading);

    void setRefreshing(boolean isRefreshing);

    void setContent(NewsContent newsContent);

    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = "fatalError")
    void showFatalError(String message);

    @StateStrategyType(value = AddToEndSingleByTagStateStrategy.class, tag = "fatalError")
    void hideFatalError();
}
