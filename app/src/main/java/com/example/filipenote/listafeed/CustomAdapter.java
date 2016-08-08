package com.example.filipenote.listafeed;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by FilipeNote on 06/08/2016.
 */
public class CustomAdapter extends BaseAdapter {

    List<FeedDados> listFeed;
    Context context;
    private static LayoutInflater inflater=null;

    public CustomAdapter(Activity activity, List<FeedDados> listFeed) {
        this.context = activity;
        this.listFeed = listFeed;
        this.inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listFeed.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class Holder {
        TextView title;
        TextView desc;
        ImageView img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();

        View rowView;
        rowView = inflater.inflate(R.layout.ow_layout, null);
        holder.desc = (TextView) rowView.findViewById(R.id.tvDesc);
        holder.title = (TextView) rowView.findViewById(R.id.tvTitulo);
        holder.img = (ImageView) rowView.findViewById(R.id.imageView);

        if(listFeed.get(position).getImgUrl()!="ImgPadrao") {
            Ion.with(holder.img)
                    .placeholder(R.drawable.images)
                    .error(R.drawable.images)
                    .load(listFeed.get(position).getImgUrl());
        }

        holder.title.setText(listFeed.get(position).getTitle());
        holder.desc.setText(listFeed.get(position).getDescricao());

        return rowView;
    }


}
