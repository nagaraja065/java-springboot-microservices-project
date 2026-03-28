package com.restaurant.config;

import com.restaurant.model.RestaurantTable;
import com.restaurant.model.User;
import com.restaurant.repository.TableRepository;
import com.restaurant.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepo,
                                     TableRepository tableRepo,
                                     PasswordEncoder encoder) {
        return args -> {
            // Create Admin if not exists
            if (!userRepo.existsByEmail("admin@restaurant.com")) {
                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@restaurant.com");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole("ADMIN");
                userRepo.save(admin);
                System.out.println("✅ Admin created: admin@restaurant.com / admin123");
            }

            // Create demo customer if not exists
            if (!userRepo.existsByEmail("customer@gmail.com")) {
                User customer = new User();
                customer.setName("John Customer");
                customer.setEmail("customer@gmail.com");
                customer.setPassword(encoder.encode("customer123"));
                customer.setRole("CUSTOMER");
                userRepo.save(customer);
                System.out.println("✅ Demo Customer: customer@gmail.com / customer123");
            }

            // Create sample tables if none exist
            if (tableRepo.count() == 0) {
                String[][] tables = {
                    {"T1", "2", "Indoor"},
                    {"T2", "4", "Indoor"},
                    {"T3", "4", "Window"},
                    {"T4", "6", "Window"},
                    {"T5", "2", "Outdoor"},
                    {"T6", "4", "Outdoor"},
                    {"T7", "8", "Private"},
                    {"T8", "10", "Private"},
                };
                for (String[] t : tables) {
                    RestaurantTable table = new RestaurantTable();
                    table.setTableNumber(t[0]);
                    table.setCapacity(Integer.parseInt(t[1]));
                    table.setLocation(t[2]);
                    table.setAvailable(true);
                    tableRepo.save(table);
                }
                System.out.println("✅ 8 sample tables created.");
            }
        };
    }
}
