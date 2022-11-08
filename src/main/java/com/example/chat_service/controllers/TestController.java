package com.example.chat_service.controllers;

import com.example.chat_service.model.account.Account;
import com.example.chat_service.model.message.ChatEntity;
import com.example.chat_service.services.AccountService;
import com.example.chat_service.services.ChatService;
import com.example.chat_service.services.ShortingMessService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class TestController {

    private final AccountService accountService;
    private final ChatService chatService;
    private final ShortingMessService shortingMessService;

    public TestController(AccountService accountService, ChatService chatService, ShortingMessService shortingMessService) {
        this.accountService = accountService;
        this.chatService = chatService;
        this.shortingMessService = shortingMessService;
    }

    @GetMapping("hello")
    public ResponseEntity helloTest(){
        return ResponseEntity.ok("helloTest()");
    }

    @GetMapping("accounts")
    @Transactional(transactionManager = "transaction_manager_account")
    public List<Account> showAccounts(){
        var accounts =  accountService.getAllAccounts();
        return accounts;
    }

    @GetMapping("chats")
    @Transactional(transactionManager = "transaction_manager_chat")
    public List<ChatEntity> showChats(){
        var chats = chatService.getAllChats();
        return chats;
    }

}
