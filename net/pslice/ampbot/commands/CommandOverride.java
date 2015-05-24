package net.pslice.ampbot.commands;

import net.pslice.ampbot.AmpBot;
import net.pslice.archebot.Channel;
import net.pslice.archebot.Command;
import net.pslice.archebot.Permission;
import net.pslice.archebot.User;
import net.pslice.archebot.output.Message;

public class CommandOverride extends Command<AmpBot> {

    public CommandOverride() {
        super("override", "o");
        permission = Permission.OPERATOR;
        parameters = "";
        description = "Toggle override mode";
    }

    public void execute(AmpBot bot, Channel channel, User sender, String[] args) {
        if (args.length == 0) {
            boolean override = bot.toggleOverride();
            for (Channel chan : bot.getChannels())
                bot.send(new Message(chan, override ? "Override mode enabled by '&9%s&r'. Only users with &10permission.operator&r can now perform commands."
                        : "Override mode disabled by '&9%s&r'. All users can now perform commands.", sender));
        } else
            bot.paramErr(this, sender);
    }
}
