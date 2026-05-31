package com.iu.forum.service;

import com.iu.forum.model.User;
import com.iu.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Hàm bắt buộc của Spring Security (UserDetailsService) để tìm kiếm tài khoản khi đăng nhập.
     * Nó tự động chuyển đổi thông tin User của ta thành đối tượng UserDetails mà hệ thống hiểu được.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản: " + username));

        // Cấp "Thẻ quyền hạn" (GrantedAuthority) dựa trên chuỗi Role lưu trong Database (ví dụ: ROLE_USER)
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isActive(), // true = được đăng nhập, false = tài khoản bị khóa/chưa kích hoạt
                true, // accountNonExpired
                true, // credentialsNonExpired
                true, // accountNonLocked
                Collections.singletonList(authority)
        );
    }

    // Nghiệp vụ bổ sung: Tìm thông tin User theo ID phục vụ các Controller khác
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng với ID: " + id));
    }
}