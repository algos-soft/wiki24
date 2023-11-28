package it.algos.base24.backend.config;

import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Sun, 19-Nov-2023
 * Time: 09:18
 */
@Configuration
@EnableScheduling
public class SpringConfig {

//    @Bean
//    public JavaMailSender getJavaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//
//        mailSender.setUsername("my.gmail@gmail.com");
//        mailSender.setPassword("password");
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//
//        return mailSender;
//    }

}
