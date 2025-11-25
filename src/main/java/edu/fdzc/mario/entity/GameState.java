package edu.fdzc.mario.entity;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameState {
    private String roomId;
    private Map<String,Object> players;
    private long timestamp=System.currentTimeMillis();

    public GameState(String roomId,Map<String,Object> players){
        this.roomId=roomId;
        this.players=players;
    }

    public JSONObject toJSON(){
        JSONObject json=new JSONObject();
        json.put("type","gameState");
        json.put("roomId",roomId);
        json.put("players",players);
        json.put("timestamp",timestamp);
        return json;
    }
}
