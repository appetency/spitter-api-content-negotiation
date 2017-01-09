package spittr.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
@EnableWebMvc
@ComponentScan("spittr.web")
public class WebConfig extends WebMvcConfigurerAdapter {

  @Bean
  public ViewResolver viewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();  /*HTML的视图解析器*/
    resolver.setPrefix("/WEB-INF/views/");
    resolver.setSuffix(".jsp");
    return resolver;
  }
  
  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  /*
   * Configures content-negotiation.
   */
  @Configuration
  public static class ContentNegotiationConfig extends WebMvcConfigurerAdapter {
    /*配置ContentNegotiationManager*/
    @Bean
    public ViewResolver cnViewResolver(ContentNegotiationManager cnm) {
      ContentNegotiatingViewResolver cnvr =
          new ContentNegotiatingViewResolver();
      cnvr.setContentNegotiationManager(cnm);/*注入ContentNegotiationManager*/
      return cnvr;
    }

    @Override
    /*获得ContentNegotiationManager*/
    /*默认为HTML*/
    /*ContentNegotiationConfigurer中的一些方法对应于ContentNegotiationManager的Setter方法*/
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
      configurer.defaultContentType(MediaType.TEXT_HTML);
    }
    
    @Bean
    /*以bean的形式查找视图*/
    public ViewResolver beanNameViewResolver() {
       return new BeanNameViewResolver();
    }
    
    @Bean
    /*将"spittles"定义为JSON视图*/
    public View spittles() {  /*bean名称匹配逻辑视图的名称*/
      return new MappingJackson2JsonView();
    }
    
  }
  
}
