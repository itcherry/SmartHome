INSERT INTO user (login, password)
  SELECT * FROM (SELECT 'admin' as login, '$2a$10$LSZXELzEuJ5CuevQ83xNO.pnnTsF8v5dEPCwwGSaahirsTbH2NvKy' as password) AS tmp
  WHERE NOT EXISTS (
      SELECT login FROM user
  ) LIMIT 1;