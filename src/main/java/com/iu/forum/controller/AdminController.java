package com.iu.forum.controller;

import com.iu.forum.model.User;
import com.iu.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    // Xem danh sách và quản lý các Moderator hệ thống
    @GetMapping("/mods")
    public String manageMods(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin/manage-mods";
    }

    // Cấp quyền nâng cấp một User thông thường lên làm Moderator
    @GetMapping("/promote/{id}")
    public String promoteToMod(@PathVariable Long id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setRole("ROLE_MODERATOR");
            userRepository.save(user); // Cập nhật lại quyền trong DB
        });
        return "redirect:/admin/mods";
    }

    // TÍNH NĂNG MỚI: Hạ quyền Moderator xuống làm User thường
    @GetMapping("/demote/{id}")
    public String demoteToUser(@PathVariable Long id) {
        userRepository.findById(id).ifPresent(user -> {
            // Đảm bảo không ai có thể tự hạ quyền của chính Admin (Bảo vệ hệ thống)
            if (!user.getRole().equals("ROLE_ADMIN")) {
                user.setRole("ROLE_USER");
                userRepository.save(user);
            }
        });
        return "redirect:/admin/mods";
    }

    // TÍNH NĂNG MỚI: Khóa / Mở khóa tài khoản
    @GetMapping("/toggle-status/{id}")
    public String toggleStatus(@PathVariable Long id) {
        userRepository.findById(id).ifPresent(user -> {
            // Không cho phép tự khóa tài khoản ADMIN để tránh tự hủy hệ thống
            if (!user.getRole().equals("ROLE_ADMIN")) {
                user.setActive(!user.isActive()); // Đảo ngược trạng thái
                userRepository.save(user);
            }
        });
        return "redirect:/admin/mods";
    }

    // Hiển thị trang cài đặt thông số hệ thống của Admin
    @GetMapping("/settings")
    public String systemSettings() {
        return "admin/settings";
    }
}