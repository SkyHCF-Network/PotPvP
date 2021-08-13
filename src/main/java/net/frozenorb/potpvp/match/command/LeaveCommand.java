package net.frozenorb.potpvp.match.command;

import com.qrakn.morpheus.game.Game;
import com.qrakn.morpheus.game.GameQueue;
import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.potpvp.match.Match;
import net.frozenorb.potpvp.match.MatchHandler;
import net.frozenorb.qlib.command.Command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class LeaveCommand {

    @Command(names = { "spawn", "leave" }, permission = "")
    public static void leave(Player sender) {
        MatchHandler matchHandler = PotPvPSI.getInstance().getMatchHandler();

        if (matchHandler.isPlayingMatch(sender)) {
            sender.sendMessage(ChatColor.RED + "You cannot perform this action while in a match.");
            return;
        }

        Game game = GameQueue.INSTANCE.getCurrentGame(sender);
        if (game != null && game.getPlayers().contains(sender)) {
            sender.sendMessage(ChatColor.RED + "You cannot perform that action here.");
            return;
        }

        sender.sendMessage(ChatColor.GREEN + "Teleported to Spawn.");

        Match spectating = matchHandler.getMatchSpectating(sender);

        if (spectating == null) {
            PotPvPSI.getInstance().getLobbyHandler().returnToLobby(sender);
        } else {
            spectating.removeSpectator(sender);
        }
    }

}