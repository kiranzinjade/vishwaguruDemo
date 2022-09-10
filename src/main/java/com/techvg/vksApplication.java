package com.techvg;

import com.techvg.vks.config.AuditorAwareImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

//import de.odysseus.el.tree.impl.Parser.ParseException;


@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing(auditorAwareRef="auditorProvider")
//@ComponentScan({"com.delivery.request"}) 
//@EntityScan("com.delivery.domain")
//@EnableJpaRepositories("com.deleivery.repository")
public class vksApplication extends SpringBootServletInitializer {

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(vksApplication.class);
    }  

	public static void main(String[] args) throws java.text.ParseException {
		SpringApplication.run(vksApplication.class, args);
	}

	@Bean
	AuditorAware<String> auditorProvider() {
		return new AuditorAwareImpl();
	}


}
