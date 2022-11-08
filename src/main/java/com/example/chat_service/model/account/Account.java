package com.example.chat_service.model.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;
    private String password;

    @Column(name = "is_enable")
    private boolean isEnable;
    private String code;

    private String authorities;
//
//    //    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
//    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
//    private Master master;
//
//    //    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
//    @OneToOne(mappedBy = "account")
//    private Participant participant;
}
