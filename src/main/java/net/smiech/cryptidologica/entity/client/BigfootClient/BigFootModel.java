package net.smiech.cryptidologica.entity.client.BigfootClient;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class BigFootModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart Bigfoot;
	private final ModelPart Body;
	private final ModelPart Arm_R;
	private final ModelPart Arm_L;
	private final ModelPart head;
	private final ModelPart Eyes;
	private final ModelPart Leg_L;
	private final ModelPart Toe_L;
	private final ModelPart Leg_R;
	private final ModelPart Toe_R;

	public BigFootModel(ModelPart root) {
		this.Bigfoot = root.getChild("Bigfoot");
		this.Body = this.Bigfoot.getChild("Body");
		this.Arm_R = this.Body.getChild("Arm_R");
		this.Arm_L = this.Body.getChild("Arm_L");
		this.head = this.Body.getChild("head");
		this.Eyes = this.head.getChild("Eyes");
		this.Leg_L = this.Bigfoot.getChild("Leg_L");
		this.Toe_L = this.Leg_L.getChild("Toe_L");
		this.Leg_R = this.Bigfoot.getChild("Leg_R");
		this.Toe_R = this.Leg_R.getChild("Toe_R");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Bigfoot = partdefinition.addOrReplaceChild("Bigfoot", CubeListBuilder.create(), PartPose.offset(0.0F, 11.75F, 0.75F));

		PartDefinition Body = Bigfoot.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 15).addBox(-4.0F, -11.75F, -2.75F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(42, 0).addBox(-4.0F, -12.0F, -2.5F, 8.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(26, 0).addBox(-4.0F, -12.0F, 1.5F, 8.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Arm_R = Body.addOrReplaceChild("Arm_R", CubeListBuilder.create().texOffs(50, 20).addBox(-4.0F, 11.0F, 2.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 31).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(16, 48).addBox(-4.25F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-4.0F, -9.75F, -0.75F));

		PartDefinition Arm_L = Body.addOrReplaceChild("Arm_L", CubeListBuilder.create().texOffs(24, 29).addBox(0.0F, -2.0F, -2.0F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(32, 48).addBox(0.25F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
		.texOffs(50, 16).addBox(0.0F, 11.0F, 2.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -9.75F, -0.75F));

		PartDefinition head = Body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(24, 15).addBox(-3.0F, -6.0F, -6.0F, 6.0F, 7.0F, 7.0F, new CubeDeformation(0.5F))
		.texOffs(0, 0).addBox(-3.0F, -6.0F, -6.0F, 6.0F, 8.0F, 7.0F, new CubeDeformation(0.25F))
		.texOffs(0, 0).addBox(-3.0F, -8.0F, -6.0F, 6.0F, 8.0F, 7.0F, new CubeDeformation(-0.25F))
		.texOffs(26, 12).addBox(-3.0F, -3.8F, -6.3F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, -11.75F, -0.75F));

		PartDefinition nose_r1 = head.addOrReplaceChild("nose_r1", CubeListBuilder.create().texOffs(16, 37).addBox(-1.0F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.5F, -2.05F, -6.05F, -0.1658F, 0.0F, 0.0F));

		PartDefinition jaw_r1 = head.addOrReplaceChild("jaw_r1", CubeListBuilder.create().texOffs(16, 31).addBox(-2.0F, -2.5F, -0.15F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.25F, -7.0F, 0.0F, -0.0087F, 1.5708F));

		PartDefinition Eyes = head.addOrReplaceChild("Eyes", CubeListBuilder.create().texOffs(26, 14).addBox(1.0F, -4.7F, -4.31F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(28, 14).addBox(-2.0F, -4.7F, -4.31F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -2.0F));

		PartDefinition Leg_L = Bigfoot.addOrReplaceChild("Leg_L", CubeListBuilder.create().texOffs(40, 29).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.25F, -0.75F));

		PartDefinition Toe_L = Leg_L.addOrReplaceChild("Toe_L", CubeListBuilder.create().texOffs(42, 4).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, -2.0F));

		PartDefinition Leg_R = Bigfoot.addOrReplaceChild("Leg_R", CubeListBuilder.create().texOffs(48, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 0.25F, -0.75F));

		PartDefinition Toe_R = Leg_R.addOrReplaceChild("Toe_R", CubeListBuilder.create().texOffs(45, 10).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, -2.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Bigfoot.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return Bigfoot;
	}
}