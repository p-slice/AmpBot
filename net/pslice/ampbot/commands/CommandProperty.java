package net.pslice.ampbot.commands;

import net.pslice.ampbot.AmpBot;
import net.pslice.archebot.*;
import net.pslice.archebot.output.ErrorMessage;
import net.pslice.archebot.output.Message;

public class CommandProperty extends Command<AmpBot> {

    public CommandProperty() {
        super("property", "p");
        permission = Permission.get("ampbot.property");
        parameters = "<property> (value)";
        description = "View or change properties";
    }

    public void execute(AmpBot bot, Channel channel, User sender, String[] args) {
        if (args.length == 1) {
            if (Property.isProperty(args[0])) {
                Property property = Property.get(args[0]);
                if (property != Property.nickservPass)
                    bot.send(new Message(channel, "Value of '&13%s&r': " + bot.getProperty(property), args[0]));
                else
                    bot.send(new ErrorMessage(sender, "That property cannot be displayed."));
            } else
                bot.send(new ErrorMessage(sender, "The property '&13%s&r' doesn't exist.", args[0]));
        } else if (args.length == 2) {
            if (Property.isProperty(args[0])) {
                bot.setProperty(Property.get(args[0]), args[1]);
                bot.saveData();
                bot.send(new Message(channel, "Property '&13%s&r' updated.", args[0]));
            } else
                bot.send(new ErrorMessage(sender, "The property '&13%s&r' doesn't exist.", args[0]));
        } else
            bot.paramErr(this, sender);
    }
}
