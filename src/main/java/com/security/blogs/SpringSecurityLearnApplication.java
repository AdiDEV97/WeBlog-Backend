package com.security.blogs;

import com.security.blogs.Config.AppConstants;
import com.security.blogs.Dao.RoleRepo;
import com.security.blogs.Dao.UserRepo;
import com.security.blogs.Model.Role;
import com.security.blogs.Model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringSecurityLearnApplication implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRepo userRepo;

    public static void main(String[] args) {

        SpringApplication.run(SpringSecurityLearnApplication.class, args);
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Override
    public void run(String... args) throws Exception {

        // We will define Roles by-default if the application is runs for first time OR the default roles get deleted by accident
        try {
            Role adminRole = new Role();
            adminRole.setRoleId(AppConstants.ADMIN_USER);
            adminRole.setRole("ROLE_ADMIN");

            Role normalRole = new Role();
            normalRole.setRoleId(AppConstants.NORMAL_USER);
            normalRole.setRole("ROLE_NORMAL");

            List<Role> roles = List.of(adminRole, normalRole);

            List<Role> result = this.roleRepo.saveAll(roles);

            result.forEach(role -> {
                System.out.println(role.getRole());
            });

        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
