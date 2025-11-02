// JWT 密钥配置（仅后端使用）
module.exports = {
    secret: 'game_jwt_secret', // 本地开发密钥，上线后改复杂值
    expiresIn: '7d' // token 有效期7天
};