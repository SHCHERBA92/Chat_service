package com.example.chat_service.model.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "message")
public class ShortingMess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mess_text")
    private String messText;

    @Column(name = "date_at")
    private LocalDate dateAt;

    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    @JsonIgnore
    private ChatEntity chatEntity;
}
