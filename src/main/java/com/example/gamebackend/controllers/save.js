// 存档/读档接口（前端保存/加载游戏状态）
const pool = require('../config/db');

// 保存存档（覆盖旧存档）
exports.save = async (req, res) => {
    try {
        const { userId, data, name = '默认存档' } = req.body;

        // 检查是否已有存档，有则更新，无则新增
        const [existing] = await pool.query('SELECT * FROM game_saves WHERE user_id = ?', [userId]);
        if (existing.length > 0) {
            await pool.query(
                'UPDATE game_saves SET save_name = ?, save_data = ?, save_time = NOW() WHERE user_id = ?',
                [name, JSON.stringify(data), userId]
            );
        } else {
            await pool.query(
                'INSERT INTO game_saves (user_id, save_name, save_data, save_time) VALUES (?, ?, ?, NOW())',
                [userId, name, JSON.stringify(data)]
            );
        }

        res.json({ code: 200, msg: '存档成功' });
    } catch (err) {
        res.status(500).json({ code: 500, msg: '存档失败', error: err.message });
    }
};

// 读取存档
exports.load = async (req, res) => {
    try {
        const { userId } = req.params; // 从 URL 获取用户ID

        const [saves] = await pool.query('SELECT * FROM game_saves WHERE user_id = ?', [userId]);
        if (saves.length === 0) {
            return res.status(404).json({ code: 404, msg: '暂无存档' });
        }

        // 解析存档数据（前端直接用 JSON 对象）
        res.json({
            code: 200,
            msg: '读取成功',
            data: {
                name: saves[0].save_name,
                data: JSON.parse(saves[0].save_data),
                time: saves[0].save_time
            }
        });
    } catch (err) {
        res.status(500).json({ code: 500, msg: '读取失败', error: err.message });
    }
};