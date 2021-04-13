package se.sdaproject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class ArticlesController {
    ArticlesRepository articlesRepository;
    TopicsRepository topicsRepository;


    @Autowired
    public ArticlesController(ArticlesRepository articlesRepository, TopicsRepository topicsRepository){
        this.articlesRepository = articlesRepository;
        this.topicsRepository = topicsRepository;
    }

    //Retrieve an article by id
    @GetMapping("/articles/{id}")
    public Articles getArticle(@PathVariable Long id){
        Articles article = articlesRepository.findById(id).orElseThrow(ItemNotFoundException::new);
        return article;
    }

    //Create a new article
    @PostMapping("/articles")
    public Articles postArticle(@RequestBody Articles article){
        articlesRepository.save(article);
        return article;
    }

    //Retrieve all articles
    @GetMapping("/articles")
    public List<Articles> getArticlesList(){
        return articlesRepository.findAll();
    }

    //Delete an article by id
    @DeleteMapping("/articles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticle(@PathVariable Long id){
        Articles article = articlesRepository.findById(id).orElseThrow(ItemNotFoundException::new);
         articlesRepository.deleteById(id);
    }

    //Update an article by id
    @PutMapping("/articles/{id}")
    public ResponseEntity<Articles> updateArticle(@PathVariable Long id, @RequestBody Articles updatedArticle){
        Articles existingArticle = articlesRepository.findById(id).orElseThrow(ItemNotFoundException::new);
        updatedArticle.setId(id);
        articlesRepository.save(updatedArticle);
        return ResponseEntity.ok(updatedArticle);
    }
    //Get all topics associated with an article:
    @GetMapping("/articles/{articleId}/topics")
    public Set<Topics> getAllTopicsAssociatedWithArticle(@PathVariable Long articleId){
        Articles articles = articlesRepository.findById(articleId).orElseThrow(ItemNotFoundException::new);
       return articles.getTopicsList();
    }
    //Post a topic to an article, if the topic doesnt exist it creates it and save it
    @PostMapping("/articles/{articleId}/topics")
    public ResponseEntity<Topics> associateTopicWithArticle(@PathVariable Long articleId, @RequestBody Topics topicParam){
        Articles article = articlesRepository.findById(articleId).orElseThrow(ItemNotFoundException::new);
        boolean doesTopicExist = topicsRepository.existsById(topicParam.getId());
        topicParam.getArticlesList().add(article);
        topicsRepository.save(topicParam);
        if(!doesTopicExist){
            return ResponseEntity.status(HttpStatus.CREATED).body(topicParam);
        }else{
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(topicParam);
        }
    }

    //Delete the association between an article and a topic while keeping their existence:
    @DeleteMapping("/articles/{articleId}/topics/{topicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTopic(@PathVariable Long articleId, @PathVariable Long topicId) {
        Articles article = articlesRepository.findById(articleId).orElseThrow(ItemNotFoundException::new);
        Topics topic = topicsRepository.findById(topicId).orElseThrow(ItemNotFoundException::new);
        if (topic.getArticlesList().contains(article)) {
            topic.getArticlesList().remove(article);
            topicsRepository.save(topic);
        } else{
            throw new ItemNotFoundException();
        }
    }


}
