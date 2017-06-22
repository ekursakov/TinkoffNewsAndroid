package com.example.tinkofftestapp.ui.fragment.newslist;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tinkofftestapp.R;
import com.example.tinkofftestapp.data.model.NewsTitle;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    private static final SimpleDateFormat NEWS_DATE_FORMAT
            = new SimpleDateFormat("d MMM, HH:mm", Locale.getDefault());

    private final ItemInteractionListener listener;
    private List<NewsTitle> items = Collections.emptyList();

    NewsListAdapter(ItemInteractionListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void set(List<NewsTitle> newItems) {
        if (newItems != null) {
            items = newItems;
        } else {
            items = Collections.emptyList();
        }

        notifyDataSetChanged();
    }

    interface ItemInteractionListener {
        void onNewsClick(NewsTitle newsTitle);
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

        void bind(NewsTitle news, ItemInteractionListener listener) {
            dateTextView.setText(NEWS_DATE_FORMAT.format(news.getPublicationDate()));
            titleTextView.setText(Html.fromHtml(news.getText()));

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onNewsClick(news);
                }
            });
        }
    }
}
