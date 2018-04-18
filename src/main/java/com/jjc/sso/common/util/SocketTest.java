package com.jjc.sso.common.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketTest {
	public static boolean isLoclePortUsing(int port){  
        boolean flag = true;  
        try {  
            flag = isPortUsing("10.158.44.201", port);  
        } catch (Exception e) {  
        }  
        return flag;  
    }  
	
	public static boolean isPortUsing(String host,int port) throws UnknownHostException{  
        boolean flag = false;  
        InetAddress theAddress = InetAddress.getByName(host);  
        try {  
            Socket socket = new Socket(theAddress,port);  
            flag = true;  
        } catch (IOException e) {  
           e.printStackTrace();   
        }  
        return flag;  
    }
	
//	public static void main(String[] args) {
//		SocketTest t = new SocketTest();
//		boolean r = t.isLoclePortUsing(80);
//		if(r){
//			System.out.println("is use");
//		}else{
//			System.out.println("is not use");
//		}
//	}
	
	public static void main(String[] args) {
		for (int i = 63636; i < 63640; i++) {
			try {
				InetAddress localHost = InetAddress.getLocalHost();
				Socket socket = new Socket(localHost, i);
				System.out.println("本机已经使用了端口：" + i);
			} 
//			catchd (UnknownHostException e) {
//				e.printStackTrace();
//			}
			catch (IOException e) {
//				e.printStackTrace();
			}
		}
		System.out.println("执行完毕！");
	}
	
	
//	public static void main(String args[]) { 
//	    // 缩进4个空格 
//		String say = "hello"; 
//		// 运算符的左右必须有一个空格 
//		int flag = 0; 
//		// 关键词if与括号之间必须有一个空格，括号内f与左括号，1与右括号不需要空格 
//		if (flag == 0) { 
//			System.out.println(say); 
//		} 
//		// 左大括号前加空格且不换行；左大括号后换行 
//		if (flag == 1) { 
//		    System.out.println("world"); 
//		// 右大括号前换行，右大括号后有else，不用换行 
//		} else { 
//		    System.out.println("ok"); 
//		// 右大括号做为结束，必须换行
//		}
//	}
}
