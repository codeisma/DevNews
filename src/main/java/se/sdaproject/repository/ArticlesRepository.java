package se.sdaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.sdaproject.model.Articles;

public interface ArticlesRepository extends JpaRepository<Articles, Long> {
}
