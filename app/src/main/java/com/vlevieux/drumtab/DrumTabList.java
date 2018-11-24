package com.vlevieux.drumtab;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

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

        final DrumTab drumTab = drumTabs.get(position);
        TextView name = v.findViewById(R.id.main_tv_name);
        TextView info = v.findViewById(R.id.main_tv_more_info);

        final ProgressBar progressBar = v.findViewById(R.id.main_pb_download);

        final ImageButton imageButton = v.findViewById(R.id.main_ib_favorite);


        name.setText(drumTab.getArtistName() + " - " + drumTab.getSongName());
        info.setText("Rating?");


        Log.d("XMLCharAt", drumTab.toString());
        Log.d("XMLCharAT", "" + drumTab.getXml().charAt(0));

        if(drumTab.getXml().charAt(0) == '/'){
            //Set the button's appearance
            imageButton.setSelected(imageButton.isSelected());
        }
        if(drumTab.getXml().charAt(0) != 'h'){
            //Set the button's appearance
            imageButton.setSelected(!imageButton.isSelected());
        }


        imageButton.setOnClickListener(new VideoView.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Set the button's appearance
                imageButton.setSelected(!imageButton.isSelected());


                SqlHelper db = new SqlHelper(context);

                DownloadTask downloadTaskXML = new DownloadTask(context, ".xml",
                        drumTab.getDrumTabId(), progressBar);

                DownloadTask downloadTaskTAB = new DownloadTask(context, ".tab",
                        drumTab.getDrumTabId(), progressBar);

                if (imageButton.isSelected()) {

                    progressBar.setVisibility(View.VISIBLE);
                    // execute this when the downloader must be fired

                    downloadTaskXML.execute(drumTab.getXml());
                    downloadTaskTAB.execute(drumTab.getTab());

                    db.addDrumTab(new DrumTab(drumTab.getArtistName(), drumTab.getSongName(),
                            "/storage/emulated/0/drumTabs/drumTab" + drumTab.getDrumTabId() + ".xml",
                            "/storage/emulated/0/drumTabs/drumTab" + drumTab.getDrumTabId() + ".tab"));

                } else {

                    //Delete files
                    File fileXML = new File("/storage/emulated/0/drumTabs/drumTab" + drumTab.getDrumTabId() + ".xml");
                    fileXML.delete();
                    File fileTAB = new File("/storage/emulated/0/drumTabs/drumTab" + drumTab.getDrumTabId() + ".tab");
                    fileTAB.delete();

                    Toast.makeText(context, "Deleted from favorite", Toast.LENGTH_LONG).show();

                    Log.d("Button", db.getAllDrumTab().toString());

                    db.deleteDrumTab(drumTab);

                    Log.d("Button", db.getAllDrumTab().toString());
                }


            }
        });
        return v;
    }


    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock wakeLock;
        private String extension;
        private String drumTabId;
        private ProgressBar pB;

        public DownloadTask(Context context, String extension, String drumTabId, ProgressBar pB) {
            this.context = context;
            this.extension= extension;
            this.drumTabId = drumTabId;
            this.pB = pB;
        }

        @Override
        protected String doInBackground(String... sUrl) {

            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            URL url;

            try {
                url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }


                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                String folder_main = "drumTabs";

                File f = new File(Environment.getExternalStorageDirectory(), folder_main);
                if (!f.exists()) {
                    f.mkdirs();
                }
                input = connection.getInputStream();
                output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/"
                        + folder_main + "/drumTab" + drumTabId + extension);


                Log.d("PathTest", Environment.getExternalStorageDirectory() + "/" + folder_main + "/test01" + extension);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            wakeLock.acquire();

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
        }

        @Override
        protected void onPostExecute(String result) {
            wakeLock.release();
            pB.setVisibility(View.INVISIBLE);
            if (result != null)
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context, "Added to favorite", Toast.LENGTH_SHORT).show();
        }
    }
}

