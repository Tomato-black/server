package cao.webserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import com.http.Request;
import com.http.Response;

public class ServerUtil extends Thread{
    private static boolean shutdown = false;
    @Override
    public void run() {
		ServerSocket serverSocket = null;
		Socket accept = null;
		try {
//			,1,InetAddress.getByName("127.0.0.1")
			serverSocket = new ServerSocket(8088);
			 while(true) {
				accept = serverSocket.accept();
				InputStream is = accept.getInputStream();
				OutputStream outputStream = accept.getOutputStream();
				Request request = new Request(is);
				String url = request.getUrl();
				Response response = new Response(outputStream,url);
				response.sendResource();
//				SimpleDateFormat greenwichDate = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
				accept.shutdownInput();
				accept.shutdownOutput();
				accept.close();
               }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
