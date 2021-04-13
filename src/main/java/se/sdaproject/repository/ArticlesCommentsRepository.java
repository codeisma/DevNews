package se.sdaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import se.sdaproject.model.ArticlesComments;

import java.util.List;

public interface ArticlesCommentsRepository extends JpaRepository<ArticlesComments,Long> {
    List<ArticlesComments> findByAuthorName(@Param("name") String name);
}
