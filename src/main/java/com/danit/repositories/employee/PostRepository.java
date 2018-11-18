package com.danit.repositories.employee;

import com.danit.models.eployee.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
