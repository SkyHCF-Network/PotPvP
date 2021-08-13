package net.frozenorb.potpvp.tab;

import java.util.function.BiConsumer;

import net.frozenorb.qlib.util.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.qlib.tab.TabLayout;

final class HeaderLayoutProvider implements BiConsumer<Player, TabLayout> {

    @Override
    public void accept(Player player, TabLayout tabLayout) {
        header: {
            tabLayout.set(1, 0, "&b&lSkyHCF");
        }

        status: {
            tabLayout.set(1, 1, ChatColor.GRAY + "&bYour Ping&7: &a" + PlayerUtils.getPing(player) + "ms");
        }
    }

}
