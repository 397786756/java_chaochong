package com.teeqee.norak.configuration;

import xyz.noark.core.annotation.Configuration;
import xyz.noark.core.annotation.Value;
import xyz.noark.core.annotation.configuration.Bean;
import xyz.noark.game.template.csv.CsvTemplateLoader;

@Configuration
public class GameServerConfiguration {

    @Value("template.path")
    private String templatePath;
    @Bean
    public CsvTemplateLoader templateLoader() {
        return new CsvTemplateLoader(templatePath, '	');
    }

}