package net.pslice.ampbot.commands;

import net.pslice.ampbot.AmpBot;
import net.pslice.archebot.Channel;
import net.pslice.archebot.Command;
import net.pslice.archebot.Permission;
import net.pslice.archebot.User;

public class CommandQuit extends Command<AmpBot> {

    public CommandQuit() {
        super("quit", "q");
        permission = Permission.get("ampbot.quit");
        parameters = "(reason)";
        description = "Disconnect the bot from the server";
    }

    public void execute(AmpBot bot, Channel channel, User sender, String[] args) {
        if (args.length == 0)
            bot.disconnect("Quit command by " + sender);
        else
            bot.disconnect(args[0]);
    }
}
