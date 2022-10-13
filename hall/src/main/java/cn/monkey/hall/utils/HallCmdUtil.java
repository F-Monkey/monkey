package cn.monkey.hall.utils;

import cn.monkey.commons.data.pojo.vo.ResultCode;
import cn.monkey.proto.Command;
import cn.monkey.proto.CommandUtil;
import cn.monkey.proto.Game;

public abstract class HallCmdUtil {
    public static Command.PackageGroup error(Throwable error) {
        return CommandUtil.error(ResultCode.ERROR, error);
    }

    public static Command.PackageGroup chooseRoomResult(int wait) {
        Game.ChooseRoomServerResult.Builder builder = Game.ChooseRoomServerResult.newBuilder();
        builder.setWaitCount(wait);
        return CommandUtil.packageGroup(CommandUtil.pkg(ResultCode.OK, null, HallCmdType.CHOOSE_GAME_SERVER_RESULT, builder.build().toByteString()));
    }

    public static Command.PackageGroup chooseRoomResult(String roomServerUrl) {
        Game.ChooseRoomServerResult.Builder builder = Game.ChooseRoomServerResult.newBuilder();
        builder.setRoomServerUrl(roomServerUrl);
        return CommandUtil.packageGroup(CommandUtil.pkg(ResultCode.OK, null, HallCmdType.CHOOSE_GAME_SERVER_RESULT, builder.build().toByteString()));
    }
}
