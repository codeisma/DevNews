package se.sdaproject;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.NotNull;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;

@Entity
public class ArticlesComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    private String authorName;

    @Column(nullable = false)
    private String body;


    @ManyToOne
    //@JsonIgnore
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JoinColumn(nullable = false)
    @NotNull
    private Articles commentedArticle;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void updateComment(ArticlesComments updatedArticleComment){
        this.authorName = updatedArticleComment.authorName;
        this.body = updatedArticleComment.body;
    }

    public Articles getCommentedArticle() {
        return commentedArticle;
    }

    public void setCommentedArticle(Articles commentedArticle) {
        this.commentedArticle = commentedArticle;
    }
}
