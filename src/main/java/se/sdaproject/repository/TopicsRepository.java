package se.sdaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.sdaproject.model.Topics;

public interface TopicsRepository extends JpaRepository<Topics, Long> {

}
