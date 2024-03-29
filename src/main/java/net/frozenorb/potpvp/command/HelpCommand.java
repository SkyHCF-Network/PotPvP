package net.frozenorb.potpvp.command;

import com.google.common.collect.ImmutableList;

import net.frozenorb.potpvp.PotPvPLang;
import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.potpvp.match.MatchHandler;
import net.frozenorb.qlib.command.Command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Generic /help command, changes message sent based on if sender is playing in
 * or spectating a match.
 */
public final class HelpCommand {

    private static final List<String> HELP_MESSAGE_HEADER = ImmutableList.of(
        ChatColor.GRAY + PotPvPLang.LONG_LINE,
        "§b§lSkyHCF Practice Help",
        ChatColor.GRAY + PotPvPLang.LONG_LINE,
        "§7§lRemember: §eMost things are clickable!",
        ""
    );

    private static final List<String> HELP_MESSAGE_LOBBY = ImmutableList.of(
        "§bCommon Commands:",
        "§e/duel <player> §7- Challenge a player to a duel",
        "§e/party invite <player> §7- Invite a player to a party",
        "",
        "§bOther Commands:",
        "§e/party help §7- Information on party commands",
        "§e/report <player> <reason> §7- Report a player for violating the rules",
        "§e/request <message> §7- Request assistance from a staff member"
    );

    private static final List<String> HELP_MESSAGE_MATCH = ImmutableList.of(
        "§bCommon Commands:",
        "§e/spectate <player> §7- Spectate a player in a match",
        "§e/report <player> <reason> §7- Report a player for violating the rules",
        "§e/request <message> §7- Request assistance from a staff member"
    );

    private static final List<String> HELP_MESSAGE_FOOTER = ImmutableList.of(
        "",
        "§bServer Information:",
        PotPvPSI.getInstance().getDominantColor() == ChatColor.LIGHT_PURPLE ? "§eOfficial Rules §7- §bwww.skyhcf.net/rules" : "§eOfficial Rules §7- §bwww.skyhcf.net/rules",
        PotPvPSI.getInstance().getDominantColor() == ChatColor.LIGHT_PURPLE ? "§eStore §7- §bstore.skyhcf.net" : "§eStore §7- §bstore.skyhcf.net",
     // "§ePractice Leaderboards §7- §dwww.minehq.com/stats/potpvp",
        ChatColor.GRAY + PotPvPLang.LONG_LINE
    );

    @Command(names = {"help", "?", "halp", "helpme"}, permission = "")
    public static void help(Player sender) {
        MatchHandler matchHandler = PotPvPSI.getInstance().getMatchHandler();

        HELP_MESSAGE_HEADER.forEach(sender::sendMessage);

        if (matchHandler.isPlayingOrSpectatingMatch(sender)) {
            HELP_MESSAGE_MATCH.forEach(sender::sendMessage);
        } else {
            HELP_MESSAGE_LOBBY.forEach(sender::sendMessage);
        }

        HELP_MESSAGE_FOOTER.forEach(sender::sendMessage);
    }

}
