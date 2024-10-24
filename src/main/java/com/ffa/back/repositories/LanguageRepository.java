package com.ffa.back.repositories;

import com.ffa.back.models.Language;
import com.ffa.back.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface LanguageRepository extends ReactiveCrudRepository<Language, Long> {
    Mono<Language> findByLanguage(String language);
}
