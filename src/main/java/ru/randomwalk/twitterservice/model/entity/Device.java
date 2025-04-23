package ru.randomwalk.twitterservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "DEVICE")
public class Device {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "DEVICE_TOKEN")
    private String deviceToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UserAccount user;

    @Column(name = "CREATED_AT")
    @CreationTimestamp
    private LocalDateTime createdAt;
}
