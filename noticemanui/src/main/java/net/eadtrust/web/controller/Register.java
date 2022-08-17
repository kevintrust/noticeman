package net.eadtrust.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;

import net.eadtrust.helper.MultipartUtility;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller .
 * 
 * @author Said Boubnane
 */
@Controller
public class Register {
	//@RequestMapping(value = "/sendauthresp", method = RequestMethod.GET)
	public CloseableHttpResponse sendMultiPartPost(String url, Map<String,String> headers, Map<String,String> fields, MultiValueMap<String,MultipartFile> multiFiles,
			boolean ssl, String sslPathCert, String pass) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException, NoSuchProviderException, KeyManagementException{

		System.out.println("Nada");
		Registry<ConnectionSocketFactory> socketFactoryRegistry;
		if(ssl){
    	Security.addProvider(new BouncyCastleProvider());
    	KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
    	InputStream keyInput = SendAuth.class.getResourceAsStream("/eadtrust.p12");
    	keyStore.load(keyInput, ("a071c001b8dedf899054b2").toCharArray());
    	keyInput.close();

    	KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
    	kmf.init(keyStore, ("a071c001b8dedf899054b2").toCharArray());
    	SSLContext sslcontext = SSLContext.getInstance("TLS");
    	sslcontext.init(kmf.getKeyManagers(), null, new SecureRandom());
		//SSLContext sslcontext = SSLContexts.createSystemDefault();
		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
		        sslcontext, new String[] { "TLSv1", "SSLv3" }, null,
		        SSLConnectionSocketFactory.getDefaultHostnameVerifier());
		/*SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslcontext);*/
		
			socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
		        .register("http", PlainConnectionSocketFactory.INSTANCE)
		        .register("https", sslConnectionSocketFactory)
		        .build();
		}else{
			socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
			        .register("http", PlainConnectionSocketFactory.INSTANCE)
			        .build();
		}
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		CloseableHttpClient httpClient = HttpClients.custom()
		        .setConnectionManager(cm)
		        .build();
		//HttpPost uploadFile = new HttpPost("https://noticemanws.eadtrust.net/api_v1/register");
		//HttpPost uploadFile = new HttpPost("http://localhost:8080/noticemanws/service/register");
		HttpPost uploadFile = new HttpPost(url);
		if(headers != null){
			Set<String> keys = headers.keySet();
			for(String key: keys)
				uploadFile.addHeader(key, headers.get(key));
		}
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if(fields != null){
			Set<String> keys = fields.keySet();
			for(String key: keys)
				//builder.addTextBody("mail", "said.boubnane@eadtrust.net", ContentType.TEXT_PLAIN);
				builder.addTextBody(key,fields.get(key), ContentType.TEXT_PLAIN);
		}
		if(multiFiles != null){
			Set<String> keys = multiFiles.keySet();
			MultipartFile multiFile = null;
			for(String key:keys){
				if(!key.equals("fileprov")){
					multiFile = multiFiles.getFirst(key);
					builder.addBinaryBody(multiFile.getOriginalFilename(), multiFile.getInputStream());
				}
			}
		}
		
		
		HttpEntity multipart = builder.build();

      uploadFile.setEntity(multipart);

      CloseableHttpResponse resp = null;
    
		try {
			resp = httpClient.execute(uploadFile);
		} catch (IOException e1) {

		}

		return resp;
		
	}

	public ModelAndView view( HttpServletRequest request){
		
		Map<String, ?> inputFlashMap = RequestContextUtils
	            .getInputFlashMap(request);
		ModelAndView mav = new ModelAndView("/WEB-INF/view/result.jsp");
		if(inputFlashMap != null)			
			mav.addObject("theModelKey",inputFlashMap.get("theModelKey"));
		return mav;
	}
   @RequestMapping(value = "/registerresp", method = RequestMethod.POST)
    public ModelAndView handle(MultipartHttpServletRequest multipart, HttpServletRequest request) throws IOException, UnrecoverableKeyException, KeyManagementException, KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException{
	   	ResourceBundle  rb = null;
	   	ModelAndView mav = new ModelAndView("/WEB-INF/view/result.jsp");
        MultipartUtility multipartObj;
        //multipartObj = new MultipartUtility("https://prepro.noticemanws.eadtrust.net/noticemanws/service/register", Charset.forName("UTF-8"), true);
		multipartObj = new MultipartUtility("https://noticemanws.eadtrust.net/api_v1/register", Charset.forName("UTF-8"), true);
		//multipartObj = new MultipartUtility("http://localhost:8080/noticemanws/service/register", Charset.forName("UTF-8"), false);
        Enumeration<String> values = multipart.getHeaderNames();
        multipartObj.addFormField("ip",request.getRemoteAddr());
        
        while(values.hasMoreElements()){
        	String value = values.nextElement();
        	System.out.println(value+","+multipart.getHeader(value));
        	if(value.equals("accept-language")){
        		String language = multipart.getHeader(value).split(",")[0];
            	language = language.split("-")[0];
            	System.out.println("Y el lenguaje es " + language);
            	switch(language){
            		case "es":
            				break;
            		default: 
            			language = "en";
            	}
            	rb = ResourceBundle.getBundle("messages",new Locale(language,language.toUpperCase()));
            }
        	
        	multipartObj.addHeaderField(value,multipart.getHeader(value));
        	multipartObj.addFormField(value,multipart.getHeader(value));
        }
        multipartObj.addHeaderField("Cache-Control", "no-cache, no-store, must-revalidate");
        multipartObj.addHeaderField("Pragma", "no-cache");
        multipartObj.addHeaderField("Expires", "0");
        values = multipart.getParameterNames();
 
        while(values.hasMoreElements()){
        	String value = values.nextElement();
        	System.out.println(value+","+multipart.getParameter(value));
        	multipartObj.addFormField(value,multipart.getParameter(value));        	
        }
       
        List<String> response = null;
		try {
			response = multipartObj.finishttps();
			//response = multipartObj.finishttp();
			//mav.addObject("theModelKey", response.toString() + "TODO CORRECTO");
			String message = null;
			Pattern pattern = Pattern.compile(".*code\":\"([a-zA-Z0-9]{20}).*");
			Matcher matcher = pattern.matcher(response.toString());
			if (matcher.find()) 
				message = matcher.group(1);
			message = response.toString();
			
			//redirectAttrs.addFlashAttribute("theModelKey", "<div class=\"success\">"+rb.getString("success").replace("%%CODE%%", message) + "</div>");
			mav.addObject("theModelKey", "<div class=\"success\">"+rb.getString("registered")+ "</div>");
		
		} catch (IOException e) {
			//mav.addObject("theModelKey",e.getMessage() + "HUBO UN ERROR");
			String message;
			Pattern pattern = Pattern.compile(".*([0-9]{10}).*");
			Matcher matcher = pattern.matcher(e.getMessage());
			if (matcher.find()) 
				message = matcher.group(1);
			else
				message = "0000000000";
			try{
				//redirectAttrs.addFlashAttribute("theModelKey", "<div class=\"error\">ERROR: ["+ message + "] " + rb.getString(message) + "</div>");
				mav.addObject("theModelKey", "<div class=\"error\">ERROR: ["+ message + "] " + rb.getString(message) + "</div>");
			}catch(Exception ex){
				//redirectAttrs.addFlashAttribute("theModelKey", "<div class=\"error\">ERROR: ["+ message + "] " + rb.getString("0000000000") + "</div>");
				mav.addObject("theModelKey", "<div class=\"error\">ERROR: ["+ message + "] " + rb.getString("0000000000") + "</div>");
			}
		}
		
        return mav;
    }
}
