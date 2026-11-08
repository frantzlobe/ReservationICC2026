INSERT INTO user_role (user_id, role_id)
SELECT u.id, r.id
FROM users u
JOIN roles r ON LOWER(u.role) = LOWER(r.role);