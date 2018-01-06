package com.akhilcjacob.thedailycuration;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akhilcjacob.thedailycuration.adapters.ArticleAdapter;
import com.akhilcjacob.thedailycuration.helpers.RSSParser;
import com.prof.rssparser.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akhil on 1/2/18.
 */

public class RecyclerViewFragment extends Fragment {
    private static final String TAG = "RecyclerViewFragment";
    protected static ArticleAdapter articleAdapter;
    protected static SwipeRefreshLayout swipeRefresh;
    protected RecyclerView recyclerView;
    protected LinearLayoutManager layoutManager;

    public RecyclerViewFragment() {
    }

    public static void updateCards(List<Article> list) {
        articleAdapter.addAllItems(list);
        articleAdapter.removeDuplicates();
        articleAdapter.sortList();
        articleAdapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        rootView.setTag(TAG);
        //Add swipe to refresh to activity_card-view
        recyclerView = rootView.findViewById(R.id.cardList);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        articleAdapter = new ArticleAdapter(getActivity());
        recyclerView.setAdapter(articleAdapter);
        ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (articleAdapter.contains(viewHolder.getAdapterPosition())) {
                    articleAdapter.removeItem(viewHolder.getAdapterPosition());
                }
            }
        };
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);

        swipeRefresh = rootView.findViewById(R.id.swipeRefresh);
        //Add color to the refresh spinner
        swipeRefresh.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeRefresh.setProgressViewEndTarget(false, 200);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                parseSources();
            }
        });
        parseSources();
        return rootView;
    }

    public void parseSources() {
        swipeRefresh.setRefreshing(true);
        for (Sources s : Sources.values()) {
            new RSSParser().parse(s);
        }
    }

}
