package skeletone.config;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import skeletone.base.resolver.CustomMapArgumentResolver;
import skeletone.base.util.MessageUtil;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	//https://www.petrikainulainen.net/programming/spring-framework/spring-from-the-trenches-creating-a-custom-handlermethodargumentresolver/
	//http://jdm.kr/blog/230

	@Value("${spring.mvc.locale}")
	Locale locale = null;
	@Value("${spring.messages.encoding}")
	String messagesEncoding = null;
	@Value("${spring.messages.cache-seconds}")
	int messagesCacheSeconds;
	@Value("${spring.messages.basename}")
	String messagesBasename = null;
    
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new CustomMapArgumentResolver());
    }
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//interceptor 등록
	    registry.addInterceptor(getHttpInterceptor()).addPathPatterns("/api/**");
	    
	    
	}
	
	@Bean
	public HandlerInterceptor getHttpInterceptor() {
		return new HandlerInterceptorAdapter(){
			@Override
			public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
				System.out.println("---Before Method Execution---");
				return true;
			}
			@Override
			public void postHandle(	HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
				System.out.println("---method executed---");
			}
			@Override
			public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
				System.out.println("---Request Completed---");
			} 
		};
	}
	
	
	//다국어 https://justinrodenbostel.com/2014/05/13/part-4-internationalization-in-spring-boot/
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(locale);
		return slr;
	}
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	//message source
	@Bean
	public ReloadableResourceBundleMessageSource messageSource(){
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename(messagesBasename);                //"classpath:/message"
		messageSource.setDefaultEncoding(messagesEncoding);
		messageSource.setCacheSeconds(messagesCacheSeconds);
		return messageSource;
	}

	@Bean
	public MessageSourceAccessor getMessageSourceAccessor(){
		ReloadableResourceBundleMessageSource m = messageSource();
		return new MessageSourceAccessor(m);
	}
	
	@Bean
	public MessageUtil messageUtil(){
		MessageUtil messageUtil = new MessageUtil();
		messageUtil.setMessageSourceAccessor(getMessageSourceAccessor());
		return messageUtil;
	}

}