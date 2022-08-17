package net.eadtrust.helper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.HostnameVerifier;

import net.eadtrust.web.controller.SendAuth;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
public class MultipartUtility {

	 

	 
	/**
	 * This utility class provides an abstraction layer for sending multipart HTTP
	 * POST requests to a web server.
	 * @author www.codejava.net
	 *
	 */
	    private final String boundary;
	    private static final String LINE_FEED = "\r\n";
	    private HttpURLConnection httpConn;
	    private HttpsURLConnection httpsConn;
	    private String path;
	    private String psPath;
	    private Charset charset;
	    private OutputStream outputStream;
	    private PrintWriter writer;
	 
	    /**
	     * This constructor initializes a new HTTP POST request with content type
	     * is set to multipart/form-data
	     * @param requestURL
	     * @param charset
	     * @throws IOException
	     * @throws NoSuchProviderException 
	     * @throws KeyStoreException 
	     * @throws CertificateException 
	     * @throws NoSuchAlgorithmException 
	     * @throws UnrecoverableKeyException 
	     * @throws KeyManagementException 
	     */
	    public MultipartUtility(String requestURL, Charset charset, boolean https)
	            throws IOException, KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, KeyManagementException {
	        this.charset = charset;
	        KeyManagerFactory kmf = null;
	        
	        if(https){
	        	Security.addProvider(new BouncyCastleProvider());
	        	KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
	        	InputStream keyInput = SendAuth.class.getResourceAsStream("/eadtrust.p12");
	        	keyStore.load(keyInput, ("bb480af54138bfe870f9").toCharArray());
	        	keyInput.close();

	        	kmf = KeyManagerFactory.getInstance("SunX509");
	        	kmf.init(keyStore, ("bb480af54138bfe870f9").toCharArray());
	        }
			
	        // creates a unique boundary based on time stamp
	        boundary = "===" + System.currentTimeMillis() + "===";
	         
	        URL url = new URL(requestURL);
	        
	        if(https){
	        	httpsConn = (HttpsURLConnection) url.openConnection();
				httpsConn.setHostnameVerifier(new HostnameVerifier() {
	                public boolean verify(String host, SSLSession sess) {
	                    if (host.equals("prepro.noticemanws.eadtrust.net")) return true;
	                    else return false;
	                }
	            });
	        	SSLContext context = SSLContext.getInstance("TLS");
	        	context.init(kmf.getKeyManagers(), null, new SecureRandom());
	        	httpsConn.setSSLSocketFactory(context.getSocketFactory());
	        	httpsConn.setRequestProperty("Connection", "close");
	        	httpsConn.setUseCaches (false);
	        	httpsConn.setDoInput(true);
	        	httpsConn.setDoOutput(true);
	        	httpsConn.setRequestMethod("POST");
	        	httpsConn.setRequestProperty("Content-Type",
	                "multipart/form-data; boundary=" + boundary);
	        	outputStream = httpsConn.getOutputStream();
	        }else{
		        httpConn = (HttpURLConnection) url.openConnection();
		        httpConn.setUseCaches(false);
		        httpConn.setDoOutput(true); // indicates POST method
		        httpConn.setDoInput(true);
		        httpConn.setRequestProperty("Content-Type",
		                "multipart/form-data; boundary=" + boundary);
		        outputStream = httpConn.getOutputStream();
		        writer = new PrintWriter(new OutputStreamWriter(outputStream, charset),
		                true);
	        }
	        //httpConn.setRequestProperty("User-Agent", "CodeJava Agent");
	        //httpConn.setRequestProperty("Test", "Bonjour");       
	        
	        
	        //httpConn.setUseCaches(false);
	        //httpConn.setDoOutput(true); // indicates POST method
	        //httpConn.setDoInput(true);


	        
	        writer = new PrintWriter(new OutputStreamWriter(outputStream, charset),
	                true);
	    }
	    /**
	     * Adds a form field to the request
	     * @param name field name
	     * @param value field value
	     */
	    public void addFormField(String name, String value) {
	        writer.append("--" + boundary).append(LINE_FEED);
	        writer.append("Content-Disposition: form-data; name=\"" + name + "\"")
	                .append(LINE_FEED);
	        writer.append("Content-Type: text/plain; charset=" + charset).append(
	                LINE_FEED);
	        writer.append(LINE_FEED);
	        writer.append(value).append(LINE_FEED);
	        writer.flush();
	    }
	 
