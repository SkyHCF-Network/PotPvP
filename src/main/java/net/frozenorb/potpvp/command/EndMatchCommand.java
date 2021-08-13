package net.frozenorb.potpvp.command;

import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.potpvp.match.Match;
import net.frozenorb.potpvp.match.MatchEndReason;
import net.frozenorb.qlib.command.Command;
import net.skyhcf.atmosphere.shared.chat.BukkitChat;
import org.bukkit.entity.Player;

public class EndMatchCommand {

    @Command(names = {"endmatch"}, permission = "potpvp.admin")
    public static void endMatch(Player player){
        if(!PotPvPSI.getInstance().getMatchHandler().isPlayingMatch(player)){
            player.sendMessage(BukkitChat.format("&cYou are not in a match!"));
            return;
        }
        Match match = PotPvPSI.getInstance().getMatchHandler().getMatchPlaying(player);
        match.getTeams().forEach(team -> {
            team.messageAlive("&cThe match has been forcefully ended by a staff member.");
        });
        match.endMatch(MatchEndReason.ENDED_BY_STAFF);


    }

}
