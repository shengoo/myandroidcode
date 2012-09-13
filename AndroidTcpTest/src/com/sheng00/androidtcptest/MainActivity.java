package com.sheng00.androidtcptest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	EditText outText;
	Button cbtn;
	Button sbtn;
	String ip;
	String port;
	String content;

	Socket socket;
	InputStream is;
	OutputStream os;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		outText = (EditText) findViewById(R.id.outputtext);
		cbtn = (Button) findViewById(R.id.button1);
		cbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				ip = ((EditText) findViewById(R.id.editText1)).getText()
						.toString();
				port = ((EditText) findViewById(R.id.editText2)).getText()
						.toString();
				content = ((EditText) findViewById(R.id.editText3)).getText()
						.toString();
				connect(ip, port);
			}
		});
		sbtn = (Button) findViewById(R.id.button2);
		sbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				ip = ((EditText) findViewById(R.id.editText1)).getText()
						.toString();
				port = ((EditText) findViewById(R.id.editText2)).getText()
						.toString();
				content = ((EditText) findViewById(R.id.editText3)).getText()
						.toString();
				send(content);
			}
		});
		// con2();

	}

	private void connect(String ip, String port) {
		if (ip == null || ip.length() == 0 || port == null
				|| port.length() == 0) {
			output("ip == null || ip.length()==0 || port == null || port.length() == 0");
			return;
		}
		try {
			socket = new Socket(ip, Integer.parseInt(port));
			output("connected.");
			sbtn.setEnabled(true);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			output(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			output(e.getLocalizedMessage());
		}

	}

	private void send(String content) {
		if (socket == null || socket.isClosed()) {
			output("socket == null || socket.isClosed()");
			return;
		}
		if (!socket.isConnected()) {
			output("!socket.isConnected()");
			return;
		}
		try {
			is = socket.getInputStream();
			os = socket.getOutputStream();

	        DataOutputStream dos = new DataOutputStream(os);  
	        BufferedReader brNet = new BufferedReader(new InputStreamReader(is));  
	        dos.writeBytes(content + System.getProperty("line.separator"));  
			output("Send:" + content);
			String result = brNet.readLine();
			output("Receive:" + result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void output(String string) {
		outText.getText().append(
				System.getProperty("line.separator") + getTimeNow() + string);
	}

	public String getTimeNow() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:");
		String str = dateFormat.format(new Date());
		return str;
	}

	public void con1() {
		try {
			Socket s = new Socket("192.168.0.254", 8080);
			// if(args.length < 2)
			// {
			// System.out.println("Usage:java TcpClient ServerIP ServerPort");
			// return;
			// }
			// 建立Socket
			// Socket s=new
			// Socket(InetAddress.getByName(args[0]),Integer.parseInt(args[1]));
			InputStream ips = s.getInputStream();
			OutputStream ops = s.getOutputStream();

			BufferedReader brKey = new BufferedReader(new InputStreamReader(
					System.in));// 键盘输入
			DataOutputStream dos = new DataOutputStream(ops);
			BufferedReader brNet = new BufferedReader(
					new InputStreamReader(ips));

			while (true) {
				String strWord = brKey.readLine();
				dos.writeBytes("hi" + System.getProperty("line.separator"));
				if (strWord.equalsIgnoreCase("quit"))
					break;
				else
					System.out.println(brNet.readLine());
			}
			dos.close();
			brNet.close();
			brKey.close();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void con2() {
		try {
			Socket s = new Socket("192.168.87.1", 8080);
			// outgoing stream redirect to socket
			OutputStream out = s.getOutputStream();
			// 注意第二个参数据为true将会自动flush，否则需要需要手动操作out.flush()
			PrintWriter output = new PrintWriter(out, true);
			output.println("Hello IdeasAndroid!");
			BufferedReader input = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			// read line(s)
			String message = input.readLine();
			Log.d("Tcp Demo", "message From Server:" + message);
			s.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
