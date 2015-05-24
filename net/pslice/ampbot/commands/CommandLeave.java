package net.pslice.ampbot.commands;

import net.pslice.ampbot.AmpBot;
import net.pslice.archebot.Channel;
import net.pslice.archebot.Command;
import net.pslice.archebot.Permission;
import net.pslice.archebot.User;
import net.pslice.archebot.output.PartMessage;

public class CommandLeave extends Command<AmpBot> {

    public CommandLeave() {
        super("leave", "l");
        permission = Permission.get("ampbot.leave");
        parameters = "(channel...)";
        description = "Make the bot leave channels";
    }

    public void execute(AmpBot bot, Channel channel, User sender, String[] args) {
        if (args.length == 0)
            bot.send(new PartMessage(channel, "Leave command by %s.", sender));
        else
            for (String arg : args)
                bot.send(new PartMessage(arg, "Leave command by %s.", sender));
    }
}
