package net.pslice.ampbot.commands;

import net.pslice.ampbot.AmpBot;
import net.pslice.archebot.Channel;
import net.pslice.archebot.Command;
import net.pslice.archebot.Permission;
import net.pslice.archebot.User;
import net.pslice.archebot.output.ErrorMessage;
import net.pslice.archebot.output.Message;

public class CommandEcho extends Command<AmpBot> {

    public CommandEcho() {
        super("echo", "e");
        permission = Permission.get("ampbot.echo");
        parameters = "(channel) <message>";
        description = "Echo a message to a channel";
    }

    public void execute(AmpBot bot, Channel channel, User sender, String[] args) {
        if (args.length == 1)
            bot.send(new Message(channel, args[0]));
        else if (args.length == 2) {
            if (args[0].startsWith("#") && !bot.getChannel(args[0]).contains(bot))
                bot.send(new ErrorMessage(sender, "Must be in '&8%s&r' to send a message there.", args[0]));
            else
                bot.send(new Message(args[0], args[1]));
        } else
            bot.paramErr(this, sender);
    }
}
