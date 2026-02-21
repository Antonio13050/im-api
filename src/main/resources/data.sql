INSERT INTO roles (role_id, nome) VALUES (1, 'ADMIN')
ON CONFLICT (role_id) DO NOTHING;

INSERT INTO roles (role_id, nome) VALUES (2, 'GERENTE')
ON CONFLICT (role_id) DO NOTHING;

INSERT INTO roles (role_id, nome) VALUES (3, 'CORRETOR')
ON CONFLICT (role_id) DO NOTHING;

INSERT INTO roles (role_id, nome) VALUES (4, 'SECRETARIO')
ON CONFLICT (role_id) DO NOTHING;
