package cn.monkey.game.utils;

import cn.monkey.commons.data.pojo.vo.ResultCode;
import cn.monkey.game.data.DMInfo;
import cn.monkey.game.data.Item;
import cn.monkey.game.data.Player;
import cn.monkey.game.data.Role;
import cn.monkey.game.state.core.GameStateContext;
import cn.monkey.proto.Command;
import cn.monkey.proto.CommandUtil;
import cn.monkey.proto.Game;
import com.google.common.base.Strings;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

public abstract class GameCmdUtil {

    public static Command.PackageGroup error(Throwable error) {
        return CommandUtil.error(ResultCode.ERROR, error);
    }

    public static Command.PackageGroup msg(String msg) {
        return CommandUtil.packageGroup(CommandUtil.pkg(ResultCode.OK, msg, null, null));
    }

    public static Command.PackageGroup enterSuccess(GameStateContext stateContext) {
        Command.ResultMessage resultMessage = CommandUtil.resultMessage(ResultCode.OK, null);
        Command.Package.Builder builder = Command.Package.newBuilder();
        builder.setResultMsg(resultMessage);
        Collection<Player> players = stateContext.getPlayers();
        if (players.size() > 0) {
        }
        return CommandUtil.packageGroup(builder.build());
    }

    public static Command.PackageGroup drawResult(int resultCode, String msg, GameStateContext gameStateContext) {
        return null;
    }


    public static Game.Player build(Player player) {
        String uid = player.getUid();
        String username = player.getUsername();
        Game.Player.Builder builder = Game.Player.newBuilder();
        if (!Strings.isNullOrEmpty(uid)) {
            builder.setId(uid);
        }
        if (!Strings.isNullOrEmpty(username)) {
            builder.setName(username);
        }
        return builder.build();
    }

    public static Game.Role build(Role role, boolean needDesc) {
        Game.Role.Builder builder = Game.Role.newBuilder();
        String name = role.getName();
        if (!Strings.isNullOrEmpty(name)) {
            builder.setName(name);
        }
        String briefIntroduction = role.getBriefIntroduction();
        if (!Strings.isNullOrEmpty(briefIntroduction)) {
            builder.setBriefIntroduction(briefIntroduction);
        }
        if (needDesc) {
            String desc = role.getDesc();
            if (!Strings.isNullOrEmpty(desc)) {
                builder.setDesc(desc);
            }
        }
        String img = role.getImg();
        if (!Strings.isNullOrEmpty(img)) {
            builder.setImg(img);
        }
        return builder.build();
    }


    public static Game.Player build(Player player, Role role, boolean needRoleDesc) {
        String uid = player.getUid();
        String username = player.getUsername();
        Game.Player.Builder builder = Game.Player.newBuilder();
        if (!Strings.isNullOrEmpty(uid)) {
            builder.setId(uid);
        }
        if (!Strings.isNullOrEmpty(username)) {
            builder.setName(username);
        }
        if (role != null) {
            Game.Role build = build(role, needRoleDesc);
            builder.setRole(build);
        }
        return builder.build();
    }

    public static Command.PackageGroup enterFail(String msg) {
        Command.ResultMessage resultMessage = CommandUtil.resultMessage(ResultCode.ERROR, msg);
        Command.Package.Builder builder = Command.Package.newBuilder();
        builder.setResultMsg(resultMessage);
        builder.setCmdType(GameCmdType.ENTER_RESULT);
        return CommandUtil.packageGroup(builder.build());
    }

    public static Command.PackageGroup buildChatResult(String uid, Game.ChatMsg chat) {
        List<Game.Content> contentsList = chat.getContentsList();
        Game.ChatMsgResult.Builder builder = Game.ChatMsgResult.newBuilder();
        builder.setFrom(uid);
        builder.addAllContents(contentsList);
        return CommandUtil.packageGroup(CommandUtil.pkg(ResultCode.OK, null, GameCmdType.CHAT_RESULT,
                builder.build().toByteString()));
    }

    public static Game.DMInfo build(DMInfo dmInfo) {
        Game.DMInfo.Builder builder = Game.DMInfo.newBuilder();
        String uid = dmInfo.getUid();
        if (!Strings.isNullOrEmpty(uid)) {
            builder.setUid(uid);
        }
        String username = dmInfo.getUsername();
        if (!Strings.isNullOrEmpty(username)) {
            builder.setUsername(username);
        }
        return builder.build();
    }

    public static Command.PackageGroup buildGameStateContextData(int cmdType, GameStateContext stateContext, Player player) {
        Player dm = stateContext.getDm();
        List<DMInfo> applyDmList = stateContext.getApplyDmList();
        String master = stateContext.getMaster();
        Collection<Player> players = stateContext.getPlayers();
        Game.GameData.Builder builder = Game.GameData.newBuilder();
        if (null != dm) {
            builder.setDm(build(dm));
        }
        if (!CollectionUtils.isEmpty(applyDmList)) {
            for (DMInfo dmInfo : applyDmList) {
                Game.DMInfo build = build(dmInfo);
                builder.addDmInfos(build);
            }
        }
        if (!Strings.isNullOrEmpty(master)) {
            builder.setMaster(master);
        }
        String uid = player.getUid();
        for (Player p : players) {
            String uid_ = p.getUid();
            Role role = p.getRole();
            if (uid.equals(uid_)) {
                // build my data
                Game.Player build = build(p, role, true);
                builder.addPlayers(build);
                continue;
            }
            Game.Player build = build(p, role, false);
            builder.addPlayers(build);
        }
        return CommandUtil.packageGroup(CommandUtil.pkg(ResultCode.OK, null, cmdType, builder.build().toByteString()));
    }

    public static Game.Item build(Item item) {
        Game.Item.Builder builder = Game.Item.newBuilder();
        return builder.setName(item.getName())
                .setDesc(item.getDesc())
                .setImg(item.getImg())
                .build();
    }

    public static Command.PackageGroup searchItemResult(Item item) {
        if (item == null) {
            Command.Package pkg = CommandUtil.pkg(ResultCode.OK, null, GameCmdType.SEARCH_ITEM_RESULT, null);
            return CommandUtil.packageGroup(pkg);
        }
        Game.Item build = build(item);
        return CommandUtil.packageGroup(CommandUtil.pkg(ResultCode.OK, null, GameCmdType.SEARCH_ITEM_RESULT, build.toByteString()));
    }

    public static Command.PackageGroup buildBroadCastMsg() {
        return null;
    }

    public static Command.PackageGroup buildAddItemResult(boolean addResult, GameStateContext stateContext) {
        return null;
    }

    public static Command.PackageGroup buildReturnItemResult(GameStateContext stateContext) {
        return null;
    }

    public static Command.PackageGroup buildEnterSceneResult(int fail, String msg, GameStateContext stateContext) {
        return null;
    }

    public static Command.PackageGroup buildPlayerExistSceneMsg(String msg, GameStateContext stateContext) {
        return null;
    }

    public static Command.PackageGroup buildPlayerEnterSceneMsg(String s, GameStateContext stateContext) {
        return null;
    }
}
