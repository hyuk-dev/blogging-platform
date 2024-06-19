package dev.hyuk.blogging.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.hyuk.blogging.domain.Article;

public interface BlogRepository extends JpaRepository <Article, Long> {
    
}
