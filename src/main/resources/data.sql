INSERT INTO user (login, password)
  SELECT * FROM (SELECT 'admin' as login, '$2a$10$o9hIdIwrBWLIgnqpiBvO8OfP6lLzOspm0Rw.FdcqXAElHYp9EEClW' as password) AS tmp
  WHERE NOT EXISTS (
      SELECT login FROM user
  ) LIMIT 1;