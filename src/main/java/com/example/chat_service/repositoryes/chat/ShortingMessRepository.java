package com.example.chat_service.repositoryes.chat;

import com.example.chat_service.model.message.ShortingMess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortingMessRepository extends JpaRepository<ShortingMess, Long> {
}
