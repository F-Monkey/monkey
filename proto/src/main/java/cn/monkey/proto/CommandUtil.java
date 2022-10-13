package cn.monkey.proto;

import com.google.protobuf.ByteString;

public abstract class CommandUtil {

    public static Command.PackageGroup error(int errorCode, Throwable error) {
        Command.ResultMessage resultMessage = CommandUtil.resultMessage(errorCode, error.getMessage());
        Command.Package.Builder builder = Command.Package.newBuilder();
        builder.setResultMsg(resultMessage);
        return CommandUtil.packageGroup(builder.build());
    }

    public static Command.PackageGroup fail(int code, String msg) {
        Command.ResultMessage resultMessage = CommandUtil.resultMessage(code, msg);
        Command.Package.Builder builder = Command.Package.newBuilder();
        builder.setResultMsg(resultMessage);
        return CommandUtil.packageGroup(builder.build());
    }

    public static Command.ResultMessage resultMessage(int code, String msg) {
        Command.ResultMessage.Builder builder = Command.ResultMessage.newBuilder();
        builder.setCode(code);
        if (msg != null && msg.length() > 0) {
            builder.setMsg(msg);
        }
        return builder.build();
    }


    public static Command.Package pkg(int code, String msg, Integer cmdType, ByteString content) {
        Command.ResultMessage resultMessage = resultMessage(code, msg);
        Command.Package.Builder builder = Command.Package.newBuilder();
        builder.setResultMsg(resultMessage);
        if (cmdType != null) {
            builder.setCmdType(cmdType);
        }
        if (content != null) {
            builder.setContent(content);
        }
        return builder.build();
    }

    public static Command.PackageGroup packageGroup(Command.Package... pkgs) {
        Command.PackageGroup.Builder builder = Command.PackageGroup.newBuilder();
        for (Command.Package pkg : pkgs) {
            builder.addPackages(pkg);
        }
        return builder.build();
    }
}
