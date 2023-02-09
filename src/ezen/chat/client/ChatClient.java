package ezen.chat.client;

import java.awt.Choice;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * TCP/IP 기반의 ChatClient
 * @author 김기정
 * @Date   2023. 2. 6.
 */
public class ChatClient {
	
	private static final String SERVER_IP = "localhost";
	private static final int SERVER_PORT = 7777;
	
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	
	private String nickName;
	
	private ChatPanel chatPanel;
	
	public ChatClient(ChatPanel chatPanel) {
		this.chatPanel = chatPanel;
	}
	
	// 서버 연결
	public void connectServer() throws IOException {
		socket = new Socket(SERVER_IP, SERVER_PORT);
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
	}
	
	// 서버 연결 종료
	public void disConnectServer() throws IOException {
		socket.close();
	}
	
	// 메시지 전송
	public void sendMessage(String message) throws IOException {
		out.writeUTF(message);
		out.flush();
	}
	
	// 메시지 수신
	public void receiveMessage() {
		// 서버로부터 전송되는 메시지를 실시간 수신하기 위해 스레드 생성 및 시작
		Thread thread = new Thread() {
			public void run() {
				try {
					while (true) {
						String serverMessage = in.readUTF();
						//"CONNECT★방그리"
						String[] tokens = serverMessage.split("★");
						String messageType = tokens[0];
						String senderNickName = tokens[1];
						switch (messageType) {
							case "CONNECT":
								// ※※※※※ [????]님이 대화에 참여하셨습니다 ※※※※※
								chatPanel.appendMessage("※※※※※ ["+senderNickName+"]님이 대화에 참여하셨습니다 ※※※※※");
								break;
							// 사용자 목록
								
							case "USER_LIST":
								String userList = tokens[2];
								// 목록을 List에 추가
//								chatPanel.setList(senderNickName);							
								chatPanel.choice.removeAll();
								chatPanel.setList(userList);
								chatPanel.addChoice(userList);
								break;
								
							// 채팅 메시지
							case "CHAT_MESSAGE":
								String chatMessage = tokens[2];
								// [대화명] : 메시지
								if((chatPanel.choice.getSelectedItem()).equals("전체")) {
									chatPanel.appendMessage("["+senderNickName+"] : " + chatMessage);	
								} 
								break;
								// 연결 종료 메시지
								
							case "DIS_CONNECT":
								// ※※※※※ [????]님이 퇴장하였습니다 ※※※※※
								chatPanel.appendMessage("※※※※※ ["+senderNickName+"]님이 퇴장하였습니다 ※※※※※");
								break;
						}
						
					}
				} catch (IOException e) {} 
				finally {
					//System.out.println("[서버]와 연결 종료함...");
				}
			}
		};
		thread.start();
	}
}








