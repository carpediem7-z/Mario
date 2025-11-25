package edu.fdzc.mario.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "game_rooms")
public class GameRoom {
    @Id
    private String roomId;

    private String roomName;
    private int maxPlayers = 10;
    private int currentPlayers = 0;
    private boolean isPlaying = false;

    public GameRoom(String roomId, String roomName, int maxPlayers) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.maxPlayers = maxPlayers;
        this.currentPlayers = 0;
    }

    /**
     * 获取房间基本信息（不包含玩家数据）
     */
    public Map<String, Object> getRoomInfo() {
        Map<String, Object> roomInfo = new HashMap<>();
        roomInfo.put("roomId", roomId);
        roomInfo.put("roomName", roomName);
        roomInfo.put("maxPlayers", maxPlayers);
        roomInfo.put("currentPlayers", currentPlayers);
        roomInfo.put("isPlaying", isPlaying);
        return roomInfo;
    }
}