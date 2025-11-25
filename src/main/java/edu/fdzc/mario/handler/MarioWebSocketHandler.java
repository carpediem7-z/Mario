package edu.fdzc.mario.handler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import edu.fdzc.mario.entity.GameState;
import edu.fdzc.mario.entity.Message;
import edu.fdzc.mario.entity.Player;
import edu.fdzc.mario.service.GameRoomService;
import edu.fdzc.mario.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class MarioWebSocketHandler implements WebSocketHandler {
    private final GameRoomService gameRoomService;
    private final PlayerService playerService;
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String sessionId = session.getId();
        sessions.put(sessionId, session);

        // 创建玩家
        Player player = playerService.createPlayer(sessionId, "玩家_" + sessionId.substring(0, 6));
        // 加入默认房间
        boolean joinSuccess = gameRoomService.joinRoom("room-1", player);
        // 发送欢迎消息
        Map<String, Object> welcomeMsg = new java.util.HashMap<>();
        welcomeMsg.put("type", "welcome");
        welcomeMsg.put("playerId", player.getId());
        welcomeMsg.put("playerName", player.getName());
        welcomeMsg.put("roomId", "room-1");
        welcomeMsg.put("joinSuccess", joinSuccess);

        session.sendMessage(new TextMessage(JSON.toJSONString(welcomeMsg)));
        // 广播房间状态
        broadcastRoomState("room-1");
        log.info("玩家连接: {}, 名称: {}, 当前在线: {}", sessionId, player.getName(), sessions.size());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = message.getPayload().toString();
        log.debug("收到消息: {}", payload);
        try {
            JSONObject json = JSON.parseObject(payload);
            String type = json.getString("type");
            String sessionId = session.getId();

            Player player = playerService.getPlayerBySession(sessionId);
            if (player == null) {
                log.warn("未找到玩家，sessionId: {}", sessionId);
                return;
            }
            switch (type) {
                case "playerMove":
                    handlePlayerMove(player, json);
                    break;
                case "playerJump":
                    handlePlayerJump(player);
                    break;
                case "chatMessage":
                    handleChatMessage(player, json.getString("message"));
                    break;
                case "changeRole":
                    handleChangeRole(player, json.getString("role"));
                    break;
                case "playerUpdate": // 新增：客户端物理更新
                    handlePlayerUpdate(player, json);
                    break;
                case "switchRoom": // 新增：切换房间
                    handleSwitchRoom(player, json.getString("targetRoomId"));
                    break;
                default:
                    log.warn("未知消息类型: {}", type);
            }
        } catch (Exception e) {
            log.error("处理消息异常: {}", e.getMessage(), e);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("传输错误, sessionId: {}", session.getId(), exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String sessionId = session.getId();
        sessions.remove(sessionId);

        Player player = playerService.getPlayerBySession(sessionId);
        if (player != null) {
            String roomId = player.getRoomId();
            gameRoomService.leaveRoom(player.getId());
            playerService.removePlayer(player.getId());

            if (roomId != null) {
                broadcastRoomState(roomId);
            }

            log.info("玩家断开: {}, 名称: {}, 剩余在线: {}", sessionId, player.getName(), sessions.size());
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 处理玩家移动 - 整合单机版游戏逻辑
     */
    private void handlePlayerMove(Player player, JSONObject data) {
        String direction = data.getString("direction");

        // 单机版移动逻辑
        switch (direction) {
            case "LEFT":
                player.leftMove();
                break;
            case "RIGHT":
                player.rightMove();
                break;
            case "STOP_LEFT":
                player.leftStop();
                break;
            case "STOP_RIGHT":
                player.rightStop();
                break;
        }

        // 应用物理更新
        player.update();

        playerService.updatePlayerPosition(player.getId(), player.getX(), player.getY(), player.getStatus());
        broadcastRoomState(player.getRoomId());
    }

    /**
     * 处理玩家跳跃
     */
    private void handlePlayerJump(Player player) {
        player.jump();
        // 应用物理更新
        player.update();

        playerService.updatePlayerPosition(player.getId(), player.getX(), player.getY(), player.getStatus());
        broadcastRoomState(player.getRoomId());
    }

    /**
     * 处理玩家物理更新（来自客户端的同步）
     */
    private void handlePlayerUpdate(Player player, JSONObject data) {
        if (data.containsKey("x")) player.setX(data.getIntValue("x"));
        if (data.containsKey("y")) player.setY(data.getIntValue("y"));
        if (data.containsKey("status")) player.setStatus(data.getString("status"));
        if (data.containsKey("score")) player.setScore(data.getIntValue("score"));

        playerService.updatePlayerPosition(player.getId(), player.getX(), player.getY(), player.getStatus());
        broadcastRoomState(player.getRoomId());
    }

    /**
     * 处理切换房间
     */
    private void handleSwitchRoom(Player player, String targetRoomId) {
        String currentRoomId = player.getRoomId();

        // 离开当前房间
        if (currentRoomId != null) {
            gameRoomService.leaveRoom(player.getId());
            broadcastRoomState(currentRoomId);
        }

        // 加入新房间
        boolean joinSuccess = gameRoomService.joinRoom(targetRoomId, player);

        // 发送切换结果
        Map<String, Object> switchResult = new java.util.HashMap<>();
        switchResult.put("type", "roomSwitch");
        switchResult.put("success", joinSuccess);
        switchResult.put("targetRoomId", targetRoomId);

        WebSocketSession session = sessions.get(player.getSessionId());
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(JSON.toJSONString(switchResult)));
            } catch (IOException e) {
                log.error("发送房间切换结果失败: {}", e.getMessage(), e);
            }
        }

        if (joinSuccess) {
            broadcastRoomState(targetRoomId);
        }
    }

    private void handleChatMessage(Player player, String message) {
        Message chatMsg = new Message("chat", player.getName(), message);
        broadcastToRoom(player.getRoomId(), "chatMessage", chatMsg);
    }

    private void handleChangeRole(Player player, String role) {
        Player updatedPlayer = playerService.changePlayerRole(player.getId(), role);
        if (updatedPlayer != null) {
            broadcastRoomState(player.getRoomId());
        }
    }

    private void broadcastRoomState(String roomId) {
        Map<String, Object> playersData = gameRoomService.getRoomState(roomId);
        if (playersData != null) {
            GameState gameState = new GameState(roomId, playersData);
            broadcastToRoom(roomId, "gameState", gameState.toJSON());
        }
    }

    private void broadcastToRoom(String roomId, String messageType, Object data) {
        Map<String, Object> message = new java.util.HashMap<>();
        message.put("type", messageType);
        message.put("data", data);
        message.put("timestamp", System.currentTimeMillis());

        String jsonMessage = JSON.toJSONString(message);

        // 通过 PlayerService 获取房间内的所有玩家
        List<Player> playersInRoom = playerService.getPlayersByRoom(roomId);
        for (Player player : playersInRoom) {
            WebSocketSession session = sessions.get(player.getSessionId());
            if (session != null && session.isOpen()) {
                try {
                    synchronized (session) {
                        session.sendMessage(new TextMessage(jsonMessage));
                    }
                } catch (IOException e) {
                    log.error("发送消息失败: {}", e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 获取当前在线会话数量
     */
    public int getOnlineCount() {
        return sessions.size();
    }

    /**
     * 向指定玩家发送消息
     */
    public void sendToPlayer(String sessionId, String messageType, Object data) {
        WebSocketSession session = sessions.get(sessionId);
        if (session != null && session.isOpen()) {
            try {
                Map<String, Object> message = new java.util.HashMap<>();
                message.put("type", messageType);
                message.put("data", data);
                message.put("timestamp", System.currentTimeMillis());

                synchronized (session) {
                    session.sendMessage(new TextMessage(JSON.toJSONString(message)));
                }
            } catch (IOException e) {
                log.error("向玩家发送消息失败: {}", e.getMessage(), e);
            }
        }
    }
}