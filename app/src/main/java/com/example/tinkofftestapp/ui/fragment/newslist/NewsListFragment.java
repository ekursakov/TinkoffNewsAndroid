package com.example.tinkofftestapp.ui.fragment.newslist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.tinkofftestapp.App;
import com.example.tinkofftestapp.R;
import com.example.tinkofftestapp.data.model.News;
import com.example.tinkofftestapp.presentation.newslist.NewsListPresenter;
import com.example.tinkofftestapp.presentation.newslist.NewsListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsListFragment extends MvpAppCompatFragment implements NewsListView {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.rvNewsList)
    RecyclerView newsRecyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @InjectPresenter
    NewsListPresenter presenter;

    private NewsListAdapter adapter = new NewsListAdapter();

    @ProvidePresenter
    NewsListPresenter providePresenter() {
        return App.getAppComponent().newsListPresenterProvider().get();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        initViews();
    }

    private void initViews() {
        newsRecyclerView.setAdapter(adapter);
        newsRecyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.onSwipeToRefresh());
    }

    @Override
    public void setLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setRefreshing(boolean isRefreshing) {
        swipeRefreshLayout.setRefreshing(isRefreshing);
    }

    @Override
    public void setItems(List<News> items) {
        if (items != null) {
            adapter.set(items);
        }
    }
}

