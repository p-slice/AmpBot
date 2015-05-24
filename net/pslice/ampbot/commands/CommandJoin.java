package net.pslice.ampbot.commands;

import net.pslice.ampbot.AmpBot;
import net.pslice.archebot.Channel;
import net.pslice.archebot.Command;
import net.pslice.archebot.Permission;
import net.pslice.archebot.User;

public class CommandJoin extends Command<AmpBot> {

    public CommandJoin() {
        super("join", "j");
        permission = Permission.get("ampbot.join");
        parameters = "<channel...>";
        description = "Make the bot join channels";
    }

    public void execute(AmpBot bot, Channel channel, User sender, String[] args) {
        if (args.length > 0)
            for (String arg : args)
                bot.send("JOIN " + arg);
        else
            bot.paramErr(this, sender);
    }
}
