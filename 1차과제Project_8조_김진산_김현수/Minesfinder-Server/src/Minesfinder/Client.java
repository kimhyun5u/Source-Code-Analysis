package Minesfinder;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	Socket socket;

	public Client(Socket socket) {
		this.socket = socket;
		receive();
	}

	// �޽��� ���� �޴� �޼ҵ�
	private void receive() {
		Runnable thread = new Runnable() {
			@Override
			public void run() {
				try {
					while(true) {
						InputStream in = socket.getInputStream();
						byte[] buffer = new byte[512];
						DataInputStream dis = new DataInputStream(in);
//						int length = in.read(buffer);
//						while(length == -1) throw new IOException();
						System.out.println("�޽��� ���� ����" + socket.getRemoteSocketAddress() + " : "+ Thread.currentThread().getName());						
//						String message = new String(buffer, 0, length, "UTF-8");
						String message = dis.readUTF();
						for(Client client : Server.clients) {
							client.send(message);
						}
					}
				}catch(Exception e) {
					try{
						System.out.println("�޽��� ���� ����" + socket.getRemoteSocketAddress()+ " : " + Thread.currentThread().getName());						
					}catch(Exception e2) {
						e2.printStackTrace();
					}
				}

			}
		};
		Server.threadPool.submit(thread);
	}

	// �޽��� ���� Ŭ����
	private void send(String message) {
		Runnable thread = new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("�޼��� �۽� ����"
							+ socket.getRemoteSocketAddress() + " : " + Thread.currentThread().getName());
					OutputStream out = socket.getOutputStream();
					byte[] buffer = message.getBytes("UTF-8");
					out.write(buffer);
					out.flush();
				}catch(Exception e) {
					try {
						System.out.println("�޽��� �۽� ����" + socket.getRemoteSocketAddress() + " : " + Thread.currentThread().getName());
						Server.clients.remove(Client.this);
						socket.close();
					}catch(Exception e2) {
						e2.printStackTrace();
					}
				}
				
			}
			
		};
		Server.threadPool.submit(thread);
	}
}
