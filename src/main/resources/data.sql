-- 유저 추가
INSERT INTO users (username, password) VALUES ('user1', '1234');
INSERT INTO users (username, password) VALUES ('user2', '1234');
INSERT INTO users (username, password) VALUES ('admin', 'admin');

-- 여행 추가 (tripId=1) - 부산 여행
INSERT INTO trips (title, start_date, end_date, description, user_id, settled)
VALUES ('부산 여름 여행', '2026-05-24', '2026-05-27', '부산 해운대 3박 4일 여행', 1, false);

-- 여행 추가 (tripId=2) - 경주 여행
INSERT INTO trips (title, start_date, end_date, description, user_id, settled)
VALUES ('경주 역사 탐방', '2026-06-10', '2026-06-12', '경주 불국사·첨성대 2박 3일', 2, false);

-- 여행 멤버 추가 (부산)
INSERT INTO trip_members (trip_id, user_id) VALUES (1, 1);
INSERT INTO trip_members (trip_id, user_id) VALUES (1, 2);
INSERT INTO trip_members (trip_id, user_id) VALUES (1, 3);

-- 여행 멤버 추가 (경주)
INSERT INTO trip_members (trip_id, user_id) VALUES (2, 1);
INSERT INTO trip_members (trip_id, user_id) VALUES (2, 2);

-- 지출 내역 추가 (부산)
INSERT INTO expense (trip_id, title, price, travel_date, payer, category) VALUES (1, 'KTX 서울-부산', 59800, '2026-05-24', '세종대왕', '교통');
INSERT INTO expense (trip_id, title, price, travel_date, payer, category) VALUES (1, '해운대 숙소 (3박)', 210000, '2026-05-24', '이순신', '숙박');
INSERT INTO expense (trip_id, title, price, travel_date, payer, category) VALUES (1, '돼지국밥 저녁', 32000, '2026-05-25', '홍길동', '식비');
INSERT INTO expense (trip_id, title, price, travel_date, payer, category) VALUES (1, '해운대 수상레저', 90000, '2026-05-25', '세종대왕', '관광');
INSERT INTO expense (trip_id, title, price, travel_date, payer, category) VALUES (1, '자갈치시장 회', 75000, '2026-05-26', '이순신', '식비');
INSERT INTO expense (trip_id, title, price, travel_date, payer, category) VALUES (1, '감천문화마을 입장료', 2000, '2026-05-26', '홍길동', '관광');
INSERT INTO expense (trip_id, title, price, travel_date, payer, category) VALUES (1, '부산 기념품', 45000, '2026-05-27', '세종대왕', '쇼핑');
INSERT INTO expense (trip_id, title, price, travel_date, payer, category) VALUES (1, 'KTX 부산-서울', 59800, '2026-05-27', '이순신', '교통');

-- 지출 내역 추가 (경주)
INSERT INTO expense (trip_id, title, price, travel_date, payer, category) VALUES (2, 'KTX 서울-경주', 52000, '2026-06-10', '세종대왕', '교통');
INSERT INTO expense (trip_id, title, price, travel_date, payer, category) VALUES (2, '경주 한옥 숙소 (2박)', 160000, '2026-06-10', '이순신', '숙박');
INSERT INTO expense (trip_id, title, price, travel_date, payer, category) VALUES (2, '불국사 입장료', 6000, '2026-06-11', '세종대왕', '관광');
INSERT INTO expense (trip_id, title, price, travel_date, payer, category) VALUES (2, '경주 쌈밥 점심', 28000, '2026-06-11', '이순신', '식비');
INSERT INTO expense (trip_id, title, price, travel_date, payer, category) VALUES (2, '첨성대·동궁 야경 투어', 15000, '2026-06-11', '세종대왕', '관광');
INSERT INTO expense (trip_id, title, price, travel_date, payer, category) VALUES (2, '경주빵 쇼핑', 22000, '2026-06-12', '이순신', '쇼핑');

-- 공금 납입 내역 (부산)
INSERT INTO funds (amount, paid_date, note, trip_id, user_id) VALUES (150000, '2026-05-20', '부산 여행 공금', 1, 1);
INSERT INTO funds (amount, paid_date, note, trip_id, user_id) VALUES (150000, '2026-05-20', '부산 여행 공금', 1, 2);
INSERT INTO funds (amount, paid_date, note, trip_id, user_id) VALUES (150000, '2026-05-21', '부산 여행 공금', 1, 3);

-- 공금 납입 내역 (경주)
INSERT INTO funds (amount, paid_date, note, trip_id, user_id) VALUES (130000, '2026-06-05', '경주 여행 공금', 2, 1);
INSERT INTO funds (amount, paid_date, note, trip_id, user_id) VALUES (130000, '2026-06-05', '경주 여행 공금', 2, 2);
