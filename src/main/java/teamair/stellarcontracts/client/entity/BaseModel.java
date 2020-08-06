package teamair.stellarcontracts.client.entity;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class BaseModel<T extends Entity> extends EntityModel<T> {
    protected final ModelRenderer model;

    public BaseModel() {
        this.model = new ModelRenderer(this);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.model.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    public static class ModelRenderer extends ModelPart {
        public ModelRenderer(Model model) {
            super(model);
        }

        public void setRotationPoint(float x, float y, float z) {
            this.pivotX = x;
            this.pivotY = y;
            this.pivotZ = z;
        }

        public void addBox(float x, float y, float z, float sizeX, float sizeY, float sizeZ, float extra, boolean mirror) {
            super.addCuboid(x, y, z, sizeX, sizeY, sizeZ, mirror);
        }

        @Override
        public ModelRenderer setTextureOffset(int textureOffsetU, int textureOffsetV) {
            super.setTextureOffset(textureOffsetU, textureOffsetV);
            return this;
        }
    }
}
