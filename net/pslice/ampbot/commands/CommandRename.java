package net.pslice.ampbot.commands;

import net.pslice.ampbot.AmpBot;
import net.pslice.archebot.*;

public class CommandRename extends Command<AmpBot> {

    public CommandRename() {
        super("rename", "rn");
        permission = Permission.get("ampbot.rename");
        parameters = "<nick>";
        description = "Change the bot's nick";
    }

    public void execute(AmpBot bot, Channel channel, User sender, String[] args) {
        if (args.length == 0)
            bot.send("NICK " + bot.getProperty(Property.nick));
        else if (args.length == 1)
            bot.send("NICK " + args[0]);
        else
            bot.paramErr(this, sender);
    }
}
