package com.iu.forum.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "threads")
public class Thread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator; // Người tạo ra thread

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Thread() {
        this.createdAt = LocalDateTime.now();
    }

    public Thread(String title, User creator) {
        this.title = title;
        this.creator = creator;
        this.createdAt = LocalDateTime.now();
    }

    // Getter và Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
