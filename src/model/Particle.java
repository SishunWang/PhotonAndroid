package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL; 
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;



public class Particle {
    
    private String token;
    private static HttpURLConnection con;
    
    public Particle(String username, String password) throws Exception {
        String url = "https://api.particle.io/oauth/token";
        String urlParameters = "particle=particle&grant_type=password&username=hc.eng@outlook.com&password=Qinyou413";
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }

            StringBuilder content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            System.out.println(content.toString());

        } finally {
            
            con.disconnect();
        }
        
//        String url = "https://api.particle.io/oauth/token \\ -u particle:particle \\ -d grant_type=password \\ -d \"username=hc.eng@outlook.com\" \\ -d \"password=Qinyou413\"";
//        URL obj = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//        con.setRequestMethod("POST");
//        con.setRequestProperty("User-Agent", "Mozilla/5.0");
//        int responseCode = con.getResponseCode();
////        System.out.println("\nSending 'GET' request to URL : " + url);
//        System.out.println("Response Code : " + responseCode);
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        StringBuffer response = new StringBuffer();
//        while ((inputLine = in.readLine()) != null) {
//           response.append(inputLine);
//        }
//        in.close();
    }
	   
    public static void call_me() throws Exception {
         String url = "https://api.particle.io/v1/devices/2b0025000b47363330353437/analogvalue?access_token=d9d95c247a72abef388620412e330fd1ff8cf203";
    //     String url = "https://api.particle.io/oauth/token \\ -u particle:particle \\ -d grant_type=password \\ -d \"username=hc.eng@outlook.com\" \\ -d \"password=Qinyou413\"";
         URL obj = new URL(url);
         HttpURLConnection con = (HttpURLConnection) obj.openConnection();
         // optional default is GET
         con.setRequestMethod("GET");
         //add request header
         con.setRequestProperty("User-Agent", "Mozilla/5.0");
         int responseCode = con.getResponseCode();
         System.out.println("\nSending 'GET' request to URL : " + url);
         System.out.println("Response Code : " + responseCode);
         BufferedReader in = new BufferedReader(
                 new InputStreamReader(con.getInputStream()));
         String inputLine;
         StringBuffer response = new StringBuffer();
         while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
         }
         in.close();
         //print in String
    //     System.out.println(response.toString());
         //Read JSON response and print
         JSONObject myResponse = new JSONObject(response.toString());
         System.out.println("result after Reading JSON Response");
         System.out.println("name- "+myResponse.getString("name"));
         System.out.println("result- "+myResponse.getInt("result"));

       }
}