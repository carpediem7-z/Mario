// MySQL 连接配置（修改为你的 MySQL 账号密码）
const mysql = require('mysql2/promise');

const pool = mysql.createPool({
    host: 'localhost',    // 本地 MySQL 地址（不变）
    password: 'wym',   // MySQL 密码（必须改成你自己的 MySQL 密码）
    database: 'game_db',  // 数据库名（后续会创建）
    connectionLimit: 10,  // 连接池大小（不变）
    waitForConnections: true
});

// 测试连接（可选，启动后看终端是否提示连接成功）
pool.getConnection().then(conn => {
    console.log('MySQL 连接成功');
    conn.release();
}).catch(err => {
    console.error('MySQL 连接失败：', err.message);
});

module.exports = pool;