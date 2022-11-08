package com.example.chat_service.repositoryes.chat;

import com.example.chat_service.model.message.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
}
