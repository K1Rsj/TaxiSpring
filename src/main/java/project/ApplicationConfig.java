package project;

import java.util.Locale;
import java.util.Properties;

import org.hibernate.cfg.Environment;
import org.hibernate.dialect.MySQL5Dialect;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mysql.jdbc.Driver;

//@Configuration
//@EnableWebMvc
//@ComponentScan("project")
@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories("project.model.dao.repository")
@EnableAspectJAutoProxy
public class ApplicationConfig implements WebMvcConfigurer {

    public static void main(String[] args) {
        System.setProperty("server.port", "8015");
        System.setProperty("server.error.whitelabel.enabled", "false");
        System.out.println(EVIL);
        SpringApplication.run(ApplicationConfig.class, args);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.addBasenames("/WEB-INF/jsp/resources/messages");
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

    @Bean
    LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");

        return localeChangeInterceptor;
    }

    @Bean
    LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.ENGLISH);

        return sessionLocaleResolver;
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        //internalResourceViewResolver.setViewClass(JstlView.class);
        internalResourceViewResolver.setPrefix("/WEB-INF/jsp/");
        internalResourceViewResolver.setSuffix(".jsp");

        return internalResourceViewResolver;
    }

    private DriverManagerDataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Driver.class.getCanonicalName());
        dataSource.setUrl("jdbc:mysql://localhost:3306/taxi");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        return dataSource;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(getDataSource());
        entityManagerFactoryBean.setPackagesToScan("project");
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(jpaProps());

        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager platformTransactionManager = new JpaTransactionManager();
        platformTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return platformTransactionManager;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    private Properties jpaProps() {
        Properties jpaProps = new Properties();
        //jpaProps.put(Environment.DRIVER, Driver.class.getCanonicalName());
        jpaProps.put(Environment.DIALECT, MySQL5Dialect.class.getCanonicalName());
        jpaProps.put(Environment.HBM2DDL_AUTO, "validate");
        jpaProps.put(Environment.SHOW_SQL, true);
        jpaProps.put(Environment.FORMAT_SQL, true);
        //jpaProps.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "org.springframework.orm.hibernate5.SpringSessionContext");

        return jpaProps;
    }

    public static final String EVIL =
            "                            ,-.                                \n"
                    + "       ___,---.__          /'|`\\          __,---,___           \n"
                    + "    ,-'    \\`    `-.____,-'  |  `-.____,-'    //    `-.        \n"
                    + "  ,'        |           ~'\\     /`~           |        `.      \n"
                    + " /      ___//              `. ,'          ,  , \\___      \\     \n"
                    + "|    ,-'   `-.__   _         |        ,    __,-'   `-.    |    \n"
                    + "|   /          /\\_  `   .    |    ,      _/\\          \\   |    \n"
                    + "\\  |           \\ \\`-.___ \\   |   / ___,-'/ /           |  /    \n"
                    + " \\  \\           | `._   `\\\\  |  //'   _,' |           /  /     \n"
                    + "  `-.\\         /'  _ `---'' , . ``---' _  `\\         /,-'      \n"
                    + "     ``       /     \\    ,='/ \\`=.    /     \\       ''         \n"
                    + "             |__   /|\\_,--.,-.--,--._/|\\   __|                 \n"
                    + "             /  `./  \\\\`\\ |  |  | /,//' \\,'  \\                 \n"
                    + "            /   /     ||--+--|--+-/-|     \\   \\                \n"
                    + "           |   |     /'\\_\\_\\ | /_/_/`\\     |   |               \n"
                    + "            \\   \\__, \\_     `~'     _/ .__/   /                \n"
                    + "             `-._,-'   `-._______,-'   `-._,-'                 \n";
}
