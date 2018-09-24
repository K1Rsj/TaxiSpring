package project;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class WebMVCApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        System.out.println(ApplicationConfig.EVIL);
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(ApplicationConfig.class);
        applicationContext.register(SecurityConfig.class);

        servletContext.addListener(new ContextLoaderListener(applicationContext));
        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", new DispatcherServlet(applicationContext));
        registration.setLoadOnStartup(1);
        registration.addMapping("/");

        servletContext.addFilter("securityFilter", new DelegatingFilterProxy("springSecurityFilterChain"))
                .addMappingForUrlPatterns(null, false, "/*");

        servletContext.addFilter("encodingFilter", new CharacterEncodingFilter("UTF-8"))
                .addMappingForUrlPatterns(null, true, "/*");
    }
}
