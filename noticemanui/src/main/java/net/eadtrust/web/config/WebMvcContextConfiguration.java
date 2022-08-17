package net.eadtrust.web.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.context.annotation.ComponentScan;


/**
 * Spring MVC configuration
 * 
 * @author Marten Deinum
 * @author Koen Serneels
 * 
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "net.eadtrust.web" })
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter {

//    @Override
//    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resources/**/*").addResourceLocations("classpath:/META-INF/web-resources/");
//    }
    @Bean
    public InternalResourceViewResolver getViewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        //resolver.setPrefix("/WEB-INF/view/");
        //resolver.setSuffix(".htm");
        return resolver;
    }
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor localeChangeInterceptor=new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }
    public void addInterceptors(InterceptorRegistry registry) {
    	   registry.addInterceptor(localeChangeInterceptor());
    } 
    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
    	//registry.addViewController("/").setViewName("/WEB-INF/view/index.jsp");
    	registry.addViewController("/index.htm").setViewName("/WEB-INF/view/register.jsp");
    	registry.addViewController("/sendauth").setViewName("/WEB-INF/view/sendauth.jsp");
    	registry.addViewController("/senddoubleauth").setViewName("/WEB-INF/view/senddoubleauth.jsp");
    	registry.addViewController("/digpad").setViewName("/WEB-INF/view/digital.jsp");
    	registry.addViewController("/register").setViewName("/WEB-INF/view/register.jsp");
    	registry.addViewController("/act").setViewName("/WEB-INF/view/act.jsp");
    }

    @Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
	
    @Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("utf-8");
		multipartResolver.setMaxUploadSize(5 * 1024 * 1024);
		return multipartResolver;
    }
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(localeChangeInterceptor());
//    }

    //-- Start Locale Support (I18N) --//

    /**
     * The {@link LocaleChangeInterceptor} allows for the locale to be changed. It provides a <code>paramName</code> property which sets 
     * the request parameter to check for changing the language, the default is <code>locale</code>.
     * @return the {@link LocaleChangeInterceptor}
     */
//    @Bean
//    public HandlerInterceptor localeChangeInterceptor() {
//        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
//        localeChangeInterceptor.setParamName("lang");
//        return localeChangeInterceptor;
//    }

    /**
     * The {@link LocaleResolver} implementation to use. Specifies where to store the current selected locale.
     * 
     * @return the {@link LocaleResolver}
     */
//    @Bean
//    public LocaleResolver localeResolver() {
//        return new CookieLocaleResolver();
//    }

    /**
     * To resolve message codes to actual messages we need a {@link MessageSource} implementation. The default 
     * implementations use a {@link java.util.ResourceBundle} to parse the property files with the messages in it.
     * @return the {@link MessageSource}
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    //-- End Locale Support (I18N) --//

//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(categoryConverter());
//        registry.addFormatter(new DateFormatter("dd-MM-yyyy"));
//    }

//    @Bean
//    public StringToEntityConverter categoryConverter() {
//        return new StringToEntityConverter(Category.class);
//    }

}
