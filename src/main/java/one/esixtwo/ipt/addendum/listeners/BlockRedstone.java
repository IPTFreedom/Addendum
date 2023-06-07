package one.esixtwo.ipt.addendum.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.plugin.Plugin;

public class BlockRedstone implements Listener {
    private static final double REDSTONE_MINIMUM_TPS = 10;
    private static double tps = 20;

    @EventHandler
    public void onBlockRedstone(final BlockRedstoneEvent event) {
        if (tps < REDSTONE_MINIMUM_TPS) {
            event.setNewCurrent(0);
        }
    }

    private void updateTPS() {
        tps = Bukkit.getTPS()[0];
    }

    public void init(final Plugin plugin) {
        Bukkit.getScheduler().runTaskTimer(plugin, this::updateTPS, 0L, 200L); // 10s
    }
}
