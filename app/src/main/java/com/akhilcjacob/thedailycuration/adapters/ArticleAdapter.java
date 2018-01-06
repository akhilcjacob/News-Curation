package com.akhilcjacob.thedailycuration.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akhilcjacob.thedailycuration.R;
import com.prof.rssparser.Article;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

//import com.prof.rssparser.Article;

/**
 * Created by akhil on 12/24/17.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.LinkHolder>  {
    private List<Article> linkList;
    private Context context;

    public ArticleAdapter(Context context) {
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

    public List<Article> getArticles() {
        return linkList;
    }

    public void removeItem(int position) {
        linkList.remove(position);
        notifyItemRemoved(position);
    }

    public void addAllItems(List<Article> items) {
        linkList.addAll(items);
    }

    @Override
    public void onBindViewHolder(final LinkHolder linkHolder, final int i) {
        final Article article = linkList.get(i);
        if (article.getTitle() == "") return;
        linkHolder.vLink.setText(article.getLink());
        linkHolder.vTitle.setText(article.getTitle());
        linkHolder.vBrief_text.setText(grabIntro(article.getDescription()));
        linkHolder.vArticle = article;
        linkHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse((article.getLink())));
                context.startActivity(browserIntent);
                browserIntent.putExtra("color", R.color.colorPrimary);
            }
        });
        //CHeck if rss provided an image, show otherwise hide component
        if (article.getImage() != null && !article.getImage().isEmpty()) {
            System.out.println(linkHolder.imageView);
            Picasso.with(context).load(article.getImage()).noPlaceholder().into(linkHolder.imageView);
        } else {
            linkHolder.imageView.setVisibility(View.GONE);
        }

    }

    /**
     * Take and convert html strings into readable content
     *
     * @param content html string
     * @return human readable content
     */
    private String grabIntro(String content) {
        return Jsoup.parse(content).text();
    }

    @Override
    public LinkHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_card, viewGroup, false);
        return new LinkHolder(itemView);
    }

    public void sortList() {
        Collections.sort(linkList, new Comparator<Article>() {
            @Override
            public int compare(Article article, Article t1) {
                if (article.getPubDate() == null || t1.getPubDate() == null) {
                    return 0;
                }
                if (article.getPubDate().before(t1.getPubDate()))
                    return 1;
                else if (article.getPubDate().after(t1.getPubDate()))
                    return -1;
                else
                    return 0;
            }
        });
    }

    public void removeDuplicates() {
        ArrayList<Article> result = new ArrayList<>();
        // Record encountered Strings in HashSet.
        HashSet<String> set = new HashSet<>();

        // Loop over argument list.
        for (Article item : linkList) {

            // If String is not in set, add it to the list and the set.
            if (!set.contains(item.getTitle())) {
                result.add(item);
                set.add(item.getTitle());
            }
        }
        linkList = result;
    }


    public void setContext(Context context) {
        this.context = context;
    }

    class LinkHolder extends RecyclerView.ViewHolder {
        TextView vLink;
        TextView vTitle;
        TextView vBrief_text;
        Article vArticle;
        ImageView imageView;

        LinkHolder(View v) {
            super(v);
            vLink = v.findViewById(R.id.link);
            vTitle = v.findViewById(R.id.title);
            vBrief_text = v.findViewById(R.id.link_brief);
            imageView = v.findViewById(R.id.imageView);
        }
    }
}
