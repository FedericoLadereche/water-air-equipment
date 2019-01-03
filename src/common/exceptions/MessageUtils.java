package common.exceptions;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import common.MessageFormatError;

public class MessageUtils {
	
	public static void sendMessage(Socket socket, String message) throws IOException {
		OutputStream stream = socket.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(stream);
		oos.writeUTF(message);
		oos.flush();
	}

	public static String getMessage(Socket socket) throws IOException, MessageFormatError {
		try {
			String msgRecivedFromClient = null;
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			return checkValidMessageFormat(msgRecivedFromClient = bufferedReader.readLine());
		}catch (Exception e) {
			return null;
		}
	}
	
	private static String checkValidMessageFormat(String msgRecivedFromClient) throws MessageFormatError {
		String msgRecivedContainer[] = msgRecivedFromClient.split(" ");
//		if(!msgRecivedContainer[0].equalsIgnoreCase("#") || msgRecivedContainer.length == 1)
//			throw new MessageFormatError("El formato del mensaje recibido es inválido y no ha podido procesarse");
		return msgRecivedFromClient;
	}
	
	public static boolean classifyClientMessage(String message) {
		return message.contains("#");
	}

//	public static boolean validateSeguraSatellitalMessage(String msgRecivedFromClient) {
//		return false;
//	}
	
}