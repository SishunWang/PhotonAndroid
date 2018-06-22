package model;

//import static com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.XmlToken.Uri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Apachehttp {

    private static final String USER_AGENT = "Mozilla/5.0";

    private static String GET_URL;

    private static String POST_URL;

    private static String access_token;

    private static final String access_token_file = "access_token.txt";

    private static HttpPost httpPost;
    
    public ArrayList<Device> getDevices() throws IOException, JSONException{
        ArrayList<Device> devices = new ArrayList<>();
        GET_URL = "https://api.particle.io/v1/devices?access_token=" + access_token;
                
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(GET_URL);
        httpGet.addHeader("User-Agent", USER_AGENT);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        
        System.out.println("GET Response Status:: "+ httpResponse.getStatusLine().getStatusCode());

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                           httpResponse.getEntity().getContent()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
        }
        reader.close();

        // print result
        
        httpClient.close();
        JSONArray jsArray = new JSONArray(response.toString());
        for(int i = 0; i < jsArray.length();i++){
            JSONObject myResponse =  new JSONObject(jsArray.get(i).toString());
//            System.out.println(jsArray.get(i));
            String id = myResponse.getString("id");
            String name = myResponse.getString("name");
//            Boolean last_app = myResponse.getBoolean("last_app");
//            String last_ip_address = myResponse.getString("last_ip_address");
//            String last_heard = myResponse.getString("last_heard");
//            int product_id = myResponse.getInt("product_id");
//            boolean connected = myResponse.getBoolean("connected");
//            String system_firmware_version = myResponse.getString("system_firmware_version");    
//            Device device = new Device(id, name, last_app, last_ip_address, last_heard, product_id, connected, system_firmware_version);
            Device device = new Device(id, name);
            devices.add(device);
        }
        return devices;
    }
    public String getVar(String id, String variable) throws Exception{
        ArrayList<Device> devices = new ArrayList<>();
        GET_URL = "https://api.particle.io/v1/devices/"+ id + "/" + variable + "?access_token=" + access_token;
                
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(GET_URL);
        httpGet.addHeader("User-Agent", USER_AGENT);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        
        System.out.println("GET Response Status:: "+ httpResponse.getStatusLine().getStatusCode());

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                           httpResponse.getEntity().getContent()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
        }
        reader.close();

        // print result
        
        httpClient.close();
        JSONObject myResponse =  new JSONObject(response.toString());
        
        return Integer.toString(myResponse.getInt("result"));
    }


//        private static HashMap<String,String> param = new HashMap<>();

    public Apachehttp(HashMap<String, String> request) throws IOException, UnsupportedEncodingException, JSONException {

        setupOAuth(request);

    }

    private static void setupOAuth(HashMap<String, String> request) throws UnsupportedEncodingException, IOException, JSONException{
        boolean found = true;
        try{
            File file = new File(access_token_file);
            Scanner scan = new Scanner(file);
            HashMap<String,String> temp = new HashMap<>();
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                temp.put(line.split(":")[0], line.split(":")[1]);

            }
            if(!temp.get("username").equals(request.get("username"))) found = false;
            else access_token = temp.get("access_token");
            GET_URL  = "https://api.particle.io/v1/devices/2b0025000b47363330353437/analogvalue?access_token=" + access_token;
//                System.out.println(access_token);
            if(sendGET("varible","analogvalue") == null) found = false;
        }catch(Exception e){
            found = false;
        }
        if (found == false){
            System.out.println("Either access token not exist or invalid");
            POST_URL = "https://api.particle.io/oauth/token/";
            JSONObject myResponse = new JSONObject(sendPOST(request));
            access_token = myResponse.getString("access_token");
            File file = new File(access_token_file);
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write("username:" + request.get("username"));
            writer.write("\n");
            writer.write("access_token:" + access_token);
            writer.close();
        }
//            System.out.println(access_token);
    }

    private static String sendGET(String type, String name) throws IOException {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(GET_URL);
            httpGet.addHeader("User-Agent", USER_AGENT);
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

            int code = httpResponse.getStatusLine().getStatusCode();
            System.out.println("GET Response Status:: "
                            + code);
            if (code == 400 ||code == 401 ) return null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                            httpResponse.getEntity().getContent()));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
            }
            reader.close();

            // print result
//            System.out.println(response.toString());
            httpClient.close();
            return response.toString();
    }



    private static String sendPOST(HashMap<String, String> request) throws IOException {

            List<NameValuePair> urlParameters = hashToList(request);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(POST_URL);
            httpPost.addHeader("User-Agent", USER_AGENT);

            HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);
            httpPost.setEntity(postParams);

            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            System.out.println("POST Response Status:: "
                            + httpResponse.getStatusLine().getStatusCode());

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                            httpResponse.getEntity().getContent()));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
            }
            reader.close();

//                 JSONObject myResponse = new JSONObject(response.toString());

            // print result
//		System.out.println(response.toString());

            httpClient.close();
            return response.toString();
    }


    private static List<NameValuePair> hashToList(HashMap<String, String> request) {
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        for(String key:request.keySet()){            
            urlParameters.add(new BasicNameValuePair(key, request.get(key)));
        }
        return urlParameters;
    }
}