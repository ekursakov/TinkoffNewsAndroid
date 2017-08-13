package com.example.tinkofftestapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextSwitcher;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.tinkofftestapp.App;
import com.example.tinkofftestapp.R;
import com.example.tinkofftestapp.presentation.main.MainPresenter;
import com.example.tinkofftestapp.presentation.main.MainView;
import com.example.tinkofftestapp.ui.fragment.newsdetail.NewsDetailFragment;
import com.example.tinkofftestapp.ui.fragment.newslist.NewsListFragment;
import com.example.tinkofftestapp.ui.navigation.Screens;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @Inject
    NavigatorHolder navigatorHolder;

    @BindView(R.id.ivToolbarNavigation)
    ImageView toolbarNavigationImageView;

    @BindView(R.id.tsToolbarTitle)
    TextSwitcher toolbarTitleTextSwitcher;

    @InjectPresenter
    MainPresenter presenter;

    private Navigator navigator = new SupportAppNavigator(this, R.id.container) {
        @Override
        protected Intent createActivityIntent(String screenKey, Object data) {
            return null;
        }

        @Override
        protected Fragment createFragment(String screenKey, Object data) {
            switch (screenKey) {
                case Screens.NEWS_LIST:
                    return new NewsListFragment();
                case Screens.NEWS_CONTENT: {
                    NewsDetailFragment fragment = new NewsDetailFragment();

                    Bundle args = new Bundle();
                    args.putString(NewsDetailFragment.ARG_NEWS_ID, (String) data);
                    fragment.setArguments(args);

                    return fragment;
                }
                default:
                    throw new IllegalStateException("Navigating to unknown screen: " + screenKey);
            }
        }

        @Override
        protected void setupFragmentTransactionAnimation(
                Command command,
                Fragment currentFragment,
                Fragment nextFragment,
                FragmentTransaction fragmentTransaction
        ) {
            if (command instanceof Forward) {
                fragmentTransaction.setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right);
            }
        }
    };

    private FragmentManager.OnBackStackChangedListener onBackStackChangedListener
            = this::updateToolbarBackButton;


    @ProvidePresenter
    MainPresenter providePresenter() {
        return App.getAppComponent().mainPresenterProvider().get();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        updateToolbarBackButton();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();

        navigatorHolder.setNavigator(navigator);

        getSupportFragmentManager().addOnBackStackChangedListener(onBackStackChangedListener);
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        getSupportFragmentManager().removeOnBackStackChangedListener(onBackStackChangedListener);

        super.onPause();
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);

        toolbarTitleTextSwitcher.setText(title);
    }

    @OnClick(R.id.ivToolbarNavigation)
    void onToolbarNavigationClick() {
        onBackPressed();
    }

    private void updateToolbarBackButton() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            toolbarNavigationImageView.setVisibility(View.VISIBLE);
        } else {
            toolbarNavigationImageView.setVisibility(View.GONE);
        }
    }
}
