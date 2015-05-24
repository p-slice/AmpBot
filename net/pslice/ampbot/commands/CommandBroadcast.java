package net.pslice.ampbot.commands;

import net.pslice.ampbot.AmpBot;
import net.pslice.archebot.Channel;
import net.pslice.archebot.Command;
import net.pslice.archebot.Permission;
import net.pslice.archebot.User;
import net.pslice.archebot.output.Message;

public class CommandBroadcast extends Command<AmpBot> {

    public CommandBroadcast() {
        super("broadcast", "b");
        permission = Permission.get("ampbot.broadcast");
        parameters = "<message>";
        description = "Broadcast a message to all channels the bot is in";
    }

    public void execute(AmpBot bot, Channel channel, User sender, String[] args) {
        if (args.length == 1)
            for (Channel c : bot.getChannels())
                bot.send(new Message(c, "[&3Broadcast&r] " + args[0]));
        else
            bot.paramErr(this, sender);
    }
}
