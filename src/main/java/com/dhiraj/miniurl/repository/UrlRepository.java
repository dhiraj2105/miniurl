package com.dhiraj.miniurl.repository;

import com.dhiraj.miniurl.model.UrlEntity;
import com.dhiraj.miniurl.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlEntity, Long> {

    Optional<UrlEntity> findByShortCode(String shortCode);

    boolean existsByShortCode(String shortCode);

//    fetch all urls created by a specific user
    List<UrlEntity> findAllBy(UserEntity user);

    List<UrlEntity> findAllByUser_Id(Long userId);
    List<UrlEntity> findAllByUser_IdOrderByCreatedAtDesc(Long userId);

    Optional<UrlEntity> findByIdAndUser_Id(Long id, Long userId);
}