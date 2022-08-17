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
public class SendDoubleAuth {
	@RequestMapping(value = "/senddoubleauthresp", method = RequestMethod.GET)
	public ModelAndView view( HttpServletRequest request){
		
		Map<String, ?> inputFlashMap = RequestContextUtils
	            .getInputFlashMap(request);
		ModelAndView mav = new ModelAndView("/WEB-INF/view/result.jsp");
		if(inputFlashMap != null)			
			mav.addObject("theModelKey",inputFlashMap.get("theModelKey"));
		return mav;
	}
   @RequestMapping(value = "/senddoubleauthresp", method = RequestMethod.POST)
    public RedirectView handle(RedirectAttributes redirectAttrs, MultipartHttpServletRequest multipart, HttpServletRequest request) throws IOException, UnrecoverableKeyException, KeyManagementException, KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException{
        //ModelAndView mav = new ModelAndView("/WEB-INF/view/otramas.jsp");
	   	ResourceBundle  rb = null;
	   	//Locale english = new Locale("en","EN");
	   	//Locale french = new Locale("fr","FR");
	   	//Locale defaultLocale = Locale.getDefault();

        MultipartUtility multipartObj;
		multipartObj = new MultipartUtility("https://noticemanws.eadtrust.net/api_v1/type/doubleauth/", Charset.forName("UTF-8"), true);
		//multipartObj = new MultipartUtility("http://localhost:8080/noticemanws/service/type/html/", Charset.forName("UTF-8"), false);
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
        	switch(value){
        	case "acceptlimit":
        		String acceptlimit = multipart.getParameter(value);
        		if(acceptlimit.isEmpty() || (Integer.parseInt(acceptlimit)<1))
        			break;
        	case "scheduled":
        		String scheduled = multipart.getParameter(value);
        		if(!scheduled.matches("\\d{2}/\\d{2}/\\d{4}\\s\\d{2}:\\d{2}"))
        			break;
        	default:
        		multipartObj.addFormField(value,multipart.getParameter(value));
        	}
        	
        }
        MultiValueMap<String,MultipartFile> multiFiles = multipart.getMultiFileMap();
        Set<String> keys = multiFiles.keySet();
        for(String string : keys){
        	if(!string.equals("fileprov")){
        		MultipartFile multiFile = multiFiles.getFirst(string);
        		System.out.println("----Nombre archivo------"+multiFile.getOriginalFilename());
        		multipartObj.addFilePart(multiFile.getName(),multiFile.getOriginalFilename(), multiFile.getInputStream());
        		multipartObj.addFormField("utf_encoded_"+multiFile.getName(),multiFile.getOriginalFilename());
        	}
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
			redirectAttrs.addFlashAttribute("theModelKey", "<div class=\"success\">"+rb.getString("success").replace("%%CODE%%", message) + "</div>");
		
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
				redirectAttrs.addFlashAttribute("theModelKey", "<div class=\"error\">ERROR: ["+ message + "] " + rb.getString(message) + "</div>");
			}catch(Exception ex){
				redirectAttrs.addFlashAttribute("theModelKey", "<div class=\"error\">ERROR: ["+ message + "] " + rb.getString("0000000000") + "</div>");
			}
		}
		
        return new RedirectView("senddoubleauthresp",true);
    }
}
