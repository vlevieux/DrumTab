package com.vlevieux.drumtab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author thomas THEBAUD, 11/6/2018
 *
 */
public class DrumTabList extends ArrayAdapter<DrumTab> {

    private int resourceLayout;
    private Context context;
    private ArrayList<DrumTab> drumTabs;
    private ProgressBar progressBarHorizontal;

    public DrumTabList(Context context, int resource, ArrayList<DrumTab> drumTabs, ProgressBar progressBarHorizontal) {
        super(context, resource, drumTabs);
        this.resourceLayout = resource;
        this.context = context;
        this.drumTabs = drumTabs;
        this.progressBarHorizontal = progressBarHorizontal;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            v = vi.inflate(resourceLayout, null);
        }

        TextView name = v.findViewById(R.id.main_tv_name);
        TextView info = v.findViewById(R.id.main_tv_more_info);

        Boolean inFavorite = false;
        DrumTab drumTab = drumTabs.get(position);
        ProgressBar progressBar = v.findViewById(R.id.main_pb_download);
        ImageButton imageButton = v.findViewById(R.id.main_ib_favorite);

        name.setText(drumTab.getArtistName() + " - " + drumTab.getSongName());
        info.setText("Rating?");

        if(drumTab.getIsFavorite().equals("true")){
            imageButton.setBackgroundResource(R.drawable.button_selected);
            inFavorite = true;
        } else {
            imageButton.setBackgroundResource(R.drawable.button_normal);
        }

        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TabActivity.class);
                intent.putExtra("PATH", "test");
                context.startActivity(intent);
            }
        });

        imageButton.setOnClickListener(new FavoriteOnClickListener(context, progressBar,
                progressBarHorizontal, drumTab, imageButton, inFavorite){
            @Override
            public void onClick(View v) {
                super.onClick(v);
            }
        });
        return v;
    }
}

