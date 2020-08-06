package teamair.stellarcontracts.util;

import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public final class StellarUtilities {
    private StellarUtilities() {
    }

    /**
     * Generates a random value between -1 and 1
     */
    public static double centeredRandom() {
        return Math.random() * 2.0 - 1.0;
    }

    public static void readInventory(CompoundTag tag, String key, SimpleInventory inv) {
        ListTag list = tag.getList(key, 10);
        int size = Math.min(inv.size(), list.size());

        for (int i = 0; i < size; i++) {
            inv.setStack(i, ItemStack.fromTag((CompoundTag) list.get(i)));
        }
    }

    public static void writeInventory(CompoundTag tag, String key, SimpleInventory inv) {
        ListTag list = new ListTag();
        for (int i = 0; i < inv.size(); i++) {
            CompoundTag itemTag = new CompoundTag();
            inv.getStack(i).toTag(itemTag);
            list.addTag(i, itemTag);
        }
        tag.put(key, list);
    }

    public static void dropInventory(SimpleInventory inv, World world, Vec3d pos){
        for (ItemStack i : inv.clearToList()) {
            world.spawnEntity(new ItemEntity(world, pos.x, pos.y, pos.z, i));
        }
    }
}
