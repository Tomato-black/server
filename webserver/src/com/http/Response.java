package com.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class Response {
	private OutputStream os;
	private String url;
	private static HashMap<String,String> map= new HashMap<>();
	static {
		map.put("html", "text/html;charset=utf-8");
		map.put("tif", "image/tiff");
		map.put("jpg", "image/jpeg");
		map.put("js", "application/x-javascript");
		map.put("png", "image/png");
	}
	public Response() {}
    public Response(OutputStream os,String url) {
    	this.os = os;
    	this.url = url;
    }
    
	public OutputStream getOs() {
		return os;
	}
	public void setOs(OutputStream os) {
		this.os = os;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void sendResource() {
		String path = "D:\\work\\vs code work\\html";
		String pathURL = path + url;
		File file = new File(pathURL);
		String substring = url.substring(url.lastIndexOf(".")+1, url.length());
		PrintStream out = new PrintStream(os);
				out.println("HTTP/1.1 200 OK");
				out.println("Content-Type:"+map.get(substring));
				out.println("Content-Length:"+file.length());
				out.println();
				FileInputStream fileInputStream = null;
				try {
					fileInputStream = new FileInputStream(file);
					byte[] buf = new byte[fileInputStream.available()];
					fileInputStream.read(buf);
					out.write(buf);
					out.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					try {
						fileInputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				}
}
