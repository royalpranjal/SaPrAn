package com.pranjal.customerapp.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.pranjal.customerapp.Activity.FoodBot;
import com.pranjal.customerapp.Activity.Recipe;
import com.pranjal.customerapp.R;
import com.pranjal.customerapp.Response.FindByIngredientsResponse;
import com.pranjal.customerapp.Response.TalkToChatBoxResponse;
import com.pranjal.customerapp.Service.TalkToChatBoxService;
import com.pranjal.customerapp.Utils.Constants;
import com.squareup.picasso.Picasso;
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

public class FindItemsByIngredientsAdapter extends RecyclerView.Adapter<FindItemsByIngredientsHolder> {
    private Context context;
    private List<FindByIngredientsResponse> itemList;
    private String url = "google.com";

    public FindItemsByIngredientsAdapter(Recipe context, List<FindByIngredientsResponse> rowListItem) {
        this.itemList = rowListItem;
        this.context = context;
    }

    @Override
    public FindItemsByIngredientsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewOfItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, null);
        FindItemsByIngredientsHolder rvh = new FindItemsByIngredientsHolder(viewOfItem);
        return rvh;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(FindItemsByIngredientsHolder holder, final int position) {
        holder.textView.setText(itemList.get(position).getTitle());
        Picasso.with(context).load("" + itemList.get(position).getImage()).into(holder.imageView);

        String Title = itemList.get(position).getTitle();
        RestAdapter adapter = new RestAdapter
                .Builder()
                .setEndpoint(Constants.BASE_URL)
                .build();

        TalkToChatBoxService api = adapter.create(TalkToChatBoxService.class);

//        Toast.makeText(FoodBot.this, inputString, Toast.LENGTH_SHORT).show();

        int number = ThreadLocalRandom.current().nextInt(1, 10000 + 1);

        api.getSuggestions(number + "", Title, new Callback<TalkToChatBoxResponse>() {
            @Override
            public void success(TalkToChatBoxResponse talkToChatBoxResponse, Response response) {
                if(talkToChatBoxResponse.getMedia().size() != 0) {
                    url = talkToChatBoxResponse.getMedia().get(0).getLink();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

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

    public void clear() {
        itemList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<FindByIngredientsResponse> list) {
        itemList.addAll(list);
        notifyDataSetChanged();
    }
}


