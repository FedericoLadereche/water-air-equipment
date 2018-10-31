package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;

import common.JSONConverter;
import common.JSONSender;
import common.MessageFormatError;
import common.exceptions.MessageUtils;

public class Server implements Runnable {
	ServerSocket serverSocket;
	volatile boolean keepProcessing = true;

	public Server(int port, int millisecondsTimeout) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(millisecondsTimeout);
	}

	public void run() {
		System.out.printf("Servidor arriba y listo para conectarse...\n");

		while (keepProcessing) {
			try {
				Socket socket = serverSocket.accept();
				System.out.printf("Cliente conectado al servidor...\n");
				process(socket);
			} catch (Exception e) {
				handle(e);
			}
		}
	}

	private void handle(Exception e) {
		if (!(e instanceof SocketException)) {
			e.printStackTrace();
		}
	}

	public void stopProcessing() {
		keepProcessing = false;
		closeIgnoringException(serverSocket);
	}

	void process(final Socket socket) {
		if (socket == null)
			return;

		Runnable clientHandler = new Runnable() {
			public void run() {
				try {
					reciveAndProcessClientMessages(socket);	
				} 
				catch (MessageFormatError e) {
					e.printStackTrace();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}

			private void reciveAndProcessClientMessages(final Socket socket) throws IOException, MessageFormatError,
														InterruptedException, ClientProtocolException, 
														URISyntaxException {
				String msgRecivedFromClient;
				while ((msgRecivedFromClient = MessageUtils.getMessage(socket))!= null) {
					System.out.printf("Servidor - Mensaje entrante: %s\n", msgRecivedFromClient);
					Thread.sleep(1000);
					System.out.println("Servidor - Procesando mensaje recibido...\n");
					if(MessageUtils.validateClientMessage(msgRecivedFromClient)) {
						String sondeDataJSON = JSONConverter.createJSONFromObject(msgRecivedFromClient);
						System.out.printf("Servidor - JSON generado: %s\n", sondeDataJSON);
						if (JSONSender.sendJSONData(sondeDataJSON)) {
							System.out.printf("Servidor - Enviando respuesta al cliente: %s\n", msgRecivedFromClient);
							MessageUtils.sendMessage(socket, "Mensaje procesado: " + msgRecivedFromClient + "\n");
							System.out.printf("Servidor - Mensaje enviado\n");
						}
					}
					else {
						MessageUtils.sendMessage(socket, "Mensaje recibido pero no procesado por el servidor: " + msgRecivedFromClient + "\n");
					}
					
				}
				closeIgnoringException(socket);
			}
		};

		Thread clientConnection = new Thread(clientHandler);
		clientConnection.start();
	}

	private void closeIgnoringException(Socket socket) {
		if (socket != null)
			try {
				socket.close();
			} catch (IOException ignore) {
			}
	}

	private void closeIgnoringException(ServerSocket serverSocket) {
		if (serverSocket != null)
			try {
				serverSocket.close();
			} catch (IOException ignore) {
			}
	}
}