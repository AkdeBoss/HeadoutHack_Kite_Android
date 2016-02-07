package kite.ak.com.teamace.kite.helpers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import kite.ak.com.teamace.kite.R;
import kite.ak.com.teamace.kite.RecomendationActivity;
import kite.ak.com.teamace.kite.adapters.Place;
import kite.ak.com.teamace.kite.imgurmodel.ImageResponse;
import kite.ak.com.teamace.kite.utils.HttpConnectionUtil;


/**
 * Created by AKiniyalocts on 1/15/15.
 * <p/>
 * This class is just created to help with notifications, definitely not necessary.
 */
public class NotificationHelper {
    public final static String TAG = NotificationHelper.class.getSimpleName();

    private WeakReference<Context> mContext;


    public NotificationHelper(Context context) {
        this.mContext = new WeakReference<>(context);
    }

    public void createUploadingNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext.get());
        mBuilder.setSmallIcon(android.R.drawable.ic_menu_upload);
        mBuilder.setContentTitle(mContext.get().getString(R.string.notification_progress));


        mBuilder.setColor(mContext.get().getResources().getColor(R.color.primary));

        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) mContext.get().getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(mContext.get().getString(R.string.app_name).hashCode(), mBuilder.build());

    }
    private int resultCode;
    public void createUploadedNotification(final ImageResponse response) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext.get());
        mBuilder.setSmallIcon(android.R.drawable.ic_menu_gallery);
        mBuilder.setContentTitle(mContext.get().getString(R.string.notifaction_success));

        mBuilder.setContentText(response.data.link);

        mBuilder.setColor(mContext.get().getResources().getColor(R.color.primary));


        Intent resultIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(response.data.link));
        PendingIntent intent = PendingIntent.getActivity(mContext.get(), 0, resultIntent, 0);
        mBuilder.setContentIntent(intent);
        mBuilder.setAutoCancel(true);

        Intent shareIntent = new Intent(Intent.ACTION_SEND, Uri.parse(response.data.link));
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, response.data.link);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        new AsyncTask<String, Void, String>() {

            int responseCode;

            @Override
            protected String doInBackground(String... params) {
                try {
                    HttpConnectionUtil con = new HttpConnectionUtil("http://192.168.1.217/newsfeed/assets/face.php?url="+response.data.link, HttpConnectionUtil.REQUEST_METHOD_GET) {
                        @Override
                        public void getRequestHeaders(HttpURLConnection connection) {
                            connection.setRequestProperty("url", response.data.link);
                        }

                        @Override
                        public byte[] getRequestBody() {
                            return null;
                        }
                    };
                    con.execute();
                    String line;
                    StringBuilder result = new StringBuilder();
                    responseCode = con.getResponseCode();
                                        if (responseCode > 199 && responseCode < 300) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getResponse()));
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                        reader.close();
                                            System.out.println(result.toString());
                        return result.toString();
                    } else {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getError()));
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                        reader.close();
                        return result.toString();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;


            }

            @Override
            protected void onPostExecute(String s) {

                if (responseCode == 200) {
                    try {
                        JSONObject jsonObj = new JSONObject(s);
                        System.out.print(jsonObj.toString());
                        JSONArray results = jsonObj.getJSONArray("face");
                        JSONObject attribute= results.getJSONObject(0);
                        String smiling = attribute.getString("attribute");
                        JSONObject attributes= new JSONObject(smiling);
                        String age= attributes.getString("age");
                        String smile=attributes.getString("smiling");
                        JSONObject smileJson= new JSONObject(smile);
                        double smileVal= smileJson.getDouble("value");
                        JSONObject ageJson= new JSONObject(age);
                        String value= ageJson.getString("value");
                       // String Value=smiling.getString("value");
                        System.out.println("\n------\n age val"+value+"-----------\n smile value:"+smileVal+"\n\n");

                        fetchRecomendations(smileVal);


                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                } else {

                }

            }
        }.execute("", "");
        PendingIntent pIntent = PendingIntent.getActivity(mContext.get(), 0, shareIntent, 0);
        mBuilder.addAction(new NotificationCompat.Action(R.drawable.abc_ic_menu_share_mtrl_alpha,
                mContext.get().getString(R.string.notification_share_link), pIntent));

        NotificationManager mNotificationManager =
                (NotificationManager) mContext.get().getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(mContext.get().getString(R.string.app_name).hashCode(), mBuilder.build());
    }

    public void createFailedUploadNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext.get());
        mBuilder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        mBuilder.setContentTitle(mContext.get().getString(R.string.notification_fail));


        mBuilder.setColor(mContext.get().getResources().getColor(R.color.primary));

        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) mContext.get().getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(mContext.get().getString(R.string.app_name).hashCode(), mBuilder.build());
    }
     String url="";
    public void  fetchRecomendations(double val){

        if(val > 0 && val <21){
            url="http://192.168.1.217/newsfeed/assets/trips1.php?lat=12.927923&lng=77.627108";
        }
        else if (val >= 21 && val < 40){
            url="http://192.168.1.217/newsfeed/assets/trips2.php?lat=12.927923&lng=77.627108";
        }
        else if(val >= 40 && val <60){
            url="http://192.168.1.217/newsfeed/assets/trips3.php?lat=12.927923&lng=77.627108";
        }
        else if(val >= 60 && val <80){
            url="http://192.168.1.217/newsfeed/assets/trips4.php?lat=12.927923&lng=77.627108";
        }
        else if(val >=80){
            url="http://192.168.1.217/newsfeed/assets/trips5.php?lat=12.927923&lng=77.627108";
        }
        new AsyncTask<String, Void, String>() {

            int responseCode;

            @Override
            protected String doInBackground(String... params) {
                try {
                    System.out.println("\n\nurl="+url);
                    HttpConnectionUtil con = new HttpConnectionUtil(url, HttpConnectionUtil.REQUEST_METHOD_GET) {
                        @Override
                        public void getRequestHeaders(HttpURLConnection connection) {
                            connection.setRequestProperty("url", url);
                        }

                        @Override
                        public byte[] getRequestBody() {
                            return null;
                        }
                    };
                    con.execute();
                    String line;
                    StringBuilder result = new StringBuilder();
                    responseCode = con.getResponseCode();
                    if (responseCode > 199 && responseCode < 300) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getResponse()));
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                        reader.close();
                        System.out.println(result.toString());
                        return result.toString();
                    } else {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getError()));
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                        reader.close();
                        return result.toString();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;


            }

            @Override
            protected void onPostExecute(String s) {

                if (responseCode == 200) {
                    try {
                       JSONObject jsonObj = new JSONObject(s);
                        mContext.get().startActivity(new Intent(mContext.get(), RecomendationActivity.class));
                        System.out.println("\n \n \n DATA" + s + "\n");
                        JSONArray results = jsonObj.getJSONArray("results");
                        ArrayList<Place> places= new ArrayList<>();
                        for(int i= 0; i< results.length();i++){
                           JSONObject geoData= results.getJSONObject(i);
                            Place p = new Place();
                            p.nameOfPlace= geoData.getString("name");
                            p.maxHeight= 500;
                            p.maxWidth = 300;
                            JSONArray photoArray= jsonObj.getJSONArray("photos");
                            JSONObject j= photoArray.getJSONObject(0);
                            JSONArray ja=j.getJSONArray("html_attributions");
                            p.photoRefference= ja.getString(0);

                        }
//                        JSONObject attribute= results.getJSONObject(0);
//                        String smiling = attribute.getString("attribute");
//                        JSONObject attributes= new JSONObject(smiling);
//                        String age= attributes.getString("age");
//                        String smile=attributes.getString("smiling");
//                        JSONObject smileJson= new JSONObject(smile);
//                        double smileVal= smileJson.getDouble("value");
//                        JSONObject ageJson= new JSONObject(age);
//                        String value= ageJson.getString("value");
//                        // String Value=smiling.getString("value");
//                        System.out.println("\n------\n age val"+value+"-----------\n smile value:"+smileVal+"\n\n");


                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                }

            }
        }.execute("", "");

    }
}
