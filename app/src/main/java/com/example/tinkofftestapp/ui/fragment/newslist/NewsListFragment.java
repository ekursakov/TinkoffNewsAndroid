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
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.tinkofftestapp.App;
import com.example.tinkofftestapp.R;
import com.example.tinkofftestapp.data.model.NewsTitle;
import com.example.tinkofftestapp.presentation.newslist.NewsListPresenter;
import com.example.tinkofftestapp.presentation.newslist.NewsListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NewsListFragment extends MvpAppCompatFragment
        implements NewsListView, NewsListAdapter.ItemInteractionListener {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.rvNewsList)
    RecyclerView newsRecyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.errorView)
    View errorView;

    @BindView(R.id.tvErrorMessage)
    TextView fatalErrorTextView;

    @InjectPresenter
    NewsListPresenter presenter;

    private NewsListAdapter adapter = new NewsListAdapter(this);

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

    @Override
    public void onStart() {
        super.onStart();

        getActivity().setTitle(getString(R.string.app_name));
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
    public void setItems(List<NewsTitle> items) {
        if (items != null) {
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            adapter.set(items);
        } else {
            swipeRefreshLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showFatalError(String message) {
        errorView.setVisibility(View.VISIBLE);

        fatalErrorTextView.setText(message);
    }

    @Override
    public void hideFatalError() {
        errorView.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnRetry)
    void onRetryClick() {
        presenter.retry();
    }

    @Override
    public void onNewsClick(NewsTitle newsTitle) {
        presenter.onNewsClick(newsTitle);
    }
}

