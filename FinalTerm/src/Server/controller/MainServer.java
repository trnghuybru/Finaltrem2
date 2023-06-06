package Server.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import Client.model.Account;
import Client.view.processData;
import Client.view.sendRequest;
import DAOs.AccountDB;
import DAOs.Condb;

public class MainServer {
	public final static int port = 3000;
	
	public MainServer() {
		// TODO Auto-generated constructor stub
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Server running");
			
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("Connected " + socket.getInetAddress());
				DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
				
				DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
				
				Thread thread = new Thread (new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							while (true) {
								String dataFromUser = dataInputStream.readUTF();
								System.out.println(dataFromUser);
								JsonObject jobj = new Gson().fromJson(dataFromUser, JsonObject.class);
								String method = jobj.get("method").toString();
//								System.out.println(method);
								String checkMethod = removeNgoac(method);
//								System.out.println(checkMethod);
							switch (checkMethod) {
								case "DAOsRegister":
									String username = 	removeNgoac(jobj.getAsJsonObject("data").get("username").toString());
									String password = 	removeNgoac(jobj.getAsJsonObject("data").get("password").toString());
									String	phone = 	removeNgoac(jobj.getAsJsonObject("data").get("phone").toString());
									String email = 	removeNgoac(jobj.getAsJsonObject("data").get("email").toString());
									
									Account account = new Account(username, password,phone,email);
//									String string= account.toString();
//									System.out.println(string);
									Register(account, dataOutputStream);
								break;
								
							default:
								break;
							}
							}
							
							
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				});
				thread.start();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void Register(Account acc, DataOutputStream sendToUser) {
		Account account = new AccountDB().selectById(acc.getPassword());
		if (account!=null) {
			String dataToUser = new processData().processRegisterError("error", "ExistedPhone");
			try {
				sendToUser.writeUTF(dataToUser);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			String dataToUser = new processData().processRegisterError("success", "Register Successfull");
			try {
				sendToUser.writeUTF(dataToUser);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	// Xu ly ngoac kep
	public static String removeNgoac(String input) {
		return input.replaceAll("^\"|\"$", "");
	}
	public static void main(String[] args) {
		new MainServer();
	}
}
