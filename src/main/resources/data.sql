INSERT INTO images (original_name, stored_name, content_type, size, path, image_type, created_at)
VALUES
('AVATAR.PNG', 'avatar1.png', 'image/png', 1000, 'avatars/avatar1.png', 'AVATAR', NOW()),

('TOGEPI.GIF', 'togepi.gif', 'image/gif', 2000, 'pkmn/togepi.gif', 'PKMN_GIF', NOW());

INSERT INTO users (username, avatar_image, user_role, create_date, last_login, blocked, blocked_until, block_reason)
VALUES
('Misty', 1, 'ADMIN', '2026-01-10 20:15:00', NOW(), false, null, null);

INSERT INTO profiles (user_id, profile_text, twitch_url, youtube_url, discord_url)
VALUES
(1, 'This is my profile!', null, null, null);

INSERT INTO pokemon (dex_id, name, shiny_gif, hunt_count, date_first_hunted)
VALUES
(175, 'Togepi', 2, 1, '2026-01-10 20:15:00');

INSERT INTO hunts (used_game, hunt_method, hunt_status, encounters, pokemon_dex_id, user_id, created_date, finish_date, edit_date, finished_hunt)
VALUES
('Gold', 'RE', 'CURRENT', 100, 175, 1, '2026-01-10 20:15:00', null, '2026-02-10 20:15:00', null);