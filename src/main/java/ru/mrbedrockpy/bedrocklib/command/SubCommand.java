package ru.mrbedrockpy.bedrocklib.command;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public interface SubCommand {

    String getTag();

    default boolean permission(CommandSender sender) {
        return true;
    }

    CommandResult execute(CommandSender sender, String[] args);

    default List<String> completer(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

}
