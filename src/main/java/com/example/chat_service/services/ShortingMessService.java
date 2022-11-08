package com.example.chat_service.services;

import com.example.chat_service.repositoryes.chat.ShortingMessRepository;
import org.springframework.stereotype.Service;

@Service
public class ShortingMessService {
    private final ShortingMessRepository shortingMessRepository;

    public ShortingMessService(ShortingMessRepository shortingMessRepository) {
        this.shortingMessRepository = shortingMessRepository;
    }


}
