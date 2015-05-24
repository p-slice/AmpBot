package net.pslice.ampbot.commands;

import net.pslice.ampbot.AmpBot;
import net.pslice.archebot.Channel;
import net.pslice.archebot.Command;
import net.pslice.archebot.Permission;
import net.pslice.archebot.User;
import net.pslice.archebot.output.ErrorMessage;
import net.pslice.archebot.output.Message;
import net.pslice.utilities.StringUtils;

public class CommandPermissions extends Command<AmpBot> {

    public CommandPermissions() {
        super("permissions", "perms");
        parameters = "(user ((+|-)permission))";
        description = "View or change a user's permissions";
    }

    public void execute(AmpBot bot, Channel channel, User sender, String[] args) {
        if (args.length == 0)
            bot.send(new Message(channel, "Available permissions: &10" + StringUtils.compact(Permission.getAll(), "&r, &10")));
        else if (args.length == 1)
            bot.send(new Message(channel, "&9%s&r's permissions: &10" + StringUtils.compact(bot.getUser(args[0]).getPermissions(), "&r, &10"), args[0]));
        else if (args.length == 2) {
            User user = bot.getUser(args[0]);
            String p = (args[1].startsWith("+")) || (args[1].startsWith("-")) ? args[1].substring(1) : args[1];
            if (Permission.exists(p)) {
                Permission permission = Permission.get(p);
                if (args[1].startsWith("+"))
                    if (sender.hasPermission(Permission.OPERATOR)) {
                        if (permission.equals(Permission.OPERATOR))
                            bot.send(new ErrorMessage(sender, "That permission cannot be given."));
                        else if (!user.hasPermission(permission)) {
                            user.givePermission(permission);
                            bot.savePermissions(user);
                            bot.send(new Message(channel, "Permissions updated."));
                        } else
                            bot.send(new Message(channel, "&9%s&r already has that permission.", user));
                    } else
                        bot.send(new ErrorMessage(sender, "You do not have permission to do that. (Required permission: &10permission.operator&r)"));
                else if (args[1].startsWith("-"))
                    if (sender.hasPermission(Permission.OPERATOR)) {
                        if (permission.equals(Permission.OPERATOR) || permission.equals(Permission.DEFAULT))
                            bot.send(new ErrorMessage(sender, "That permission cannot be removed."));
                        else if (user.hasPermission(permission)) {
                            user.removePermission(permission);
                            bot.savePermissions(user);
                            bot.send(new Message(channel, "Permissions updated."));
                        } else
                            bot.send(new Message(channel, "&9%s&r already doesn't have that permission.", user));
                    } else
                        bot.send(new ErrorMessage(sender, "You do not have permission to do that. (Required permission: &10permission.operator&r)"));
                else if (user.hasPermission(permission))
                    bot.send(new Message(channel, "&9%s&r has that permission.", user));
                else
                    bot.send(new Message(channel, "&9%s&r does not have that permission.", user));
            } else
                bot.send(new ErrorMessage(sender, "The permission '&10%s&r' doesn't exist.", p));
        } else
            bot.paramErr(this, sender);
    }
}
