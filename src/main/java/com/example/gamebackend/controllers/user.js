// 注册/登录接口（前端调用获取 token）
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const pool = require('../config/db');
const jwtConfig = require('../config/jwt');

// 注册新用户
exports.register = async (req, res) => {
    try {
        const { username, password } = req.body;

        // 检查用户名是否已存在
        const [users] = await pool.query('SELECT * FROM users WHERE username = ?', [username]);
        if (users.length > 0) {
            return res.status(400).json({ code: 400, msg: '用户名已被占用' });
        }

        // 密码加密（安全存储，前端无需处理）
        const hashedPwd = await bcrypt.hash(password, 10);

        // 存入 MySQL
        const [result] = await pool.query(
            'INSERT INTO users (username, password, create_time) VALUES (?, ?, NOW())',
            [username, hashedPwd]
        );

        res.json({ code: 200, msg: '注册成功', data: { userId: result.insertId, username } });
    } catch (err) {
        res.status(500).json({ code: 500, msg: '注册失败', error: err.message });
    }
};

// 用户登录（返回 token 给前端）
exports.login = async (req, res) => {
    try {
        const { username, password } = req.body;

        // 查询用户
        const [users] = await pool.query('SELECT * FROM users WHERE username = ?', [username]);
        if (users.length === 0) {
            return res.status(400).json({ code: 400, msg: '用户名或密码错误' });
        }
        const user = users[0];

        // 验证密码
        const isMatch = await bcrypt.compare(password, user.password);
        if (!isMatch) {
            return res.status(400).json({ code: 400, msg: '用户名或密码错误' });
        }

        // 生成 token（前端存储，后续接口带 token 访问）
        const token = jwt.sign(
            { userId: user.id, username: user.username },
            jwtConfig.secret,
            { expiresIn: jwtConfig.expiresIn }
        );

        res.json({
            code: 200,
            msg: '登录成功',
            data: { token, userId: user.id, username }
        });
    } catch (err) {
        res.status(500).json({ code: 500, msg: '登录失败', error: err.message });
    }
};