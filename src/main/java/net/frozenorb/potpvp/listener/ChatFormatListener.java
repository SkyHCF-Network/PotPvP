package net.frozenorb.potpvp.listener;

import net.skyhcf.atmosphere.shared.AtmosphereShared;
import net.skyhcf.atmosphere.shared.SharedAPI;
import net.skyhcf.atmosphere.shared.chat.BukkitChat;
import net.skyhcf.atmosphere.shared.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Applies rank prefixes, if they exist.
 * Copied from https://github.com/FrozenOrb/qHub/blob/master/src/main/java/net/frozenorb/qhub/listener/BasicListener.java#L103
 */
public final class ChatFormatListener implements Listener {

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Profile profile = AtmosphereShared.getInstance().getProfileManager().getProfile(player.getUniqueId());
        String prefix = BukkitChat.format(profile.getActivePrefix() == null ? "" : profile.getActivePrefix().getPrefix()) + profile.getHighestGrantOnScope(SharedAPI.getServer(Bukkit.getServerName())).getRank().getPrefix();
        event.setFormat(BukkitChat.format(prefix) + "%s" + BukkitChat.RESET.toString() + BukkitChat.LIGHT_GRAY.toString() + ": " + BukkitChat.RESET.toString() + "%s");
        }
    }

