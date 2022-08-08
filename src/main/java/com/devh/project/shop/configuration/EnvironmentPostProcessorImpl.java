package com.devh.project.shop.configuration;

import com.devh.project.shop.configuration.vo.DatabaseConfigVO;
import com.devh.project.shop.configuration.vo.JwtConfigVO;
import com.devh.project.shop.configuration.vo.MailConfigVO;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;

public class EnvironmentPostProcessorImpl implements EnvironmentPostProcessor {

    @Getter
    public enum Config {
        MAIL("config/mail-config.yml"),
        JWT("config/jwt-config.yml"),
        DATABASE("config/database-config.yml");
        private final String filePath;
        Config(String filePath) {
            this.filePath = filePath;
        }
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            environment.getPropertySources().addFirst(createProperties(Config.MAIL));
            environment.getPropertySources().addFirst(createProperties(Config.JWT));
            environment.getPropertySources().addFirst(createProperties(Config.DATABASE));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to start post process ... - " + e.getMessage());
            System.exit(0);
        }

    }

    private PropertiesPropertySource createProperties(final Config config) throws FileNotFoundException {
        Properties properties = new Properties();
        switch (config) {
            case MAIL:
                MailConfigVO mailConfigVO = new Yaml(new Constructor(MailConfigVO.class)).load(new FileReader(config.getFilePath()));
                properties.put("mail.host", mailConfigVO.getHost());
                properties.put("mail.port", mailConfigVO.getPort());
                properties.put("mail.username", mailConfigVO.getUsername());
                properties.put("mail.password", mailConfigVO.getPassword());
                properties.put("mail.smtp.starttls.enable", mailConfigVO.getSmtp().getStarttls().isEnable());
                properties.put("mail.smtp.starttls.required", mailConfigVO.getSmtp().getStarttls().isRequired());
                properties.put("mail.smtp.auth", mailConfigVO.getSmtp().isAuth());
                properties.put("mail.smtp.connectiontimeout", mailConfigVO.getSmtp().getConnectiontimeout());
                properties.put("mail.smtp.timeout", mailConfigVO.getSmtp().getTimeout());
                properties.put("mail.smtp.writetimeout", mailConfigVO.getSmtp().getWritetimeout());
                break;
            case JWT:
                JwtConfigVO jwtConfigVO = new Yaml(new Constructor(JwtConfigVO.class)).load(new FileReader(config.getFilePath()));
                properties.put("jwt.issuer", jwtConfigVO.getIssuer());
                properties.put("jwt.secretKey", jwtConfigVO.getSecretKey());
                properties.put("jwt.header", jwtConfigVO.getHeader());
                properties.put("jwt.expire.access", jwtConfigVO.getExpire().getAccess());
                properties.put("jwt.expire.refresh", jwtConfigVO.getExpire().getRefresh());
                break;
            case DATABASE:
                DatabaseConfigVO databaseConfigVO = new Yaml(new Constructor(DatabaseConfigVO.class)).load(new FileReader(config.getFilePath()));
                properties.put("database.database", databaseConfigVO.getDatabase());
                properties.put("database.driverClassName", databaseConfigVO.getDriverClassName());
                properties.put("database.url", databaseConfigVO.getUrl());
                properties.put("database.username", databaseConfigVO.getUsername());
                properties.put("database.password", databaseConfigVO.getPassword());
                break;
            default:
                break;
        }

        return new PropertiesPropertySource(config.toString(), properties);
    }
    
}