	    /**
	     * Adds a upload file section to the request
	     * @param fieldName name attribute in <input type="file" name="..." />
	     * @param uploadFile a File to be uploaded
	     * @throws IOException
	     */
	    public void addFilePart(String fieldName, String fileName, InputStream stream)
	            throws IOException {
	        writer.append("--" + boundary).append(LINE_FEED);
	        writer.append(
	                "Content-Disposition: form-data; name=\"" + fieldName
	                        + "\"; filename=\"" + fileName + "\"")
	                .append(LINE_FEED);
	        writer.append(
	                "Content-Type: "
	                        + URLConnection.guessContentTypeFromName(fileName))
	                .append(LINE_FEED);
	        writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
	        writer.append(LINE_FEED);
	        writer.flush();
	        
	        byte[] buffer = new byte[4096];
	        int bytesRead = -1;
	        while ((bytesRead = stream.read(buffer)) != -1) {
	            outputStream.write(buffer, 0, bytesRead);
	        }
	        outputStream.flush();
	        stream.close();
	         
	        writer.append(LINE_FEED);
	        writer.flush();    
	    }
	 
	    /**
	     * Adds a header field to the request.
	     * @param name - name of the header field
	     * @param value - value of the header field
	     */
	    public void addHeaderField(String name, String value) {
	        writer.append(name + ": " + value).append(LINE_FEED);
	        writer.flush();
	    }
	     
	    /**
	     * Completes the request and receives response from the server.
	     * @return a list of Strings as response in case the server returned
	     * status OK, otherwise an exception is thrown.
	     * @throws IOException
	     */
	    public List<String> finishttp() throws IOException {
	        List<String> response = new ArrayList<String>();
	 
	        writer.append(LINE_FEED).flush();
	        writer.append("--" + boundary + "--").append(LINE_FEED);
	        writer.close();
	 
	        // checks server's status code first
	        int status = httpConn.getResponseCode();
	        if (status == HttpURLConnection.HTTP_OK) {
	            BufferedReader reader = new BufferedReader(new InputStreamReader(
	                    httpConn.getInputStream()));
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                response.add(line);
	            }
	            reader.close();
	            httpConn.disconnect();
	        } else {
	        	BufferedReader reader = new BufferedReader(new InputStreamReader(
	                    httpConn.getErrorStream()));
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                response.add(line);
	            }
	            throw new IOException("Server returned non-OK status: " + status + "\n" + response);
	        }
	 
	        return response;
	    }
	    public List<String> finishttps() throws IOException {
	        List<String> response = new ArrayList<String>();
	 
	        writer.append(LINE_FEED).flush();
	        writer.append("--" + boundary + "--").append(LINE_FEED);
	        writer.close();
	 
	        // checks server's status code first
	        int status = httpsConn.getResponseCode();
	        if (status == HttpsURLConnection.HTTP_OK) {
	            BufferedReader reader = new BufferedReader(new InputStreamReader(
	                    httpsConn.getInputStream()));
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                response.add(line);
	            }
	            reader.close();
	            httpsConn.disconnect();
	        } else {
	        	BufferedReader reader = new BufferedReader(new InputStreamReader(
	                    httpsConn.getErrorStream()));
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                response.add(line);
	            }
	            throw new IOException("Server returned non-OK status: " + status + "\n" + response);
	        }
	 
	        return response;
	    }
	}
