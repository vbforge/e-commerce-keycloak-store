package com.vbforge.client.config;

import com.vbforge.client.entity.Product;
import com.vbforge.client.entity.Role;
import com.vbforge.client.entity.User;
import com.vbforge.client.repository.ProductRepository;
import com.vbforge.client.repository.RoleRepository;
import com.vbforge.client.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(RoleRepository roleRepository,
                               UserRepository userRepository,
                               ProductRepository productRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {

            // ── Roles ─────────────────────────────────────────────────────────
            Role userRole = roleRepository.findByName(Role.RoleName.ROLE_USER)
                .orElseGet(() -> roleRepository.save(
                    Role.builder().name(Role.RoleName.ROLE_USER).build()));

            Role adminRole = roleRepository.findByName(Role.RoleName.ROLE_ADMIN)
                .orElseGet(() -> roleRepository.save(
                    Role.builder().name(Role.RoleName.ROLE_ADMIN).build()));

            // ── Users ─────────────────────────────────────────────────────────
            if (!userRepository.existsByUsername("admin")) {
                userRepository.save(User.builder()
                    .username("admin")
                    .email("admin@admin.com")
                    .password(passwordEncoder.encode("admin123"))
                    .roles(Set.of(adminRole, userRole))
                    .build());
            }
            if (!userRepository.existsByUsername("alice")) {
                userRepository.save(User.builder()
                    .username("alice")
                    .email("alice@gmail.com")
                    .password(passwordEncoder.encode("alice123"))
                    .roles(Set.of(userRole))
                    .build());
            }

            // ── Products ──────────────────────────────────────────────────────
            if (productRepository.count() == 0) {
                productRepository.saveAll(List.of(
                    Product.builder().name("Wireless Headphones").price(89.99)
                        .description("Over-ear, noise-cancelling, 30h battery life.")
                        .category(Product.ProductCategory.ELECTRONICS).stock(15).build(),

                    Product.builder().name("Mechanical Keyboard").price(129.00)
                        .description("TKL layout, tactile brown switches, RGB backlight.")
                        .category(Product.ProductCategory.ELECTRONICS).stock(8).build(),

                    Product.builder().name("Ultrawide Monitor").price(449.00)
                        .description("34\" IPS, 3440×1440, 144Hz, USB-C.")
                        .category(Product.ProductCategory.ELECTRONICS).stock(4).build(),

                    Product.builder().name("Classic White T-Shirt").price(19.99)
                        .description("100% organic cotton, relaxed fit, unisex.")
                        .category(Product.ProductCategory.CLOTHING).stock(50).build(),

                    Product.builder().name("Slim Chino Trousers").price(49.99)
                        .description("Stretch canvas, mid-rise, tapered leg.")
                        .category(Product.ProductCategory.CLOTHING).stock(30).build(),

                    Product.builder().name("Clean Code").price(34.50)
                        .description("A handbook of agile software craftsmanship by Robert C. Martin.")
                        .category(Product.ProductCategory.BOOKS).stock(20).build(),

                    Product.builder().name("Designing Data-Intensive Applications").price(52.00)
                        .description("The big ideas behind reliable, scalable, and maintainable systems.")
                        .category(Product.ProductCategory.BOOKS).stock(12).build(),

                    Product.builder().name("Organic Coffee Beans").price(14.99)
                        .description("Single-origin Ethiopian, medium roast, 250g.")
                        .category(Product.ProductCategory.FOOD).stock(40).build(),

                    Product.builder().name("Protein Bar (12-pack)").price(22.00)
                        .description("Chocolate fudge, 20g protein per bar, no added sugar.")
                        .category(Product.ProductCategory.FOOD).stock(60).build(),

                    Product.builder().name("Cable Management Kit").price(11.99)
                        .description("Velcro ties, clips, and sleeves — 45-piece set.")
                        .category(Product.ProductCategory.OTHER).stock(25).build()
                ));
            }
        };
    }
}