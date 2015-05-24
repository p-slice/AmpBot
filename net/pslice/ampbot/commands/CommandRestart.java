package net.pslice.ampbot.commands;

import net.pslice.ampbot.AmpBot;
import net.pslice.archebot.Channel;
import net.pslice.archebot.Command;
import net.pslice.archebot.Permission;
import net.pslice.archebot.User;

public class CommandRestart extends Command<AmpBot> {

    public CommandRestart() {
        super("restart", "rs");
        permission = Permission.get("ampbot.restart");
        parameters = "";
        description = "Restart the bot";
    }

    public void execute(AmpBot bot, Channel channel, User sender, String[] args) {
        if (args.length == 0)
            bot.disconnect("Restarting...", true);
        else
            bot.paramErr(this, sender);
    }
}
