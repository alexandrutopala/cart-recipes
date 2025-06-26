package com.cartrecipes.config.mapping;

import org.modelmapper.ModelMapper;

public interface MappingRule {
    void addMappings(ModelMapper modelMapper);
}
