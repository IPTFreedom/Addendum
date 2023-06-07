package one.esixtwo.ipt.addendum;

import one.esixtwo.ipt.addendum.listeners.BlockRedstone;
import one.esixtwo.ipt.addendum.listeners.EntitySpawn;
import org.bukkit.plugin.java.JavaPlugin;

public final class Addendum extends JavaPlugin {

    @Override
    public void onEnable() {
        final BlockRedstone blockRedstoneListener = new BlockRedstone();
        blockRedstoneListener.init(this);

        getServer().getPluginManager().registerEvents(blockRedstoneListener, this);
        getServer().getPluginManager().registerEvents(new EntitySpawn(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
