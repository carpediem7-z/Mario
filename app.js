const express = require('express');
const cors = require('cors');
const userCtrl = require('./src/main/java/com/example/gamebackend/controllers/user');
const saveCtrl = require('./src/main/java/com/example/gamebackend/controllers/save');
const rankCtrl = require('./src/main/java/com/example/gamebackend/controllers/rank');
const { registerRules, saveRules } = require('./src/main/java/com/example/gamebackend/middleware/validate');
const auth = require('./src/main/java/com/example/gamebackend/middleware/auth');

const app = express();
const port = 3000; // 前端调用的端口（保持不变，方便对接）

// 中间件（前端无需关心，直接用）
app.use(cors()); // 允许跨域
app.use(express.json()); // 解析 JSON 请求体

// 接口路由（前端直接调用这些路径）
app.post('/api/user/register', registerRules, userCtrl.register); // 注册
app.post('/api/user/login', userCtrl.login); // 登录
app.post('/api/save', auth, saveRules, saveCtrl.save); // 保存存档（需登录）
app.get('/api/save/:userId', auth, saveCtrl.load); // 读取存档（需登录）
app.get('/api/rank/top10', rankCtrl.getTop10); // 获取排行榜（无需登录）
app.post('/api/rank/submit', auth, rankCtrl.submitScore); // 提交分数（需登录）

// 测试接口（前端可访问 http://localhost:3000/api/test 验证服务是否正常）
app.get('/api/test', (req, res) => {
    res.json({ code: 200, msg: '后端服务正常' });
});

// 启动服务
app.listen(port, () => {
    console.log(`服务运行在 http://localhost:${port}`);
});