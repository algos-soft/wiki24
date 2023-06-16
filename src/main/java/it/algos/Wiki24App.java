package it.algos;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import it.algos.vaad24.backend.boot.*;
import it.algos.vaad24.backend.configuration.*;
import it.algos.wiki24.backend.boot.*;
import it.algos.wiki24.backend.packages.nazplurale.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.*;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication(scanBasePackages = {"it.algos"}, exclude = {SecurityAutoConfiguration.class})
@Theme(value = "wiki24")
@NpmPackage(value = "line-awesome", version = "1.3.0")
@NpmPackage(value = "@vaadin-component-factory/vcf-nav", version = "1.0.6")
public class Wiki24App implements AppShellConfigurator {

    public static void main(String[] args) {
        VaadBoot.start();
        Wiki24Boot.start();
        VaadConfiguration.START();
        SpringApplication.run(Wiki24App.class, args);
    }

}
