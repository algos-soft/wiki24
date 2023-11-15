package it.algos;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.*;
import org.springframework.scheduling.annotation.*;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"it.algos"}, exclude = {SecurityAutoConfiguration.class})
@Theme(value = "wiki24")
@NpmPackage(value = "line-awesome", version = "1.3.0")
@NpmPackage(value = "@vaadin-component-factory/vcf-nav", version = "1.0.6")
public class Wiki24App implements AppShellConfigurator {

    public static void main(String[] args) {
//        VaadBoot.start();
//        Wiki24Boot.start();
        SpringApplication.run(Wiki24App.class, args);
    }

}
