package teamair.stellarcontracts.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import teamair.stellarcontracts.registry.StellarItems;
import teamair.stellarcontracts.screenhandler.CommunicatorScreenHandler;

public class CommunicatorItem extends Item implements ExtendedScreenHandlerFactory {
    private static final Text SCREEN_NAME = new TranslatableText("stellarcontracts.item.communicator");
    // FIXME: Check using packet sent to client, not this lol
    private boolean isContractActive;

    public CommunicatorItem() {
        super(new Item.Settings()
                .maxCount(1)
                .rarity(Rarity.UNCOMMON)
                .fireproof()
                .group(StellarItems.STELLAR_ITEM_GROUP)
        );
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            user.openHandledScreen(this);
            return TypedActionResult.success(user.getMainHandStack());
        }

        return super.use(world, user, hand);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        // Wrap it in a logical client call
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            return this.isContractActive();
        }

        return false;
    }

    @Environment(EnvType.CLIENT)
    private boolean isContractActive() {
        return this.isContractActive; // TODO: Check contract activity status client has recieved
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CommunicatorScreenHandler(syncId, playerInventory);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        // TODO: Write contract info
    }

    @Override
    public Text getDisplayName() {
        return CommunicatorItem.SCREEN_NAME;
    }
}
