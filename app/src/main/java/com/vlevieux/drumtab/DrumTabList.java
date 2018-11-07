package com.vlevieux.drumtab;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * @author thomas THEBAUD, 11/6/2018
 *
 */
public class DrumTabList extends ArrayAdapter<DrumTab> {

    private int resourceLayout;
    private Context context;
    private List<DrumTab> drumTabs;

    public DrumTabList(Context context, int resource, List<DrumTab> drumTabs) {
        super(context, resource, drumTabs);
        this.resourceLayout = resource;
        this.context = context;
        this.drumTabs = drumTabs;
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

        DrumTab drumTab = drumTabs.get(position);
        TextView name = v.findViewById(R.id.main_tv_name);
        TextView info = v.findViewById(R.id.main_tv_more_info);

        name.setText(drumTab.getArtistName() + " - " + drumTab.getSongName());
        info.setText("Rating?");
        return v;
    }
}

