
INSERT INTO users (username, password) VALUES ('user1', '1234');
INSERT INTO users (username, password) VALUES ('user2', '1234');
INSERT INTO users (username, password) VALUES ('admin', 'admin');

INSERT INTO expense (trip_id, title, price, travel_date, payer, category) VALUES (1, '인천-도쿄 항공권', 320000, '2026-05-20', 'user1', '교통');
INSERT INTO expense (trip_id, title, price, travel_date, payer, category) VALUES (1, '도쿄 숙박 (3박)', 180000, '2026-05-20', 'user2', '숙박');
INSERT INTO expense (trip_id, title, price, travel_date, payer, category) VALUES (1, '라멘 저녁 식사', 25000, '2026-05-21', 'user1', '식비');
INSERT INTO expense (trip_id, title, price, travel_date, payer, category) VALUES (1, 'JR 패스', 75000, '2026-05-21', 'user2', '교통');
INSERT INTO expense (trip_id, title, price, travel_date, payer, category) VALUES (1, '돈키호테 쇼핑', 90000, '2026-05-22', 'user1', '쇼핑');
INSERT INTO expense (trip_id, title, price, travel_date, payer, category) VALUES (1, '아사쿠사 관광 입장료', 15000, '2026-05-22', 'user2', '관광');
INSERT INTO expense (trip_id, title, price, travel_date, payer, category) VALUES (1, '편의점 간식', 12000, '2026-05-23', 'user1', '식비');
INSERT INTO expense (trip_id, title, price, travel_date, payer, category) VALUES (1, '도쿄-인천 귀국편', 310000, '2026-05-23', 'user2', '교통');
