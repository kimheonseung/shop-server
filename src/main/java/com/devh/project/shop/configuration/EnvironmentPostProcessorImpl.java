package com.devh.project.shop.configuration;

import com.devh.project.shop.configuration.vo.JwtConfigVO;
import com.devh.project.shop.configuration.vo.MailConfigVO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileReader;
import java.util.Properties;

public class EnvironmentPostProcessorImpl implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            MailConfigVO mailConfigVO = new Yaml(new Constructor(MailConfigVO.class)).load(new FileReader("config/mail-config.yml"));
            Properties mailProperties = new Properties();
            mailProperties.put("mail.host", mailConfigVO.getHost());
            mailProperties.put("mail.port", mailConfigVO.getPort());
            mailProperties.put("mail.username", mailConfigVO.getUsername());
            mailProperties.put("mail.password", mailConfigVO.getPassword());
            mailProperties.put("mail.smtp.starttls.enable", mailConfigVO.getSmtp().getStarttls().isEnable());
            mailProperties.put("mail.smtp.starttls.required", mailConfigVO.getSmtp().getStarttls().isRequired());
            mailProperties.put("mail.smtp.auth", mailConfigVO.getSmtp().isAuth());
            mailProperties.put("mail.smtp.connectiontimeout", mailConfigVO.getSmtp().getConnectiontimeout());
            mailProperties.put("mail.smtp.timeout", mailConfigVO.getSmtp().getTimeout());
            mailProperties.put("mail.smtp.writetimeout", mailConfigVO.getSmtp().getWritetimeout());
            environment.getPropertySources().addFirst(new PropertiesPropertySource("mail", mailProperties));

            JwtConfigVO jwtConfigVO = new Yaml(new Constructor(JwtConfigVO.class)).load(new FileReader("config/jwt-config.yml"));
            Properties jwtProperties = new Properties();
            jwtProperties.put("jwt.issuer", jwtConfigVO.getIssuer());
            jwtProperties.put("jwt.secretKey", jwtConfigVO.getSecretKey());
            jwtProperties.put("jwt.header", jwtConfigVO.getHeader());
            jwtProperties.put("jwt.expire.access", jwtConfigVO.getExpire().getAccess());
            jwtProperties.put("jwt.expire.refresh", jwtConfigVO.getExpire().getRefresh());
            environment.getPropertySources().addFirst(new PropertiesPropertySource("jwt", jwtProperties));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to start post process ... - " + e.getMessage());
            System.exit(0);
        }

    }
    
}