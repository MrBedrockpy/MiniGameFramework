package ru.mrbedrockpy.bedrocklib.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import ru.mrbedrockpy.bedrocklib.ChatUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseCommand implements TabExecutor {

    public BaseCommand(JavaPlugin plugin, String cmd) {
        plugin.getCommand(cmd).setExecutor(this);
        plugin.getCommand(cmd).setTabCompleter(this);
    }

    @Override
    public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!permission(sender)) {
            sender.sendMessage(getNotPermissionMessage());
            return false;
        }

        if (args.length == 0) {
            CommandResult result = execute(sender, args);

            if (result.getStatus().equals(ResultStatus.ERROR)) {
                if (!result.getMessage().isEmpty()) sender.sendMessage(result.getMessage());
                return false;
            }
        }

        else {
            for (SubCommand subCommand: getSubCommands()) {
                if (!args[0].equals(subCommand.getTag())) continue;
                if (!subCommand.permission(sender)) {
                    sender.sendMessage(getNotPermissionMessage());
                    return false;
                }
                CommandResult result = subCommand.execute(sender, args);
                if (result.getStatus().equals(ResultStatus.ERROR)) {
                    if (!result.getMessage().isEmpty()) sender.sendMessage(result.getMessage());
                    return false;
                }
                break;
            }
        }

        return true;
    }

    @Override
    public final List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            for (SubCommand subCommand: getSubCommands())
                if (subCommand.permission(sender))
                    list.add(subCommand.getTag());
        }
        else {
            for (SubCommand subCommand: getSubCommands())
                if (subCommand.permission(sender))
                    if (args[0].equals(subCommand.getTag()))
                        list.addAll(subCommand.completer(sender, args));
        }
        return list;
    }

    public boolean permission(CommandSender sender) {
        return true;
    }

    public abstract CommandResult execute(CommandSender sender, String[] args);

    public abstract List<SubCommand> getSubCommands();

    protected String getNotPermissionMessage() {
        return ChatUtil.format("У вас недостаточно прав для выполнения команды!");
    }
}
