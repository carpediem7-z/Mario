-- 初始化游戏房间
INSERT IGNORE INTO game_rooms (room_id, room_name, max_players, current_players, is_playing) VALUES
('room-1', '马里奥主房间', 10, 0, false),
('room-2', '竞技房间', 4, 0, false);

-- 初始化游戏场景表（如果需要）
INSERT IGNORE INTO game_scenes (scene_id, sort, is_last_scene, is_reach, is_base) VALUES
('scene-1', 1, 0, 0, 0),
('scene-2', 2, 0, 0, 0),
('scene-3', 3, 1, 0, 0);