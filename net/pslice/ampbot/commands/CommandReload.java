package net.pslice.ampbot.commands;

import net.pslice.ampbot.AmpBot;
import net.pslice.archebot.Channel;
import net.pslice.archebot.Command;
import net.pslice.archebot.User;
import net.pslice.archebot.output.Message;

public class CommandReload extends Command<AmpBot> {

    public CommandReload() {
        super("reload", "rl");
        parameters = "";
        description = "Reload the bot's data file";
    }

    public void execute(AmpBot bot, Channel channel, User sender, String[] args) {
        if (args.length == 0) {
            bot.reload();
            bot.send(new Message(channel, "Data file reloaded."));
        } else
            bot.paramErr(this, sender);
    }
}
