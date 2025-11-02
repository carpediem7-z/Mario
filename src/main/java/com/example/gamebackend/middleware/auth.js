// 验证用户是否登录（保护存档、提交分数等接口）
const jwt = require('jsonwebtoken');
const jwtConfig = require('../config/jwt');

module.exports = (req, res, next) => {
    // 前端请求头带：Authorization: Bearer <token>
    const token = req.headers.authorization?.split(' ')[1];
    if (!token) {
        return res.status(401).json({ code: 401, msg: '请先登录' });
    }

    try {
        const user = jwt.verify(token, jwtConfig.secret);
        req.user = user; // 挂载用户信息到请求（后续接口可用）
        next();
    } catch (err) {
        return res.status(401).json({ code: 401, msg: '登录已过期，请重新登录' });
    }
};