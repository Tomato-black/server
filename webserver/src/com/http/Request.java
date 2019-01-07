package com.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Request {

	private String url;
	
	private static HashMap<String,String> map =  new HashMap<>();
    private static Request request = null;	
    private Request() {}
    public Request(InputStream is) {
    	BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(is));
    	try {
			String readLine = inputStreamReader.readLine();
			String[] split = readLine.split(" ");
			url = split[1];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
		}
    }
    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
    
}
