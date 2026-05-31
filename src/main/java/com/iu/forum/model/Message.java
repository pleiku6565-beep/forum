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
    private User user;

    @ManyToOne
    @JoinColumn(name = "thread_id", nullable = false)
    private Thread thread;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // Thời gian sửa bình luận

    @Column(name = "is_edited", nullable = false)
    private boolean edited = false; // Đánh dấu bình luận này đã bị sửa (như trên Facebook)

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted = false; // Tính năng xóa mềm cho bình luận

    public Message() {
        this.createdAt = LocalDateTime.now();
    }

    public Message(String content, User user, Thread thread) {
        this.content = content;
        this.user = user;
        this.thread = thread;
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        this.edited = true; // Hễ có cập nhật nội dung là bật cờ "Đã chỉnh sửa" lên true
    }

    // --- GETTER VÀ SETTER ---
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}