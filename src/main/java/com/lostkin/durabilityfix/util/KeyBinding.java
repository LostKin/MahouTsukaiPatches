package com.lostkin.durabilityfix.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {

    public static final String KEY_CATEGORY_MAGIC = "key.category.durabilityfix.mahoutsukaipatches";
    public static final String KEY_ACTIVATE_MYSTIC_EYES = "key.durabilityfix.activate_eyes";

    public static final KeyMapping EYES_KEY = new KeyMapping(KEY_ACTIVATE_MYSTIC_EYES, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X, KEY_CATEGORY_MAGIC);



}
