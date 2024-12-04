package com.ffa.back.mappers;

import com.ffa.back.dto.LanguageDTO;
import com.ffa.back.models.Language;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LanguageMapper {

    LanguageMapper INSTANCE = Mappers.getMapper(LanguageMapper.class);

    @Mapping(target = "users", ignore = true)
        // Ignorar para evitar ciclos
    LanguageDTO toLanguageDTO(Language language);

    @Mapping(target = "users", ignore = true)
        // Ignorar para evitar ciclos
    Language toLanguage(LanguageDTO languageDTO);
}