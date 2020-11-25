package stellarcontracts.client.entity;

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
import net.minecraft.util.math.MathHelper;

import stellarcontracts.StellarContracts;
import stellarcontracts.entity.RocketMk1Entity;

@Environment(EnvType.CLIENT)
public class RocketMk1EntityRenderer extends EntityRenderer<RocketMk1Entity> {
	private static final Identifier TEXTURE = StellarContracts.id("textures/entity/rocket/mk1.png");
	private final RocketMk1EntityRenderer.Model model;

	public RocketMk1EntityRenderer(EntityRenderDispatcher dispatcher) {
		super(dispatcher);
		this.model = new Model();
	}

	@Override
	public Identifier getTexture(RocketMk1Entity entity) {
		return RocketMk1EntityRenderer.TEXTURE;
	}

	@Override
	public void render(RocketMk1Entity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		matrices.push();

		matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180.0F));
		matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(entity.pitch));

		float wobble = (float) entity.getDamageWobbleTicks() - tickDelta;
		float wobbleStrength = entity.getDamageWobbleStrength() - tickDelta;

		if (wobbleStrength < 0.0F) {
			wobbleStrength = 0.0F;
		}

		if (wobble > 0.0F) {
			matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(((MathHelper.sin(wobble) * wobble * wobbleStrength) / 10.0F) * (float) entity.getDamageWobbleSide()));
		}

		final VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(RocketMk1EntityRenderer.TEXTURE));
		this.model.render(matrices, buffer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

		matrices.pop();
		super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
	}

	static class Model extends BaseModel<RocketMk1Entity> {
		Model() {
			this.textureWidth = 64;
			this.textureHeight = 64;

			this.model.setTextureSize(this.textureWidth, this.textureHeight);
			this.model.setRotationPoint(0.0F, 0.7854F, 0.0F);
			this.model.setTextureOffset(0, 49).addCuboid(-3.0F, 8.0F - 21.0F, -3.0F, 6.0F, 4.0F, 6.0F, 0.0F, false);
			this.model.setTextureOffset(0, 33).addCuboid(-4.0F, 12.0F - 21.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
			this.model.setTextureOffset(24, 49).addCuboid(-1.0F, -28.0F - 21.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
			this.model.setTextureOffset(0, 0).addCuboid(-4.5F, -16.0F - 21.0F, -4.5F, 9.0F, 24.0F, 9.0F, 0.0F, false);
			this.model.setTextureOffset(32, 33).addCuboid(-3.0F, -23.0F - 21.0F, -3.0F, 6.0F, 7.0F, 6.0F, 0.0F, false);
		}
	}
}
