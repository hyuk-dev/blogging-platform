package dev.hyuk.blogging.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.hyuk.blogging.domain.Article;
import dev.hyuk.blogging.dto.AddArticleRequest;
import dev.hyuk.blogging.dto.UpdateArticleRequest;
import dev.hyuk.blogging.repository.BlogRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service

public class BlogService {
    private final BlogRepository blogRepository;

    //블로그 글 추가 메서드
    public Article save(AddArticleRequest request){
        return blogRepository.save(request.toEntity());
    }

    //블로그 글 조회 
    public List<Article> findAll(){
        return blogRepository.findAll();
    }

    public Article findById(long id){
        return blogRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(long id){
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request){
        Article article = blogRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent());
        return article;
    }
}
