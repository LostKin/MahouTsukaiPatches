package com.lostkin.mahoutsukaipatches.client;

import com.lostkin.mahoutsukaipatches.MahouTsukaiPatches;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ProtectionHudOverlay {

    public static ResourceLocation PROTECTION_ACTIVE = new ResourceLocation(MahouTsukaiPatches.MODID,
            "textures/protection/protection_enabled_icon.png");

    public static ResourceLocation PROTECTION_INACTIVE = new ResourceLocation(MahouTsukaiPatches.MODID,
            "textures/protection/protection_disabled_icon.png");

    public static final IGuiOverlay HUD_EYES= ((forgeGui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;

        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);

        if (ClientProtectionData.getProtectionUnlocked()) {
            if (ClientProtectionData.getProtectionStatus()) {
                RenderSystem.setShaderTexture(0, PROTECTION_ACTIVE);
            } else {
                RenderSystem.setShaderTexture(0, PROTECTION_INACTIVE);
            }
            GuiComponent.blit(poseStack, x - 94 + 16, y - 54, 0, 0, 12, 12, 12, 12);
        }
    });

}
