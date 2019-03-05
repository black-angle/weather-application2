package mg.studio.weatherappdesign;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.view.Gravity;

import java.io.ByteArrayOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.String;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DownloadUpdate().execute();
    }

    public void btnClick(View view) {
        new DownloadUpdate().execute();
    }



   // public void JSONAn
    private class DownloadUpdate extends AsyncTask<String, Void, String> {

        String data=null;
        String data2=null;
        String data3=null;
        String data4=null;
        String data5=null;
        String wea1=null;
        String wea2=null;
        String wea3=null;
        String wea4=null;
        String wea5=null;
        double tempt=0;

        int tem1,tem2;
       @Override
        protected String doInBackground(String... strings) {
            //String stringUrl = "https://mpianatra.com/Courses/info.txt";
            //String stringUrl = "https://mpianatra.com/Courses/forecast.json";
            String stringUrl = "http://api.openweathermap.org/data/2.5/forecast?q=Chongqing,cn&mode=json&APPID=aa3d744dc145ef9d350be4a80b16ecab";
            HttpURLConnection urlConnection = null;
            //BufferedReader reader;

            boolean f=true;

            try {
                tem1=0;
                tem2=0;
                URL url = new URL(stringUrl);

                // Create the request to get the information from the server, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                //String ss="100";
                //return ss;

                JsonReader reader=new JsonReader(new InputStreamReader(inputStream,"UTF-8"));
                //reader.beginArray();
                reader.beginObject();
                int res=10;
                String ss=null;
                //return ss;

                while(reader.hasNext()){
                    //res++;
                    String name=reader.nextName();
                    //ss=name;
                    if(name.equals("list")) {
                        //reader.beginArray();
                       // while(reader.hasNext()) {
                         //   name=reader.nextName();
                           // if (name.equals("cod")) {
                        reader.beginArray();
                        //reader.beginObject();
                        while(reader.hasNext()){
                            reader.beginObject();
                            res++;
                            while(reader.hasNext()){
                                name=reader.nextName();


                                if(name.equals("main")){
                                    reader.beginObject();
                                    while(reader.hasNext()){
                                        ss=reader.nextName();
                                        if(ss.equals("temp")&&f){
                                            f=false;
                                            tempt=reader.nextDouble();
                                            tempt-=273.15;
                                        }
                                        else
                                            reader.skipValue();
                                    }
                                    reader.endObject();
                                }
                                else if(name.equals("weather")){
                                    reader.beginArray();
                                    while (reader.hasNext()){
                                        reader.beginObject();
                                        while(reader.hasNext()){
                                            ss=reader.nextName();
                                            if(ss.equals("main")){
                                                if(tem2==0){
                                                    wea1=reader.nextString();
                                                }
                                                else if(tem2==8){
                                                    wea2=reader.nextString();
                                                }
                                                else if(tem2==16){
                                                    wea3=reader.nextString();
                                                }
                                                else if(tem2==24){
                                                    wea4=reader.nextString();
                                                }
                                                else if(tem2==32){
                                                    wea5=reader.nextString();
                                                }
                                                else{
                                                    reader.skipValue();
                                                }
                                                tem2++;
                                            }
                                            else {
                                                reader.skipValue();
                                            }
                                        }
                                        reader.endObject();
                                    }
                                    reader.endArray();
                                }
                                else if(name.equals("dt_txt")) {
                                    ss = reader.nextString();
                                    if(tem1==0)
                                        data = ss.substring(0, 10);
                                    else if(tem1==8)
                                        data2=ss.substring(5,10);
                                    else if(tem1==16)
                                        data3=ss.substring(5,10);
                                    else if(tem1==24)
                                        data4=ss.substring(5,10);
                                    else if(tem1==32)
                                        data5=ss.substring(5,10);
                                    tem1++;
                                        //res++;
                                    //f=false;
                                }
                                else
                                reader.skipValue();
                            }
                            reader.endObject();
                            /*String st=reader.nextName();
                            if(st.equals("dt")){
                                res=123;
                            }
                            else {
                                reader.skipValue();
                            }*/
                            //reader.skipValue();
                        }
                        //reader.endObject();
                        reader.endArray();;
                        //reader.skipValue();
                                //res = 123;
                                //reader.skipValue();
                           // } else
                             //   reader.skipValue();
                       // }
                        //reader.endArray();
                    }
                    else
                    {
                        reader.skipValue();
                    }
                }
                reader.endObject();
                //String fin=null;
                //data=ss.substring(0,10);
                //reader.endArray();
                //return String.valueOf(res);
                //return String.valueOf(ss.length());
                // cnt++;
                return "Y";
/*
                byte[] result;
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buffer))!=-1) {
                        baos.write(buffer,0,len);
                    }
                    inputStream.close();
                    baos.close();
                    result = baos.toByteArray();

                } catch (IOException e) {
                    e.printStackTrace();
                    String errorStr = "Failure to obtain data.";
                    return errorStr;
                }
                String res = new String(result);

                //object = ;
                try {
                    JSONObject object = new JSONObject(res);
                    JSONObject ObjectInfo = object.optJSONObject("weatherinfo");
                    String temp = ObjectInfo.optString("speed");
                    return temp;
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                /**
                 * 在你获取的string这个JSON对象中，提取你所需要的信息。
                 */

                 //((TextView) findViewById(R.id.temperature_of_the_day)).setText(temperature);

/*
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Mainly needed for debugging
                    Log.d("TAG", line);
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                //The temperature
                return buffer.toString();
*/
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            //Update the temperature displayed
            /*if(result.equals("F")){
                Toast toastCenter = Toast.makeText(getApplicationContext(),"It's been updated before.",Toast.LENGTH_LONG);

                toastCenter.setGravity(Gravity.CENTER,0,0);

                toastCenter.show();

                return;
            }*/

            ((TextView) findViewById(R.id.tv_date)).setText(data);
            ((TextView) findViewById(R.id.day2)).setText(data2);
            ((TextView) findViewById(R.id.day3)).setText(data3);
            ((TextView) findViewById(R.id.day4)).setText(data4);
            ((TextView) findViewById(R.id.day5)).setText(data5);
            //((TextView) findViewById(R.id.temperature_of_the_day)).setText(wea2);
            //((TextView) findViewById(R.id.temperature_of_the_day)).setText(temperature);
            ((TextView) findViewById(R.id.temperature_of_the_day)).setText(String.valueOf(tempt));

            if(wea1.equals("Clear")){
                ((ImageView)findViewById(R.id.img_weather_condition)).setImageDrawable(getResources().getDrawable(R.drawable.sunny_small));
            }
            else if(wea1.equals("Clouds")){
                ((ImageView)findViewById(R.id.img_weather_condition)).setImageDrawable(getResources().getDrawable(R.drawable.partly_sunny_small));
            }
            else if(wea1.equals("Rain")){
                ((ImageView)findViewById(R.id.img_weather_condition)).setImageDrawable(getResources().getDrawable(R.drawable.rainy_small));
            }
            else{
                ((ImageView)findViewById(R.id.img_weather_condition)).setImageDrawable(getResources().getDrawable(R.drawable.windy_small));
            }



            if(wea2.equals("Clear")){
                ((ImageView)findViewById(R.id.iday2)).setImageDrawable(getResources().getDrawable(R.drawable.sunny_small));
            }
            else if(wea2.equals("Clouds")){
                ((ImageView)findViewById(R.id.iday2)).setImageDrawable(getResources().getDrawable(R.drawable.partly_sunny_small));
            }
            else if(wea2.equals("Rain")){
                ((ImageView)findViewById(R.id.iday2)).setImageDrawable(getResources().getDrawable(R.drawable.rainy_small));
            }
            else{
                ((ImageView)findViewById(R.id.iday2)).setImageDrawable(getResources().getDrawable(R.drawable.windy_small));
            }

            if(wea3.equals("Clear")){
                ((ImageView)findViewById(R.id.iday3)).setImageDrawable(getResources().getDrawable(R.drawable.sunny_small));
            }
            else if(wea3.equals("Clouds")){
                ((ImageView)findViewById(R.id.iday3)).setImageDrawable(getResources().getDrawable(R.drawable.partly_sunny_small));
            }
            else if(wea3.equals("Rain")){
                ((ImageView)findViewById(R.id.iday3)).setImageDrawable(getResources().getDrawable(R.drawable.rainy_small));
            }
            else{
                ((ImageView)findViewById(R.id.iday3)).setImageDrawable(getResources().getDrawable(R.drawable.windy_small));
            }

            if(wea4.equals("Clear")){
                ((ImageView)findViewById(R.id.iday4)).setImageDrawable(getResources().getDrawable(R.drawable.sunny_small));
            }
            else if(wea4.equals("Clouds")){
                ((ImageView)findViewById(R.id.iday4)).setImageDrawable(getResources().getDrawable(R.drawable.partly_sunny_small));
            }
            else if(wea4.equals("Rain")){
                ((ImageView)findViewById(R.id.iday4)).setImageDrawable(getResources().getDrawable(R.drawable.rainy_small));
            }
            else{
                ((ImageView)findViewById(R.id.iday4)).setImageDrawable(getResources().getDrawable(R.drawable.windy_small));
            }

            if(wea5.equals("Clear")){
                ((ImageView)findViewById(R.id.iday5)).setImageDrawable(getResources().getDrawable(R.drawable.sunny_small));
            }
            else if(wea5.equals("Clouds")){
                ((ImageView)findViewById(R.id.iday5)).setImageDrawable(getResources().getDrawable(R.drawable.partly_sunny_small));
            }
            else if(wea5.equals("Rain")){
                ((ImageView)findViewById(R.id.iday5)).setImageDrawable(getResources().getDrawable(R.drawable.rainy_small));
            }
            else{
                ((ImageView)findViewById(R.id.iday5)).setImageDrawable(getResources().getDrawable(R.drawable.windy_small));
            }

            Toast toastCenter = Toast.makeText(getApplicationContext(),"Updated successful.",Toast.LENGTH_LONG);

            toastCenter.setGravity(Gravity.CENTER,0,0);

            toastCenter.show();
            //((ImageView)findViewById(R.id.img_weather_condition)).setImageDrawable(getResources().getDrawable(R.drawable.sunny_small));
        }
    }
}
