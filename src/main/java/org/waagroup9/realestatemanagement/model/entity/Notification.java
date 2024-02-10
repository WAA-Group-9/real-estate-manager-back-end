package org.waagroup9.realestatemanagement.model.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Setter
@Getter
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "message")
    private String message;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "is_read")
    private Boolean isRead;

}