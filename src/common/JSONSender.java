package common;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.ClientProtocolException;

public class JSONSender {
	
	private static String url = "https://ws.dinama.gub.uy/recepcion_datos/index.php/recepcion/recepcion_directa";
	
	public static boolean sendJSONData(String json) throws ClientProtocolException, IOException, URISyntaxException {
		System.out.println("Servidor - Creando web service y enviando ");
		String credentials = setServiceCredentials();
		HttpURLConnection con = setServiceProperties(credentials);

		return sendJSONService(json, con);
	}

	private static boolean sendJSONService(String json, HttpURLConnection con) throws IOException {
		con.setDoOutput(true);
		java.io.OutputStream os = con.getOutputStream();
		os.write(json.getBytes());
		os.flush();
		
		String responseMessage = con.getResponseMessage();
		int responseCode = con.getResponseCode();
		System.out.println(responseCode + " " + responseMessage);
		
		return getBooleanAccordingResponseCode(responseCode);
	}

	private static boolean getBooleanAccordingResponseCode(int responseCode) {
		switch (responseCode) {
		case 200:
		case 201:
		case 202:
		case 203:
		case 204:
		case 205:
		case 206:
		case 207:
		case 208:
		case 226:
			return true;
		default:
			return false;
		}
	}

	private static HttpURLConnection setServiceProperties(String credentials)
			throws MalformedURLException, IOException, ProtocolException {
		URL obj = new URL(url);	   
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty  ("Authorization", "Basic " + credentials);
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("X-HTTP-Method-Override", "POST");
		
		return con;
	}

	private static String setServiceCredentials() {
		String cred= "dinama:dinama.2018";
		byte[] encoded = Base64.encodeBase64(cred.getBytes());           
		String credentials = new String(encoded);
		
		return credentials;
	}

}
