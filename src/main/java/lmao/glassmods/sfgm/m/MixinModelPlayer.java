package lmao.glassmods.sfgm.m;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelPlayer.class)
public class MixinModelPlayer extends ModelBase {

    @Unique private ModelRenderer sfgm$breastBox;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void addBreastBox(float size, boolean smallArms, CallbackInfo c) {

        sfgm$breastBox = new ModelRenderer(this, 16, 20);

        sfgm$breastBox.addBox(-4F, -2F, -2F, 8, 4, 4, size);
        sfgm$breastBox.setRotationPoint(0, 2, -2F);

    }

    @Inject(method = "render", at = @At("RETURN"))
    public void renderBreastBoxOnPlayer(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, CallbackInfo c) {

        if (entity != Minecraft.getMinecraft().thePlayer) return;

        GlStateManager.pushMatrix();

        if (entity.isSneaking()) {
            GlStateManager.translate(0F, 0.3F, 0.1F);
            sfgm$breastBox.rotateAngleX = (float)Math.toRadians(80F);
        } else {
            GlStateManager.translate(0F, 0.1F, 0.02F);
            sfgm$breastBox.rotateAngleX = (float)Math.toRadians(45F);
        }

        sfgm$breastBox.render(scale);

        GlStateManager.popMatrix();
    }
}