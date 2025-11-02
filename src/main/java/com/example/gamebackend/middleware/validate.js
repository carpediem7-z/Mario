// 简单数据校验（防止前端传无效数据）
const { body, validationResult } = require('express-validator');

// 统一处理校验错误
const handleErrors = (req, res, next) => {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
        return res.status(400).json({ code: 400, msg: '输入错误', errors: errors.array() });
    }
    next();
};

// 注册校验（用户名非空，密码至少6位）
const registerRules = [
    body('username').notEmpty().withMessage('用户名不能为空'),
    body('password').isLength({ min: 6 }).withMessage('密码至少6位'),
    handleErrors
];

// 存档校验（确保用户ID和存档数据存在）
const saveRules = [
    body('userId').notEmpty().withMessage('用户ID不能为空'),
    body('data').notEmpty().withMessage('存档数据不能为空'),
    handleErrors
];

module.exports = { registerRules, saveRules };