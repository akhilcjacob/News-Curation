package com.example.akhil.thedailycuration.helpers;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.akhil.thedailycuration.MainActivity;
import com.example.akhil.thedailycuration.Sources;
import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.util.ArrayList;

public class RSSParser extends AppCompatActivity{

    public void parse(Sources source) {
        System.out.println("HELLOS");
        String url = source.getLink();

        Parser parser = new Parser();
        parser.execute(url);
        parser.onFinish(new Parser.OnTaskCompleted() {

            @Override
            public void onTaskCompleted(ArrayList<Article> list) {
                MainActivity.updateCards(list);
                System.out.println("WORKING");
                for(Article article: list){
                    System.out.println(article.getTitle());
                }
            }

            @Override
            public void onError() {
                System.out.println("RAN INTO ERROR");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("Unable to load ", "articles");
                    }
                });
                //Snackbar.make("Replace with your own action", Snackbar.LENGTH_LONG)
                //.setAction("Action", null).show();
            }
        });
    }
}