package com.example.akhil.thedailycuration.models;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.akhil.thedailycuration.R;

import java.util.List;

/**
 * Created by akhil on 12/24/17.
 */
public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.ContactViewHolder> {

    private List<LinkInfo> contactList;

    public LinkAdapter(List<LinkInfo> contactList) {
        this.contactList = contactList;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }
    public void removeItem(int position){
        contactList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        LinkInfo ci = contactList.get(i);
        contactViewHolder.vLink.setText(ci.link);
        contactViewHolder.vTitle.setText(ci.title);
        contactViewHolder.vBrief_text.setText(ci.brief_text);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card, viewGroup, false);

        return new ContactViewHolder(itemView);
    }


    public class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView vLink;
        protected TextView vTitle;
        protected TextView vBrief_text;

        public ContactViewHolder(View v) {
            super(v);
            vLink = v.findViewById(R.id.link);
            vTitle = v.findViewById(R.id.title);
            vBrief_text = v.findViewById(R.id.link_brief);
        }
    }
}
