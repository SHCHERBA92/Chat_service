package com.example.chat_service.model.message;

import com.example.chat_service.model.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "chat")
public class ChatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_sender_id")
    private Long accountSenderId;

    @Column(name = "account_receiver_id")
    private Long accountReceiverId;

    @OneToMany(mappedBy = "chatEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ShortingMess> shortingMesses;
}
