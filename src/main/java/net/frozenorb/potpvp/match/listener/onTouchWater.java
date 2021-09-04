package net.frozenorb.potpvp.match.listener;

import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.potpvp.match.Match;
import net.skyhcf.atmosphere.bukkit.commands.SendCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class onTouchWater implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p == null)
            return;

        Match match = PotPvPSI.getInstance().getMatchHandler().getMatchPlaying(p);
        if (match == null)
            return;

        if (!match.getKitType().getId().equals("SPLEEF")) {
            return;
        }


        if (p.getLocation().getBlock().getType() == Material.WATER || (p.getLocation().getBlock().getType() == Material.STATIONARY_WATER)) {
            p.setHealth(0);

        }

    }
}
