package net.frozenorb.potpvp.tab;

import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.qlib.tab.TabLayout;
import net.frozenorb.qlib.util.PlayerUtils;
import net.frozenorb.qlib.util.UUIDUtils;
import net.skyhcf.atmosphere.shared.AtmosphereShared;
import net.skyhcf.atmosphere.shared.SharedAPI;
import net.skyhcf.atmosphere.shared.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.function.BiConsumer;

public class OnlinePlayersLayoutProvider implements Listener, BiConsumer<Player, TabLayout> {

    private Map<UUID, String> playersMap = generateNewTreeMap();
    
    public OnlinePlayersLayoutProvider() {
        Bukkit.getPluginManager().registerEvents(this, PotPvPSI.getInstance());
        Bukkit.getScheduler().runTaskTimerAsynchronously(PotPvPSI.getInstance(), this::rebuildCache, 0, 1 * 60 * 20);
    }
    
    @Override
    public void accept(Player player, TabLayout tabLayout) {

    }
    
    @EventHandler(ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event) {
        playersMap.put(event.getPlayer().getUniqueId(), getName(event.getPlayer().getUniqueId()));
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        playersMap.remove(event.getPlayer().getUniqueId());
    }
    
    private void rebuildCache() {
        TreeMap<UUID, String> newTreeMap = generateNewTreeMap();
        
        Bukkit.getOnlinePlayers().forEach(player -> {
            newTreeMap.put(player.getUniqueId(), getName(player.getUniqueId()));
        });
        
        this.playersMap = newTreeMap;
    }
    
    private String getName(UUID uuid) {
        net.skyhcf.atmosphere.shared.profile.Profile profile = AtmosphereShared.getInstance().getProfileManager() == null ? null : AtmosphereShared.getInstance().getProfileManager().getProfile(uuid);
        if (profile == null) {
            return null;
        }

        Rank bestDisplayRank = profile.getHighestGrantOnScope(SharedAPI.getServer(Bukkit.getServerName())).getRank();
        if (bestDisplayRank == null || bestDisplayRank.getDisplayName().equals("default")) {
            return ChatColor.WHITE + UUIDUtils.name(uuid);
        }

        return bestDisplayRank.getColor() + UUIDUtils.name(uuid);
    }
    
    public int getPing(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return player == null ? -1 : Math.max(((PlayerUtils.getPing(player) + 5) / 10) * 10, 1);
    }
    
    private TreeMap<UUID, String> generateNewTreeMap() {
        return new TreeMap<UUID, String>(new Comparator<UUID>() {
            
            @Override
            public int compare(UUID first, UUID second) {
                net.skyhcf.atmosphere.shared.profile.Profile firstProfile = AtmosphereShared.getInstance().getProfileManager() == null ? null : AtmosphereShared.getInstance().getProfileManager().getProfile(first);
                net.skyhcf.atmosphere.shared.profile.Profile secondProfile = AtmosphereShared.getInstance().getProfileManager() == null ? null : AtmosphereShared.getInstance().getProfileManager().getProfile(second);
                
                if (firstProfile != null && secondProfile != null) {
                    int compare = Integer.compare(secondProfile.getHighestGrantOnScope(SharedAPI.getServer(Bukkit.getServerName())).getRank().getPriority(), firstProfile.getHighestGrantOnScope(SharedAPI.getServer(Bukkit.getServerName())).getRank().getPriority());
                    if (compare == 0) {
                        return tieBreaker(first, second);
                    }

                    return compare;
                } else if (firstProfile != null && secondProfile == null) {
                    return -1;
                } else if (firstProfile == null && secondProfile != null) {
                    return 1;
                } else {
                    return tieBreaker(first, second);
                }
            }
            
        });
    }

    private int tieBreaker(UUID first, UUID second) {
        String firstName = UUIDUtils.name(first);
        String secondName = UUIDUtils.name(second);

        return firstName.compareTo(secondName);
    }
    
}
