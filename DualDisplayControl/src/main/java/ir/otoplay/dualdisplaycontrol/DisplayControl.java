package ir.otoplay.dualdisplaycontrol;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DisplayControl {
    public final int SUCCESS=0;
    public final int ERROR=1;
    public final int EMPTY_WALLET=2;
    private Context context;
    private String root= Environment.getExternalStorageDirectory().getPath()+"/Lcd";
    public DisplayControl(Context context) {
        this.context = context;
    }
    public void setQrCode(String text){
        Send("QrCode",text);
    }
    public void setOtherBitmap(Bitmap bitmap){
        new SavePicture(bitmap,"other").execute();
    }
    public void setHeaderText(String text){
        Send("Header",text);
    }
    public void setFooterText(String text){
        Send("Footer",text);
    }
    public void setStatus(String Title,String Desc,int Status){
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("Status",Status);
            jsonObject.put("Desc",Desc);
            jsonObject.put("Title",Title);
            Send("Status",jsonObject.toString());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    private class SavePicture extends AsyncTask<Void, Void, String> {
        Bitmap bitmap;
        String Name;

        public SavePicture(Bitmap bitmap, String name) {
            this.bitmap = bitmap;
            Name = name;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... voids) {
            storeImage(bitmap,Name);
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            if(Name.equals("qrcode")){
                Send("QrCode","Change");
            }
            else  if(Name.equals("other")){
                Send("Other","Change");
            }

        }
    }
    private void storeImage(Bitmap image,String name) {
        try {
            File bg =new File(root+"/"+name+".png");
            FileOutputStream fos = new FileOutputStream(bg);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (Exception e) {
            Log.i("Error",e.getMessage());
        }
    }
    private void Send(String Command,String Value){
        Intent intent = new Intent();
        intent.setAction("ServiceManager.Message");
        intent.putExtra("Target","DualScreen");
        intent.putExtra("Command",Command);
        intent.putExtra("Value",Value);
        context.sendBroadcast(intent);
    }
}
