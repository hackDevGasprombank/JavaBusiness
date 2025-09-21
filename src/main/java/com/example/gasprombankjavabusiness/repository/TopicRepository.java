package com.example.gasprombankjavabusiness.repository;

import com.example.gasprombankjavabusiness.model.TopicModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TopicRepository extends JpaRepository<TopicModel, UUID> {
}
