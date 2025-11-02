// 排行榜接口（前端显示排名、提交分数）
const pool = require('../config/db');

// 获取前10名排行榜
exports.getTop10 = async (req, res) => {
    try {
        // 关联用户表，按分数降序取前10
        const [rows] = await pool.query(`
      SELECT u.username, r.score, r.play_time 
      FROM rank_list r
      JOIN users u ON r.user_id = u.id
      ORDER BY r.score DESC
      LIMIT 10
    `);

        res.json({ code: 200, msg: '获取成功', data: rows });
    } catch (err) {
        res.status(500).json({ code: 500, msg: '获取排行榜失败', error: err.message });
    }
};

// 提交分数（仅保留最高分）
exports.submitScore = async (req, res) => {
    try {
        const { userId, score } = req.body;

        // 检查是否已有分数
        const [existing] = await pool.query('SELECT score FROM rank_list WHERE user_id = ?', [userId]);
        if (existing.length > 0) {
            const oldScore = existing[0].score;
            if (score <= oldScore) {
                return res.json({ code: 200, msg: '分数未超过历史最高' });
            }
            // 更新最高分
            await pool.query(
                'UPDATE rank_list SET score = ?, play_time = NOW() WHERE user_id = ?',
                [score, userId]
            );
        } else {
            // 新增分数记录
            await pool.query(
                'INSERT INTO rank_list (user_id, score, play_time) VALUES (?, ?, NOW())',
                [userId, score]
            );
        }

        res.json({ code: 200, msg: '分数提交成功' });
    } catch (err) {
        res.status(500).json({ code: 500, msg: '提交失败', error: err.message });
    }
};