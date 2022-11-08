package com.example.chat_service.services;

import com.example.chat_service.model.message.ChatEntity;
import com.example.chat_service.repositoryes.chat.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public List<ChatEntity> getAllChats(){
        return chatRepository.findAll();
    }
}
