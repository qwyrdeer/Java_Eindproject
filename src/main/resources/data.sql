INSERT INTO images (original_name, stored_name, content_type, size, image_type, created_at)
VALUES
('AVATAR.JPG', '6a7e0b6c-1d3e-4d90-a7df-3bfb9c1c70cb.jpg', 'image/jpg', 1000, 'AVATAR', NOW()),

('TOGEPI.GIF', '1faf6339-e68f-41c6-abb3-01a88877d0f1.gif', 'image/gif', 2000, 'PKMN_GIF', NOW());

INSERT INTO users (kcid, username, avatar_image, create_date, last_login, blocked, blocked_until, block_reason)
VALUES
('seed-admin-1', 'Ash', 1, '2026-01-10 20:15:00', NOW(), false, null, null);

INSERT INTO profiles (user_id, profile_text, twitch_url, youtube_url, discord_url)
VALUES
(1, 'This is my profile!', null, null, null);

INSERT INTO pokemon (dex_id, name, shiny_gif, hunt_count, date_first_hunted)
VALUES
(175, 'Togepi', 2, 1, '2026-01-10 20:15:00');

INSERT INTO hunts (used_game, hunt_method, hunt_status, encounters, pokemon_dex_id, user_id, created_date, finish_date, edit_date, finished_hunt)
VALUES
('Gold', 'RE', 'CURRENT', 100, 175, 1, '2026-01-10 20:15:00', null, '2026-02-10 20:15:00', null);