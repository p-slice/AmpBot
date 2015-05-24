package net.pslice.ampbot;

import net.pslice.ampbot.commands.*;
import net.pslice.archebot.*;
import net.pslice.archebot.handlers.CommandHandler;
import net.pslice.archebot.handlers.MessageHandler;
import net.pslice.archebot.output.ErrorMessage;
import net.pslice.archebot.output.Message;

public class AmpBot extends ArcheBot implements CommandHandler<AmpBot>, MessageHandler<AmpBot> {

    /* Colours:
     * Generic - &7
     * Channels - &8
     * Users - &9
     * Permissions - &10
     * Commands - &11
     *
     * The following properties should be set to "true" for proper functionality:
     * enableCommands
     * enableQuoteSplit
     * enableColorShortcut
     */

    private boolean override = false;

    public AmpBot() {
        this(null);
    }

    public AmpBot(String directory) {
        super(directory);
        register(this);

        register(
                new CommandBroadcast(),
                new CommandEcho(),
                new CommandHelp(),
                new CommandJoin(),
                new CommandLeave(),
                new CommandOverride(),
                new CommandPermissions(),
                new CommandProperty(),
                new CommandQuit(),
                new CommandReload(),
                new CommandRename(),
                new CommandRestart(),
                new CommandToggle()
        );
        Permission.get("ampbot").include(
                "ampbot.broadcast",
                "ampbot.echo",
                "ampbot.join",
                "ampbot.leave",
                "ampbot.property",
                "ampbot.quit",
                "ampbot.rename",
                "ampbot.restart",
                "ampbot.toggle"
        );
    }

    public void paramErr(Command command, User sender) {
        send(new ErrorMessage(sender, "Incorrect parameters. (Use &11%shelp %s&r for details.)", getProperty(Property.prefix), command));
    }

    public boolean toggleOverride() {
        return override = !override;
    }

    @Override
    public void onCommand(AmpBot bot, Channel channel, User sender, Command<AmpBot> command, String[] args) {
        if (override && !sender.hasPermission(Permission.OPERATOR))
            bot.send(new ErrorMessage(sender, "You do not have permission to run commands in Override mode."));
        else if (!command.isEnabled() && !sender.hasPermission(Permission.OPERATOR))
            bot.send(new ErrorMessage(sender, "The command '&11%s&r' is currently disabled.", command));
        else if (sender.hasPermission(command.getPermission()) || sender.hasPermission(Permission.OPERATOR))
            command.execute(bot, channel, sender, args);
        else
            bot.send(new ErrorMessage(sender, "You do not have permission to do that. (Required permission: &10%s&r)", command.getPermission()));
    }

    @Override
    public void onMessage(AmpBot bot, Channel channel, User sender, String message) {}

    @Override
    public void onPrivateMessage(AmpBot bot, User sender, String message) {
        if (!sender.hasPermission(Permission.OPERATOR)) {
            for (User user : bot.getUsers())
                if (user.hasPermission(Permission.OPERATOR))
                    bot.send(new Message(user, "Message from &9%s&r: " + message, sender));
            bot.send(new Message(sender, "Your message has been forwarded to all bot operators."));
        }
    }
}
