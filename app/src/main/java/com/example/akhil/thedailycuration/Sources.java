package com.example.akhil.thedailycuration;

/**
 * Created by akhil on 12/25/17.
 */

public enum Sources {
    CNN("www.cnn.com", "span", "headline-text");

    private String link;
    private String html_header;
    private String html_header_class;

    private Sources(String link, String html_header, String html_header_class){}

    public String getLink() {
        return link;
    }

    public String getHtml_header() {
        return html_header;
    }

    public String getHtml_header_class() {
        return html_header_class;
    }
}
