package com.cartrecipes.config;

import com.cartrecipes.config.mapping.MappingRule;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {

    private final List<MappingRule> mappingRules;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setFieldMatchingEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setFullTypeMatchingRequired(true)
                .setImplicitMappingEnabled(true)
                .setSkipNullEnabled(true);

        configureEntityDtoMappings(modelMapper);

        return modelMapper;
    }

    private void configureEntityDtoMappings(ModelMapper modelMapper) {
        mappingRules.forEach(mappingRule -> mappingRule.addMappings(modelMapper));
    }
}
