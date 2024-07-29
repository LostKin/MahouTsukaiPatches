package com.lostkin.mahoutsukaipatches.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {

    public static final String KEY_CATEGORY_MAGIC = "key.category.mahoutsukaipatches";
    public static final String KEY_ACTIVATE_MYSTIC_EYES = "key.mahoutsukaipatches.activate_eyes";
    public static final String KEY_ACTIVATE_PROTECTION = "key.mahoutsukaipatches.activate_protection";

    public static final KeyMapping EYES_KEY = new KeyMapping(KEY_ACTIVATE_MYSTIC_EYES, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X, KEY_CATEGORY_MAGIC);
    public static final KeyMapping PROTECTION_KEY = new KeyMapping(KEY_ACTIVATE_PROTECTION, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H, KEY_CATEGORY_MAGIC);



}
