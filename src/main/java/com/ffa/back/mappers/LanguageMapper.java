package com.ffa.back.mappers;

import com.ffa.back.dto.LanguageDTO;
import com.ffa.back.models.Language;
import com.ffa.back.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface LanguageMapper {

    @Mapping(target = "userIds", expression = "java(mapUsersToIds(language.getUsers()))")
    LanguageDTO toLanguageDTO(Language language);

    @Mapping(target = "users", ignore = true)
    Language toLanguage(LanguageDTO languageDTO);

    // Métodos auxiliares
    default List<Long> mapUsersToIds(List<User> users) {
        if (users == null) {
            return null;
        }
        return users.stream().map(User::getId).collect(Collectors.toList());
    }
}