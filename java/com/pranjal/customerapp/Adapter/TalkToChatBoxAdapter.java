package com.pranjal.customerapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pranjal.customerapp.Activity.FoodBot;
import com.pranjal.customerapp.R;
import com.pranjal.customerapp.Response.Media;
import com.squareup.picasso.Picasso;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;

/**
 * Created by royalpranjal on 1/28/2017.
 */

public class TalkToChatBoxAdapter extends RecyclerView.Adapter<TalkToChatBoxHolder>{
    private Context context;
    private List<Media> itemList;

    public TalkToChatBoxAdapter(FoodBot context, List<Media> rowListItem) {
        this.itemList = rowListItem;
        this.context = context;
    }

    @Override
    public TalkToChatBoxHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewOfItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, null);
        TalkToChatBoxHolder rvh = new TalkToChatBoxHolder(viewOfItem);
        return rvh;
    }

    @Override
    public void onBindViewHolder(TalkToChatBoxHolder holder, final int position) {
        holder.textView.setText(itemList.get(position).getTitle());
        Picasso.with(context).load("" + itemList.get(position).getImage()).into(holder.imageView);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(v.getContext(), Test2.class);
//
//                Toast.makeText(context, "Link : " + itemList.get(position).getLink(), Toast.LENGTH_SHORT).show();
//
//                i.putExtra(Constants.URL, itemList.get(position).getLink());
//                v.getContext().startActivity(i);
                new FinestWebView.Builder(context).show(itemList.get(position).getLink());
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(v.getContext(), Test2.class);
//
//                Toast.makeText(context, "Link : " + itemList.get(position).getLink(), Toast.LENGTH_SHORT).show();
//
//                i.putExtra(Constants.URL, itemList.get(position).getLink());
//                v.getContext().startActivity(i);
                new FinestWebView.Builder(context).show(itemList.get(position).getLink());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public void clear() {
        itemList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Media> list) {
        itemList.addAll(list);
        notifyDataSetChanged();
    }
}
