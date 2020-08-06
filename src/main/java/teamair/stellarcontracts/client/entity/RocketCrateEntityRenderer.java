package teamair.stellarcontracts.client.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import teamair.stellarcontracts.StellarContracts;
import teamair.stellarcontracts.entity.RocketCrateEntity;

@Environment(EnvType.CLIENT)
public class RocketCrateEntityRenderer extends EntityRenderer<RocketCrateEntity> {
    public static final Identifier TEXTURE = StellarContracts.id("textures/entity/rocket_crate.png");
    private final RocketCrateEntityRenderer.Model model;

    public RocketCrateEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
        this.model = new Model();
    }

    @Override
    public Identifier getTexture(RocketCrateEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(RocketCrateEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180.0F));
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(entity.pitch));
        matrices.translate(0.5, -1.5, -0.5);

        final VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(TEXTURE));
        this.model.render(matrices, buffer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    static class Model extends BaseModel<RocketCrateEntity> {
        public Model() {
            this.textureWidth = 64;
            this.textureHeight = 64;

            this.model.setTextureSize(this.textureWidth, this.textureHeight);
            this.model.setRotationPoint(0.0F, 24.0F, 0.0F);
            this.model.setTextureOffset(0, 0).addBox(-14.0F, -13.0F, 2.0F, 12.0F, 11.0F, 12.0F, 0.0F, false);
            this.model.setTextureOffset(36, 0).addBox(-11.0F, -14.0F, 5.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);
            this.model.setTextureOffset(0, 23).addBox(-14.5F, -11.0F, 1.5F, 13.0F, 1.0F, 13.0F, 0.0F, false);
            this.model.setTextureOffset(0, 37).addBox(-14.5F, -8.0F, 1.5F, 13.0F, 1.0F, 13.0F, 0.0F, false);
            this.model.setTextureOffset(0, 51).addBox(-3.6F, -14.0F, 12.4F, 2.0F, 8.0F, 2.0F, 0.0F, false);
            this.model.setTextureOffset(0, 0).addBox(-14.4F, -14.0F, 12.4F, 2.0F, 8.0F, 2.0F, 0.0F, false);
            this.model.setTextureOffset(0, 23).addBox(-14.4F, -14.0F, 1.7F, 2.0F, 8.0F, 2.0F, 0.0F, false);
            this.model.setTextureOffset(0, 37).addBox(-3.6F, -14.0F, 1.6F, 2.0F, 8.0F, 2.0F, 0.0F, false);
            this.model.setTextureOffset(48, 49).addBox(-5.0F, -6.0F, 11.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);
            this.model.setTextureOffset(48, 7).addBox(-15.0F, -6.0F, 1.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);
            this.model.setTextureOffset(39, 39).addBox(-5.0F, -6.0F, 1.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);
            this.model.setTextureOffset(39, 23).addBox(-15.0F, -6.0F, 11.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);
        }
    }
}
