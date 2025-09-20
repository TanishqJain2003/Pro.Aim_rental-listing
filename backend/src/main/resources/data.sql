-- Initialize default users for testing
-- Passwords are BCrypt encoded versions of the plain text passwords

-- Admin User (username: admin, password: admin123)
INSERT INTO users (username, email, password, first_name, last_name, role, user_type, email_verified, created_at, updated_at) 
VALUES ('admin', 'admin@proaim.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'Admin', 'User', 'ADMIN', 'LANDLORD', true, NOW(), NOW());

-- Regular User (username: user, password: user123)
INSERT INTO users (username, email, password, first_name, last_name, role, user_type, email_verified, created_at, updated_at) 
VALUES ('user', 'user@proaim.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 'Regular', 'User', 'USER', 'TENANT', true, NOW(), NOW());

-- Landlord User (username: landlord, password: landlord123)
INSERT INTO users (username, email, password, first_name, last_name, role, user_type, email_verified, created_at, updated_at) 
VALUES ('landlord', 'landlord@proaim.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'John', 'Landlord', 'USER', 'LANDLORD', true, NOW(), NOW());

-- Tenant User (username: tenant, password: tenant123)
INSERT INTO users (username, email, password, first_name, last_name, role, user_type, email_verified, created_at, updated_at) 
VALUES ('tenant', 'tenant@proaim.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'Jane', 'Tenant', 'USER', 'TENANT', true, NOW(), NOW());
