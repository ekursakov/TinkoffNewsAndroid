package com.example.tinkofftestapp.ui.fragment.newsdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.Spanned;
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
import com.example.tinkofftestapp.data.model.NewsContent;
import com.example.tinkofftestapp.presentation.newsdetail.NewsDetailPresenter;
import com.example.tinkofftestapp.presentation.newsdetail.NewsDetailView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NewsDetailFragment extends MvpAppCompatFragment implements NewsDetailView {
    public static final String ARG_NEWS_ID = "ARG_NEWS_ID";

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.errorView)
    View errorView;

    @BindView(R.id.tvContent)
    TextView contentTextView;

    @BindView(R.id.tvErrorMessage)
    TextView fatalErrorTextView;

    @InjectPresenter
    NewsDetailPresenter presenter;

    @ProvidePresenter
    NewsDetailPresenter providePresenter() {
        NewsDetailPresenter presenter = App.getAppComponent().newsDetailPresenterProvider().get();
        presenter.setNewsId(getArguments().getString(ARG_NEWS_ID));
        return presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_detail, container, false);
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

        getActivity().setTitle(getString(R.string.title_news_detail));
    }

    private void initViews() {
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
    public void setContent(NewsContent newsContent) {
        if (newsContent != null) {
            swipeRefreshLayout.setVisibility(View.VISIBLE);

            String title = Html.fromHtml(newsContent.getTitle().getText()).toString();
            Spanned content = Html.fromHtml(newsContent.getContent());

            contentTextView.setText(content);
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
}

