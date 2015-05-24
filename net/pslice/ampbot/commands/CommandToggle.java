package net.pslice.ampbot.commands;

import net.pslice.ampbot.AmpBot;
import net.pslice.archebot.Channel;
import net.pslice.archebot.Command;
import net.pslice.archebot.Permission;
import net.pslice.archebot.User;
import net.pslice.archebot.output.ErrorMessage;
import net.pslice.archebot.output.Message;

public class CommandToggle extends Command<AmpBot> {

    public CommandToggle() {
        super("toggle", "t");
        permission = Permission.get("ampbot.toggle");
        parameters = "<command...>";
        description = "Toggle the enabled state of commands";
    }

    public void execute(AmpBot bot, Channel channel, User sender, String[] args) {
        if (args.length > 0)
            for (String arg : args) {
                if (bot.isRegistered(arg)) {
                    Command command = bot.getCommand(arg);
                    if (command instanceof CommandToggle || command instanceof CommandOverride)
                        bot.send(new ErrorMessage(sender, "'&11%s&r' cannot be toggled.", command));
                    else {
                        command.setEnabled(!command.isEnabled());
                        bot.send(new Message(channel, "'&11%s&r' is now " + (command.isEnabled() ? "enabled" : "disabled")));
                    }
                } else
                    bot.send(new ErrorMessage(sender, "'&11%s&r' is not a registered command ID.", arg));
            }
        else
            bot.paramErr(this, sender);
    }
}
