package edu.fdzc.mario.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String type;
    private String from;
    private String content;
    private long timestamp=System.currentTimeMillis();

    public Message(String type,String from,String content){
        this.type=type;
        this.from=from;
        this.content=content;
    }
}
