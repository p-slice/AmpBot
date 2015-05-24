package net.pslice.ampbot.commands;

import net.pslice.ampbot.AmpBot;
import net.pslice.archebot.*;
import net.pslice.archebot.output.ErrorMessage;
import net.pslice.archebot.output.Notice;
import net.pslice.utilities.StringUtils;

public class CommandHelp extends Command<AmpBot> {

    public CommandHelp() {
        super("help", "h", "?");
        parameters = "(command|permission)";
        description = "Display a list of commands or view details for a specific command";
    }

    public void execute(AmpBot bot, Channel channel, User sender, String[] args) {
        if (args.length == 0) {
            bot.send(new Notice(sender, "List of commands:"));
            bot.send(new Notice(sender, "Use &11%shelp <command>&r for specific command help", bot.getProperty(Property.prefix)));
            bot.send(new Notice(sender, "Commands coloured &4red&r are currently disabled."));
            for (Command command : bot.getCommands())
                if (sender.hasPermission(command.getPermission()) || sender.hasPermission(Permission.OPERATOR))
                    bot.send(new Notice(sender, "&b" + (command.isEnabled() ? "" : "&4") + "%s&r: %s", command, command.getDescription()));
        } else if (args.length == 1) {
            if (bot.isRegistered(args[0])) {
                Command command = bot.getCommand(args[0]);
                bot.send(new Notice(sender, "Help for command '&11%s&r':", command));
                bot.send(new Notice(sender, "&bPermission&r: %s", command.getPermission()));
                bot.send(new Notice(sender, "&bUsage&r: %s%s %s", bot.getProperty(Property.prefix), command, command.getParameters()));
                bot.send(new Notice(sender, "&bDescription&r: %s", command.getDescription()));
                bot.send(new Notice(sender, "&bEnabled&r: %b", command.isEnabled()));
                bot.send(new Notice(sender, "&bIDs&r: %s", command + (command.IDs.length > 0 ? ", " + StringUtils.compact(command.IDs, ", ") : "")));
            } else if (Permission.exists(args[0])) {
                bot.send(new Notice(sender, "List of commands with the permission '&10%s&r':", args[0]));
                bot.send(new Notice(sender, "Use &11%shelp <command>&r for specific command help", bot.getProperty(Property.prefix)));
                bot.send(new Notice(sender, "Commands coloured &4red&r are currently disabled."));
                Permission permission = Permission.get(args[0]);
                for (Command command : bot.getCommands())
                    if (command.getPermission() == permission || permission.includes(command.getPermission()))
                        bot.send(new Notice(sender, "&b" + (command.isEnabled() ? "" : "&4") + "%s&r: %s", command, command.getDescription()));
            } else
                bot.send(new ErrorMessage(sender, "'&11%s&r' is not recognized as a registered command ID or permission", args[0]));
        } else
            bot.paramErr(this, sender);
    }
}
