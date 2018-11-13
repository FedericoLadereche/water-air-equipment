package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;

import common.JSONChecker;
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
					System.out.println("Servidor - Procesando mensaje recibido...");
					if(MessageUtils.classifyClientMessage(msgRecivedFromClient)) {
						processYSImessage(socket, msgRecivedFromClient);
					}
					else {
						processSeguraSatelitalMessage(socket, msgRecivedFromClient);
					}
					
				}
				closeIgnoringException(socket);
			}


			private void processYSImessage(final Socket socket, String msgRecivedFromClient)
					throws ClientProtocolException, IOException, URISyntaxException {
				String sondeDataJSON = JSONConverter.createJSONSondeDataFromObject(msgRecivedFromClient);
				System.out.printf("Servidor - JSON generado: %s\n", sondeDataJSON);
				if (JSONSender.sendJSONData(sondeDataJSON)) {
					System.out.printf("Servidor - Enviando respuesta al cliente: %s\n", msgRecivedFromClient);
					MessageUtils.sendMessage(socket, "Mensaje procesado: " + msgRecivedFromClient + "\n");
					System.out.printf("Servidor - Mensaje enviado\n");
				}
			}
			
			private void processSeguraSatelitalMessage(Socket socket, String msgRecivedFromClient) {
				if(JSONChecker.isJSONValid(msgRecivedFromClient)) {
					String seguraSateliteDataJSON = JSONConverter.createJSONSeguraSatelitalDataFromObject(msgRecivedFromClient);
					System.out.printf("Servidor - JSON generado: %s\n", seguraSateliteDataJSON);
						try {
							if (JSONSender.sendJSONData(seguraSateliteDataJSON)) {
								System.out.printf("Servidor - Enviando respuesta exitosa al cliente\n");
								MessageUtils.sendMessage(socket, "201");
								System.out.printf("\n");
								System.out.printf("\n");
								System.out.printf("\n");
								System.out.printf("\n");
							}
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (URISyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				} else
					try {
						MessageUtils.sendMessage(socket, "406\n" + "Formato de JSON inválido: " + msgRecivedFromClient);
					} catch (IOException e) {
						e.printStackTrace();
					}
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