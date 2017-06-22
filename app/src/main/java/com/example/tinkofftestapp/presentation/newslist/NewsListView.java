package com.example.tinkofftestapp.presentation.newslist;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.tinkofftestapp.data.model.News;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface NewsListView extends MvpView {

    void setLoading(boolean isLoading);

    void setRefreshing(boolean isRefreshing);

    void setItems(List<News> newsList);
}
