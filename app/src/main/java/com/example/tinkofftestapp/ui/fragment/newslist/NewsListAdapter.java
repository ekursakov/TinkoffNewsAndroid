package com.example.tinkofftestapp.ui.fragment.newslist;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tinkofftestapp.R;
import com.example.tinkofftestapp.data.model.News;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    private static final SimpleDateFormat NEWS_DATE_FORMAT
            = new SimpleDateFormat("d MMM, HH:mm", Locale.getDefault());

    private List<News> items = Collections.emptyList();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(layoutInflater.inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void set(List<News> newItems) {
        if (newItems != null) {
            items = newItems;
        } else {
            items = Collections.emptyList();
        }

        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvDate)
        TextView dateTextView;

        @BindView(R.id.tvTitle)
        TextView titleTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(News news) {
            dateTextView.setText(NEWS_DATE_FORMAT.format(news.getPublicationDate()));
            titleTextView.setText(Html.fromHtml(news.getText()));
        }
    }
}
