INSERT INTO user (login, password)
  SELECT * FROM (SELECT 'admin' as login, '$2y$12$5jm8uoh6OoG.alGSgouLWu0IibSFgTSt0M3YPvNwNYBD9apAFdJ8C' as password) AS tmp
  WHERE NOT EXISTS (
      SELECT login FROM user
  ) LIMIT 1;