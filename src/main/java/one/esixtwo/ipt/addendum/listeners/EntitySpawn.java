package one.esixtwo.ipt.addendum.listeners;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.TNTPrimeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class EntitySpawn implements Listener {
    private static final double WORLD_MAXIMUM_MINECART_LIMIT = 80;
    private static final double WORLD_MAXIMUM_TNT_LIMIT = 80;

    private static final List<EntityType> MINECART_TYPES = Arrays.stream(EntityType.values()).filter((e) -> e.name().startsWith("MINECART")).toList();

    @SuppressWarnings("unchecked")
    private static final Class<? extends Entity>[] MINECART_TYPES_CLASSES = MINECART_TYPES.stream()
            .map((e) -> Objects.requireNonNull(e.getEntityClass()))
            .toArray(Class[]::new);

    private static boolean canSpawnEntity(final World world, final EntityType entityType) {
        if (entityType.equals(EntityType.PRIMED_TNT)) {
            return world.getEntitiesByClass(TNTPrimed.class).size() <= WORLD_MAXIMUM_TNT_LIMIT;
        } else if (MINECART_TYPES.contains(entityType)) {
            return world.getEntitiesByClasses(MINECART_TYPES_CLASSES).size() <= WORLD_MAXIMUM_MINECART_LIMIT;
        }

        return true;
    }

    @EventHandler
    public void onExplosionPrime(final ExplosionPrimeEvent event) {
        if (event.getEntityType().equals(EntityType.MINECART_TNT)
                && event.getEntity().getWorld().getEntitiesByClass(ExplosiveMinecart.class).size() > WORLD_MAXIMUM_TNT_LIMIT) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onTNTPrime(final TNTPrimeEvent event) {
        if (ThreadLocalRandom.current().nextBoolean()) event.setCancelled(true);
    }

    @EventHandler
    public void onEntitySpawn(final EntitySpawnEvent event) {
        if (!canSpawnEntity(event.getLocation().getWorld(), event.getEntityType())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onVehicleCreate(final VehicleCreateEvent event) {
        if (!canSpawnEntity(event.getVehicle().getWorld(), event.getVehicle().getType())) {
            event.setCancelled(true);
        }
    }
}
