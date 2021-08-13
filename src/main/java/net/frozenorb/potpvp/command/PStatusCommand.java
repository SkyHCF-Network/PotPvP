package net.frozenorb.potpvp.command;

import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.potpvp.follow.FollowHandler;
import net.frozenorb.potpvp.match.Match;
import net.frozenorb.potpvp.match.MatchHandler;
import net.frozenorb.potpvp.match.MatchTeam;
import net.frozenorb.potpvp.party.PartyHandler;
import net.frozenorb.potpvp.queue.QueueHandler;
import net.frozenorb.qlib.command.Command;
import net.frozenorb.qlib.command.Param;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class PStatusCommand {

    @Command(names = {"pstatus"}, permission = "op")
    public static void pStatus(Player sender, @Param(name="target", defaultValue = "self") Player target) {
        MatchHandler matchHandler = PotPvPSI.getInstance().getMatchHandler();
        QueueHandler queueHandler = PotPvPSI.getInstance().getQueueHandler();
        PartyHandler partyHandler = PotPvPSI.getInstance().getPartyHandler();
        FollowHandler followHandler = PotPvPSI.getInstance().getFollowHandler();

        final String LINE = net.md_5.bungee.api.ChatColor.BLUE.toString() + net.md_5.bungee.api.ChatColor.STRIKETHROUGH + "-----------------------------------------------------";

        sender.sendMessage(LINE);
        sender.sendMessage(ChatColor.RED + target.getName() + "'s - Player Status");
        sender.sendMessage(ChatColor.AQUA + "In match: " + ChatColor.WHITE + matchHandler.isPlayingMatch(target));
        sender.sendMessage(ChatColor.AQUA + "In match (NC): " + ChatColor.WHITE +  noCacheIsPlayingMatch(target));
        sender.sendMessage(ChatColor.AQUA + "Spectating match: " + ChatColor.WHITE +  matchHandler.isSpectatingMatch(target));
        sender.sendMessage(ChatColor.AQUA + "Spectating match (NC): " + ChatColor.WHITE +  noCacheIsSpectatingMatch(target));
        sender.sendMessage(ChatColor.AQUA + "In or spectating match: " + ChatColor.WHITE +  matchHandler.isPlayingOrSpectatingMatch(target));
        sender.sendMessage(ChatColor.AQUA + "In or spectating match (NC): " + ChatColor.WHITE + noCacheIsPlayingOrSpectatingMatch(target));
        sender.sendMessage(ChatColor.AQUA + "In queue: " + ChatColor.WHITE +  queueHandler.isQueued(target.getUniqueId()));
        sender.sendMessage(ChatColor.AQUA + "In party: " + ChatColor.WHITE +  partyHandler.hasParty(target));
        sender.sendMessage(ChatColor.AQUA + "Following: " + ChatColor.WHITE +  followHandler.getFollowing(target).isPresent());
        sender.sendMessage(LINE);
    }

    private static boolean noCacheIsPlayingMatch(Player target) {
        for (Match match : PotPvPSI.getInstance().getMatchHandler().getHostedMatches()) {
            for (MatchTeam team : match.getTeams()) {
                if (team.isAlive(target.getUniqueId())) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean noCacheIsSpectatingMatch(Player target) {
        for (Match match : PotPvPSI.getInstance().getMatchHandler().getHostedMatches()) {
            if (match.isSpectator(target.getUniqueId())) {
                return true;
            }
        }

        return false;
    }

    private static boolean noCacheIsPlayingOrSpectatingMatch(Player target) {
        return noCacheIsPlayingMatch(target) || noCacheIsSpectatingMatch(target);
    }

}