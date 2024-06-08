package com.ffa.back.repositories;

import com.ffa.back.models.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    Language findByLanguage(String language);
}
