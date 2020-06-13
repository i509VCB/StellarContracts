package teamair.stellarcontracts.item;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import teamair.stellarcontracts.registry.StellarGUIs;
import teamair.stellarcontracts.registry.StellarItems;

public class CommunicatorItem extends Item {
    private boolean isContractActive;

    public CommunicatorItem(Settings settings) {
        super(settings.maxCount(1).rarity(Rarity.UNCOMMON).fireproof().group(StellarItems.STELLAR_ITEM_GROUP));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            ContainerProviderRegistry.INSTANCE.openContainer(StellarGUIs.COMMUNICATOR_CONTAINER, user, (buffer) -> {
                buffer.writeText(new TranslatableText(this.getTranslationKey()));
            });
        }
        return TypedActionResult.success(user.getMainHandStack());
    }

    @Override
    public boolean hasEnchantmentGlint(ItemStack stack) {
        return isContractActive;
    }
}
