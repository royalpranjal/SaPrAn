package com.pranjal.customerapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pranjal.customerapp.Activity.Home;
import com.pranjal.customerapp.Model.HorizontalItemObject;
import com.pranjal.customerapp.R;
import com.pranjal.customerapp.Response.TalkToChatBoxResponse;
import com.pranjal.customerapp.Service.TalkToChatBoxService;
import com.pranjal.customerapp.Utils.Constants;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by royalpranjal on 1/28/2017.
 */

public class HorizontalRecylerViewAdapter extends RecyclerView.Adapter<HorizontalRecyclerViewHolder>{

    private Context context;
    private List<HorizontalItemObject> itemList;

    public HorizontalRecylerViewAdapter(Home context, List<HorizontalItemObject> rowListItem) {
        this.itemList = rowListItem;
        this.context = context;
    }

    @Override
    public HorizontalRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewOfItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list_item, null);
        HorizontalRecyclerViewHolder rvh = new HorizontalRecyclerViewHolder(viewOfItem);
        return rvh;
    }

    @Override
    public void onBindViewHolder(final HorizontalRecyclerViewHolder holder, int position) {
        holder.textView.setText(itemList.get(position).getName());
        holder.imageView.setImageResource(itemList.get(position).getPhoto());

        final String url = itemList.get(position).getUrl();

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FinestWebView.Builder(context).show(url);
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FinestWebView.Builder(context).show(url);
            }
                      });

     }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
