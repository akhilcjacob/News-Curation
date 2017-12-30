package com.example.akhil.thedailycuration;

/**
 * Created by akhil on 12/25/17.
 */

public enum Sources {
    CNN_TOP("http://rss.cnn.com/rss/cnn_topstories.rss",""),
    CNN_WORLD("http://rss.cnn.com/rss/cnn_world.rss", ""),
    PHONE_ARENA_REVIEWS("http://feeds.feedburner.com/PhoneArena-LatestNews", "");



    private String link;
    private String category;

    private Sources(String link, String category){
        this.link = link;
        this.category = category;
    }

    public String getLink() {
        return link;
    }

    public String getCategory(){return category;}
}
