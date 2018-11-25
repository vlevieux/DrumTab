package com.vlevieux.drumtab;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FavoriteOnClickListener implements View.OnClickListener {

    private ProgressBar progressBar;
    private ProgressBar progressBarHorizontal;
    private DrumTab drumtab;
    private Context context;
    private ImageButton imagebutton;
    private Boolean inFavorite;

    public FavoriteOnClickListener(Context context, ProgressBar progressBar,
                                   ProgressBar progressBarHorizontal,
                                   DrumTab drumtab, ImageButton imageButton, Boolean inFavorite) {
        this.context = context;
        this.progressBar = progressBar;
        this.progressBarHorizontal = progressBarHorizontal;
        this.drumtab = drumtab;
        this.imagebutton = imageButton;
        this.inFavorite = inFavorite;
    }

    @Override
    public void onClick(View v) {


        SqlHelper db = new SqlHelper(this.context);
        DownloadTask downloadTaskXML = new DownloadTask(this.context, ".xml",
                this.drumtab.getSongName(), this.drumtab.getArtistName(), progressBar, progressBarHorizontal);

        DownloadTask downloadTaskTAB = new DownloadTask(context, ".tab",
                this.drumtab.getSongName(), this.drumtab.getArtistName(), progressBar, progressBarHorizontal);

        if (!inFavorite){
            if(db.getAllDrumTab().size() != 0) {
                boolean alreadyInFavorite = false;
                for (DrumTab dT : db.getAllDrumTab()) {
                    if (dT.getSongName().equals(this.drumtab.getSongName()) && dT.getArtistName().equals(this.drumtab.getArtistName())) {
                        alreadyInFavorite = true;
                        break;
                    }
                }
                if (!alreadyInFavorite) {

                    inFavorite = !inFavorite;
                    imagebutton.setBackgroundResource(R.drawable.button_selected);

                    progressBar.setVisibility(View.VISIBLE);
                    // execute this when the downloader must be fired

                    try {
                        downloadTaskXML.execute(this.drumtab.getXml());
                        downloadTaskTAB.execute(this.drumtab.getTab());
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }

                    db.addDrumTab(new DrumTab(this.drumtab.getArtistName(), this.drumtab.getSongName(),
                            "/storage/emulated/0/drumTabs/drumTab"
                                    + this.drumtab.getSongName()
                                    + this.drumtab.getArtistName()
                                    + ".xml",
                            "/storage/emulated/0/drumTabs/drumTab"
                                    + this.drumtab.getSongName()
                                    + this.drumtab.getArtistName()
                                    + ".tab", "true"));
                } else {
                    Toast.makeText(context, "Already in your favorites", Toast.LENGTH_SHORT).show();
                }
            } else {

                inFavorite = !inFavorite;
                imagebutton.setBackgroundResource(R.drawable.button_selected);

                progressBar.setVisibility(View.VISIBLE);
                // execute this when the downloader must be fired

                downloadTaskXML.execute(this.drumtab.getXml());
                downloadTaskTAB.execute(this.drumtab.getTab());

                db.addDrumTab(new DrumTab(this.drumtab.getArtistName(), this.drumtab.getSongName(),
                        "/storage/emulated/0/drumTabs/drumTab"
                                + this.drumtab.getSongName()
                                + this.drumtab.getArtistName()
                                + ".xml",
                        "/storage/emulated/0/drumTabs/drumTab"
                                + this.drumtab.getSongName()
                                + this.drumtab.getArtistName()
                                + ".tab", "true"));
                }
        } else {

            inFavorite = !inFavorite;
            imagebutton.setBackgroundResource(R.drawable.button_normal);

            //Delete files
            File fileXML = new File("/storage/emulated/0/drumTabs/drumTab"
                    + this.drumtab.getSongName()
                    + this.drumtab.getArtistName()
                    + ".xml");
            fileXML.delete();
            File fileTAB = new File("/storage/emulated/0/drumTabs/drumTab"
                    + this.drumtab.getSongName()
                    + this.drumtab.getArtistName()
                    + ".tab");
            fileTAB.delete();
            db.deleteDrumTab(this.drumtab);

            Toast.makeText(context, "Deleted from favorite", Toast.LENGTH_SHORT).show();
            }
    }

    private static class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock wakeLock;
        private String extension;
        private String songName;
        private String artistName;
        private ProgressBar pB;
        private ProgressBar progressBarHorizontal;

        public DownloadTask(Context context, String extension, String songName,
                            String artistName, ProgressBar pB, ProgressBar progressBarHorizontal) {
            this.context = context;
            this.extension= extension;
            this.songName = songName;
            this.artistName = artistName;
            this.pB = pB;
            this.progressBarHorizontal = progressBarHorizontal;
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

                // download the file in a folder drumTabs on the mobile internal storage
                String folder_main = "drumTabs";
                File f = new File(Environment.getExternalStorageDirectory(), folder_main);
                if (!f.exists()) {
                    f.mkdirs();
                }

                input = connection.getInputStream();
                output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/"
                        + folder_main + "/drumTab" + this.songName + this.artistName + this.extension);

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
            progressBarHorizontal.setVisibility(View.VISIBLE);
            progressBarHorizontal.setMax(100);
            progressBarHorizontal.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            wakeLock.release();
            pB.setVisibility(View.INVISIBLE);
            progressBarHorizontal.setVisibility((View.INVISIBLE));
            if (result != null)
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context, "Added to favorite", Toast.LENGTH_SHORT).show();
        }
    }
}
