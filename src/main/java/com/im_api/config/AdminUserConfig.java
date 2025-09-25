package com.im_api.config;

import com.im_api.model.Role;
import com.im_api.model.User;
import com.im_api.repository.RoleRepository;
import com.im_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(RoleRepository roleRepository,
                           UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Role roleAdmin = roleRepository.findByNome(Role.Values.ADMIN.name())
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setNome(Role.Values.ADMIN.name());
                    return roleRepository.save(newRole);
                });

        userRepository.findByNome("admin").ifPresentOrElse(
                user -> {
                    System.out.println("Admin já existe");
                },
                () -> {
                    var user = new User();
                    user.setNome("admin");
                    user.setEmail("admin@admin.com");
                    user.setSenha(passwordEncoder.encode("admin"));
                    user.setAtivo(true);
                    user.setRoles(Set.of(roleAdmin));
                    userRepository.save(user);
                    System.out.println("Admin criado com sucesso");
                }
        );
    }
}
