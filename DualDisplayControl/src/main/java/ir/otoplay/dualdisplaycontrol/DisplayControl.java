package ir.otoplay.dualdisplaycontrol;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DisplayControl {
    private Context context;
    private String root= Environment.getExternalStorageDirectory().getPath()+"/Lcd";
    public DisplayControl(Context context) {
        this.context = context;
    }
    public void setBitmap(Bitmap bitmap){
        new SavePicture(bitmap).execute();
    }
    private class SavePicture extends AsyncTask<Void, Void, String> {
        Bitmap bitmap;

        public SavePicture(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... voids) {
            storeImage(bitmap);
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            Send();
        }
    }
    private void storeImage(Bitmap image) {
        try {
            File bg =new File(root+"/background.png");
            FileOutputStream fos = new FileOutputStream(bg);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (Exception e) {
            Log.i("Error",e.getMessage());
        }
    }
    private void Send(){
        Intent intent = new Intent();
        intent.setAction("ServiceManager.Message");
        intent.putExtra("Target","LCD");
        intent.putExtra("Command","Picture");
        intent.putExtra("Value","Change");
        context.sendBroadcast(intent);
    }
}
