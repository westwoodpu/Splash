package com.westwoodpu.splash.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.westwoodpu.splash.Models.Collection;
import com.westwoodpu.splash.Utils.SquareImage;
import com.westwoodpu.splash.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nxa16819 on 5/1/2018.
 */

public class CollectionsAdapter extends BaseAdapter {
    private List<Collection> collections;
    private Context context;
    public CollectionsAdapter(Context context, List<Collection> collections) {
        this.context = context;
        this.collections = collections;
    }


    @Override
    public int getCount() {
        return collections.size();
    }

    @Override
    public Object getItem(int i) {
        return collections.get(i);
    }

    @Override
    public long getItemId(int i) {
        return collections.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_collection, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ButterKnife.bind(this, view);
        Collection collection = collections.get(i);

        if (collection.getTitle() != null) {
            Log.d("Collection Name:", collection.getTitle());
            holder.title.setText(collection.getTitle());
        }

        holder.totalPhotos.setText(String.valueOf(collection.getTotalPhotos()) + "wallpaper");
        GlideApp.with(context)
                .load(collection.getCoverPhoto().getUrl().getRegular())
                .into(holder.collectionPhoto);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.item_collection_photo) SquareImage collectionPhoto;
        @BindView(R.id.item_collection_title)
        TextView title;
        @BindView(R.id.item_collection_total_photos) TextView totalPhotos;
        private ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
