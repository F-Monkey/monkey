package cn.monkey.game.utils;

public interface GameCmdType {
    int LOGIN = 100;
    int LOGIN_RESULT = 101;

    int CREATE = 102;

    int CREATE_RESULT = 103;

    int ENTER = 104;
    int ENTER_RESULT = 105;

    int READY = 106;
    int READY_RESULT = 107;

    int BROADCAST_DM_INVITED_INFO = 108;

    int BROADCAST_DM_INVITED_INFO_RESULT = 109;

    int DM_APPLY = 110;
    int DM_APPLY_RESULT = 111;

    int APPLY_SWITCH_ROLE = 112;

    int APPLY_SWITCH_ROLE_RESULT = 113;

    int ENTER_SCENE = 114;
    int ENTER_SCENE_RESULT = 115;

    int EXIT_SCENE = 116;
    int EXIT_SCENE_RESULT = 117;

    int SEARCH_ITEM = 118;
    int SEARCH_ITEM_RESULT = 119;

    int ADD_ITEM = 120;
    int ADD_ITEM_RESULT = 121;

    int RETURN_ITEM = 122;

    int RETURN_ITEM_RESULT = 123;

    int CHAT = 124;
    int CHAT_RESULT = 125;
    int AGREE_SWITCH_ROLE = 126;
    int AGREE_SWITCH_ROLE_RESULT = 127;

    int DRAW = 128;
    int DRAW_RESULT = 129;

    int REMOVE_DRAWER = 130;
    int REMOVE_DRAWER_RESULT = 131;
}
