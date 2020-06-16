package teamair.stellarcontracts.client.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import spinnery.client.render.BaseRenderer;
import spinnery.common.container.BaseContainer;
import spinnery.widget.WSlot;
import spinnery.widget.api.Position;
import spinnery.widget.api.Size;
import spinnery.widget.api.WModifiableCollection;
import teamair.stellarcontracts.StellarContracts;

import java.util.Collection;
import java.util.HashSet;

public class WTexturedSlot extends WSlot {
    private static final Identifier SLOT_TEXTURE = StellarContracts.id("textures/gui/slot.png");
    private Identifier texture;

    public Identifier getTexture() {
        return texture;
    }

    public WTexturedSlot setTexture(Identifier texture) {
        this.texture = texture;
        return this;
    }

    @Environment(EnvType.CLIENT)
    public static Collection<WTexturedSlot> addTArray(Position position, Size size, WModifiableCollection parent, int slotNumber, int inventoryNumber, int arrayWidth, int arrayHeight) {
        Collection<WTexturedSlot> set = new HashSet<>();
        for (int y = 0; y < arrayHeight; ++y) {
            for (int x = 0; x < arrayWidth; ++x) {
                set.add(parent.createChild(WTexturedSlot::new, position.add(size.getWidth() * x, size.getHeight() * y, 0), size)
                    .setTexture(SLOT_TEXTURE)
                    .setSlotNumber(slotNumber + y * arrayWidth + x)
                    .setInventoryNumber(inventoryNumber));
            }
        }
        return set;
    }

    @Environment(EnvType.CLIENT)
    public static Collection<WTexturedSlot> addTPlayerInventory(Position position, Size size, WModifiableCollection parent) {
        Collection<WTexturedSlot> set = addTArray(position, size, parent, 9, BaseContainer.PLAYER_INVENTORY, 9, 3);
        set.addAll(addTArray(position.add(0, size.getHeight() * 3 + 4, 0), size, parent, 0, BaseContainer.PLAYER_INVENTORY, 9, 1));
        return set;
    }

    @Override
    public void draw(MatrixStack matrices, VertexConsumerProvider.Immediate provider) {
        if (isHidden()) {
            return;
        }

        int x = (int) getX();
        int y = (int) getY();
        int z = (int) getZ();

        int sX = (int) getWidth();
        int sY = (int) getHeight();

        BaseRenderer.getTextureManager().bindTexture(getTexture());
        DrawableHelper.drawTexture(matrices, x, y, sX, sY, 0, 0, sX, sY, sX, sY);

        if (hasPreviewTexture()) {
            BaseRenderer.drawTexturedQuad(matrices, provider, x + 1, y + 1, z, sX - 2, sY - 2, getPreviewTexture());
        }

        ItemStack stackA = getPreviewStack().isEmpty() ? getStack() : getPreviewStack();

        BaseRenderer.getItemRenderer().renderGuiItemIcon(stackA, (int) ((1 + x) + ((sX - 18) / 2)), (int) ((1 + y) + ((sY - 18) / 2)));
        BaseRenderer.getItemRenderer().renderGuiItemOverlay(MinecraftClient.getInstance().textRenderer, stackA, (int) ((1 + x) + ((sX - 18) / 2)), (int) ((1 + y) + ((sY - 18) / 2)), stackA.getCount() == 1 ? "" : withSuffix(stackA.getCount()));

        if (isFocused()) {
            BaseRenderer.drawQuad(matrices, provider, x + 1, y + 1, z + 1, sX - 2, sY - 2, getStyle().asColor("overlay"));
        }
    }

    @Environment(EnvType.CLIENT)
    private static String withSuffix(long value) {
        if (value < 1000) return "" + value;
        int exp = (int) (Math.log(value) / Math.log(1000));
        return String.format("%.1f%c", value / Math.pow(1000, exp), "KMGTPE".charAt(exp - 1));
    }
}
