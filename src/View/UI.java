/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
//import model.Particle;
import model.Apachehttp;
import model.Device;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
/**
 *
 * @author Sishun Wang
 */
public class UI {
    
    private static HashMap<String,String> request = new HashMap<>();
    private static String response; 
    private static ArrayList<Device> devices = new ArrayList<>();
    
    public UI() throws Exception{
        String username = "hc.eng@outlook.com";
        String password = "Qinyou413";
        
        request.clear();
        request.put("grant_type", "password");
        request.put("username", username);
        request.put("password", password);
        request.put("client_id", "d9d95c247a82abef388620412e330fd1ff8cf203");
        request.put("client_secret", "d9d95c287a72abef388620412e330fd1ff8cf203");
        
        Apachehttp apachehttp = new Apachehttp(request);
        devices = apachehttp.getDevices();
        System.out.println("Found your device: ");
        for(int i = 0; i < devices.size(); i++){
            System.out.print(i + ", ");
            devices.get(i).print();
        }
        while(true){
            int index = -1;
            System.out.println("Please choose a device by index number ");
            while(true){
                Scanner scanner = new Scanner(System.in);
                String deviceIndex = scanner.nextLine();
                index = Integer.parseInt(deviceIndex);
                if(index >= 0 && index < devices.size()) break;   
            }
            while(true)  System.out.println(apachehttp.getVar(devices.get(index).id, "analogvalue"));
        }
    }
    
    
    public static void main(String[] args) throws Exception {
        
        UI ui = new UI();
        
  
//        while(true){
//            response = apachehttp.sendGET("varible","analogvalue");
//        }
//        try {
//            Particle.call_me();
//           } catch (Exception e) {
//            e.printStackTrace();
//          }
        }
}
