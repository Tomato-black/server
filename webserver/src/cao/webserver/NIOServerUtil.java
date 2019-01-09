package cao.webserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class NIOServerUtil extends Thread{
      private int id = 10001;
      private static HashMap<String,String> map= new HashMap<>();
  	static {
  		map.put("html", "text/html;charset=utf-8");
  		map.put("tif", "image/tiff");
  		map.put("jpg", "image/jpeg");
  		map.put("js", "application/x-javascript");
  		map.put("png", "image/png");
  	}
	@Override
	public void run() {
		ServerSocketChannel serverSocketChannel = null;
		try {
			serverSocketChannel = ServerSocketChannel.open();
			ServerSocket accept = serverSocketChannel.socket();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(8087);
			accept.bind(inetSocketAddress);
			serverSocketChannel.configureBlocking(false);
			Selector select = Selector.open();
			serverSocketChannel.register(select, SelectionKey.OP_ACCEPT);
			linsen(select);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	private void linsen(Selector select) {
		
			try {
				while(true) {
					System.out.println("****************");
				select.select();
				Set<SelectionKey> selectedKeys = select.selectedKeys();
				Iterator<SelectionKey> it = selectedKeys.iterator();
				  while(it.hasNext()) {
					  SelectionKey next = it.next();
					  it.remove();
					  if(next.isAcceptable()) {
						 SocketChannel channel = ((ServerSocketChannel)next.channel()).accept();
						 channel.configureBlocking(false);
						 channel.register(next.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(2048));
					  }else if(next.isReadable()) {
						 SocketChannel channel = (SocketChannel)next.channel();
						 ByteBuffer allocate = ByteBuffer.allocate(2048);
						 channel.read(allocate);
						 String string = new String(allocate.array());
						 String[] split = string.split(" ");
						 String url = split[1];
						 String path = "D:\\work\\vs code work\\html";
							String pathURL = path + url;
							File file = new File(pathURL);
							if(!file.exists()) {
								continue;
							}
							String substring = url.substring(url.lastIndexOf(".")+1, url.length());
							String str = "HTTP/1.1 200 OK\n"+
									     "Content-Type:"+map.get(substring)+"\n"+
							             "Content-Length:"+file.length()+"\n\n";
							FileInputStream fileInputStream = new FileInputStream(file);
							byte[] buf = new byte[fileInputStream.available()];
							byte[] bytes = str.getBytes();
							
							fileInputStream.read(buf);
							ByteBuffer wrap = ByteBuffer.wrap(bytes);
							ByteBuffer wrap2 = ByteBuffer.wrap(buf);
							channel.write(wrap);
							channel.write(wrap2);
							channel.close();
					  }else if(next.isWritable()) {
						  
					  }
				  }
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
}
