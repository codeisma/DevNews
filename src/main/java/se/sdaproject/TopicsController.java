package se.sdaproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class TopicsController {
    TopicsRepository topicsRepository;
    ArticlesRepository articlesRepository;

    @Autowired
    public TopicsController(TopicsRepository topicsRepository, ArticlesRepository articlesRepository){
        this.topicsRepository = topicsRepository;
        this.articlesRepository = articlesRepository;
    }

    //Create a new topic
    @PostMapping("/topics")
    public ResponseEntity<Topics> createTopic(@RequestBody Topics topicParam){
        return ResponseEntity.status(HttpStatus.CREATED).body(topicsRepository.save(topicParam));
    }

    //Get All topics
    @GetMapping("/topics")
    public List<Topics> getAllTopics(){
        return topicsRepository.findAll();
    }

    //Retrieve all articles associated with a topic
    @GetMapping("/topics/{topicId}/articles")
    public Set<Articles> getAllArticlesAssociatedWithTopic(@PathVariable Long topicId){
      Topics topic = topicsRepository.findById(topicId).orElseThrow(ItemNotFoundException::new);
      return topic.getArticlesList();
    }

    //Update a topic
    @PutMapping("/topics/{topicId}")
    public ResponseEntity<Topics> updateTopic(@PathVariable Long topicId, @RequestBody Topics topicParam){
        Topics existingTopic = topicsRepository.findById(topicId).orElseThrow(ItemNotFoundException::new);
        topicParam.setId(existingTopic.getId());
        topicsRepository.save(topicParam);
        return ResponseEntity.ok(topicParam);
    }

    //Delete a topic
    @DeleteMapping("/topics/{topicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTopic(@PathVariable Long topicId) {
        Topics topic = topicsRepository.findById(topicId).orElseThrow(ItemNotFoundException::new);
        topicsRepository.delete(topic);
    }




}
