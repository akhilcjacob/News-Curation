package com.akhilcjacob.thedailycuration.helpers;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.akhilcjacob.thedailycuration.Sources;
import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.util.ArrayList;
import java.util.List;

import static com.akhilcjacob.thedailycuration.RecyclerViewFragment.updateCards;



public class RSSParser extends AppCompatActivity {
    public RSSParser() {
    }

    public void parse(Sources source) {
        String url = source.getLink();

        Parser parser = new Parser();
        parser.execute(url);


        parser.onFinish(new Parser.OnTaskCompleted() {

            @Override
            public void onTaskCompleted(ArrayList<Article> list) {
                updateCards(list);
            }

            @Override
            public void onError() {

//                Snackbar.make("Replace with your own action", Snackbar.LENGTH_LONG)
//        .setAction("Action", null).show();
            }
        });
    }
}