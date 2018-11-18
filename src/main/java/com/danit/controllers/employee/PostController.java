package com.danit.controllers.employee;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.models.eployee.Post;
import com.danit.repositories.employee.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class PostController {

  @Autowired
  private PostRepository postRepository;

  @GetMapping("/post")
  public List<Post> retrieveAllPost() {
    return postRepository.findAll();
  }

  @GetMapping("/post/{id}")
  public Post retrievePost(@PathVariable long id) {
    Optional<Post> post = postRepository.findById(id);

    if (!post.isPresent()) {
      throw new EntityNotFoundException("id-" + id);
    }
    return post.get();
  }

  @DeleteMapping("/post/{id}")
  public void deletePost(@PathVariable long id) {
    postRepository.deleteById(id);
  }

  @PostMapping("/post")
  public ResponseEntity<Object> createPost(@RequestBody Post post) {
    Post savedPost = postRepository.save(post);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedPost.getId()).toUri();

    return ResponseEntity.created(location).build();

  }

  @PutMapping("/post/{id}")
  public ResponseEntity<Object> updatepost(@RequestBody Post post, @PathVariable long id) {

    Optional<Post> postOptional = postRepository.findById(id);

    if (!postOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    post.setId(id);

    postRepository.save(post);

    return ResponseEntity.noContent().build();
  }
}
