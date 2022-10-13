package cn.monkey.game.event;

import lombok.Data;

@Data
public class BroadCastDMInvitedEvent {
    private String groupId;
    private String masterId;
}
