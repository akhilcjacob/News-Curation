package com.example.akhil.thedailycuration.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.akhil.thedailycuration.R;
import com.prof.rssparser.Article;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akhil on 12/24/17.
 */
public class adapter extends RecyclerView.Adapter<adapter.LinkHolder> {
    private List<Article> linkList;
    private Context context;

    public adapter(Context context) {
        linkList = new ArrayList<>();
        this.context = context;
    }

    public boolean contains(int location) {
        return location < linkList.size();
    }

    @Override
    public int getItemCount() {
        return linkList.size();
    }

    public void removeItem(int position) {
        linkList.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(Article item) {
        linkList.add(item);
    }

    public void addAllItems(List<Article> items) {
        linkList.addAll(items);
    }

    @Override
    public void onBindViewHolder(final LinkHolder linkHolder, final int i) {
        final Article article = linkList.get(i);
        linkHolder.vLink.setText(article.getLink());
        linkHolder.vTitle.setText(article.getTitle());
        linkHolder.vBrief_text.setText(grabIntro(article.getDescription()));
        linkHolder.vArticle = article;
        linkHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getLink()));
                context.startActivity(browserIntent);
                browserIntent.putExtra("color", R.color.colorPrimary);
            }
        });
    }
    private String grabIntro(String content){
        return Jsoup.parse(content).text();
    }
    @Override
    public LinkHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card, viewGroup, false);
        return new LinkHolder(itemView);
    }

    public class LinkHolder extends RecyclerView.ViewHolder {
        protected TextView vLink;
        protected TextView vTitle;
        protected TextView vBrief_text;
        protected Article vArticle;

        public LinkHolder(View v) {
            super(v);
            vLink = v.findViewById(R.id.link);
            vTitle = v.findViewById(R.id.title);
            vBrief_text = v.findViewById(R.id.link_brief);
        }
    }
}
