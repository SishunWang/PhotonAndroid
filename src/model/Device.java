/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Sishun Wang
 */
public class Device {
    public String id;
    public String name;
//    String last_app;
//    String last_ip_address;
//    String last_heard;
//    int product_id;
//    boolean connected;
//    String system_firmware_version;
    
    public Device(){
        
    }
    
    public Device(String id, String name){
        this.id = id;
        this.name = name;
    }
    
//    public Device(String id, String name, String last_app, String last_ip_address, String last_heard, int product_id, boolean connected, String system_firmware_version){
//        this.id = id;
//        this.name = name;
//        this.last_app = last_app;
//        this.last_ip_address = last_ip_address;
//        this.last_heard = last_heard;
//        this.product_id = product_id;
//        this.connected = connected;
//        this.system_firmware_version = system_firmware_version;
//    }
    
    public void print(){
        System.out.println("id: " + id + ", name: " + name);
    }
}
