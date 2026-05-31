package com.iu.forum.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nội dung tin nhắn không được để trống")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Người viết bài

    @ManyToOne
    @JoinColumn(name = "thread_id", nullable = false)
    private Thread thread; // Thuộc bài thảo luận nào

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "file_url")
    private String fileUrl;  

    // Thêm Getter và Setter cho nó
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Message() {
        this.createdAt = LocalDateTime.now();
    }

    public Message(String content, User user, Thread thread) {
        this.content = content;
        this.user = user;
        this.thread = thread;
        this.createdAt = LocalDateTime.now();
    }

    // Getter và Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}