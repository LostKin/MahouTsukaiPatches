package com.lostkin.mahoutsukaipatches.client;

import com.lostkin.mahoutsukaipatches.MahouTsukaiPatches;
import com.lostkin.mahoutsukaipatches.eyes.PlayerEyesProvider;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.concurrent.atomic.AtomicBoolean;

public class EyesHudOverlay {
    public static ResourceLocation OPEN_EYE = new ResourceLocation(MahouTsukaiPatches.MODID,
            "textures/eyes/eye_enabled_icon.png");

    public static ResourceLocation CLOSED_EYE = new ResourceLocation(MahouTsukaiPatches.MODID,
            "textures/eyes/eye_disabled_icon.png");

    public static final IGuiOverlay HUD_EYES= ((forgeGui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;

        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);

        if (ClientEyeData.getEyesTypeDefined()) {
            if (ClientEyeData.getEyesStatus()) {
                RenderSystem.setShaderTexture(0, OPEN_EYE);
            } else {
                RenderSystem.setShaderTexture(0, CLOSED_EYE);
            }
            GuiComponent.blit(poseStack, x - 94, y - 54, 0, 0, 12, 12, 12, 12);
        }
    });

}
