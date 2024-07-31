package com.lostkin.mahoutsukaipatches.mixin.caliburn;


import io.netty.util.internal.ConcurrentSet;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import stepsword.mahoutsukai.MahouTsukaiMod;
import stepsword.mahoutsukai.advancements.ModTriggers;
import stepsword.mahoutsukai.capability.caliburn.ICaliburnMahou;
import stepsword.mahoutsukai.capability.mahou.PlayerManaManager;
import stepsword.mahoutsukai.config.MTConfig;
import stepsword.mahoutsukai.effects.projection.PowerConsolidationSpellEffect;
import stepsword.mahoutsukai.entity.WeaponProjectileEntity;
import stepsword.mahoutsukai.handlers.ServerHandler;
import stepsword.mahoutsukai.item.ModItems;
import stepsword.mahoutsukai.item.spells.projection.PowerConsolidation.Caliburn;
import stepsword.mahoutsukai.util.PlayerHelp;
import stepsword.mahoutsukai.util.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static stepsword.mahoutsukai.effects.projection.PowerConsolidationSpellEffect.*;

@Mixin(PowerConsolidationSpellEffect.class)
public class PowerConsolidationSpellMixin {

    /**
     * @LostKin
     * @I cannot put stuff directly where i need to - which is to carry over the NBT tag
     */
    @Overwrite(remap = false)
    private static void powerConsolidationWorldTick(final TickEvent.LevelTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.level.isClientSide && ServerHandler.tickCounter % (long) MTConfig.POWER_CONSOLIDATION_LAKE_CYCLE == 0L) {
            Entity failed = null;

            try {
                final int manaCost = MTConfig.POWER_CONSOLIDATION_SWORD_MANA_COST;
                double nerfFactor = MTConfig.POWER_CONSOLIDATION_NERF_FACTOR;
                Iterator<Entity> iter = ((ServerLevel)event.level).getEntities().getAll().iterator();
                final List<ItemEntity> toDelete = new ArrayList();
                final List<WeaponProjectileEntity> toSpawn = new ArrayList();

                while(iter.hasNext()) {
                    Entity e = (Entity)iter.next();
                    if (e instanceof ItemEntity) {
                        ItemEntity item = (ItemEntity)e;
                        if (item.getItem().getItem() instanceof SwordItem) {
                            boolean inMurkyWater = Utils.isInMurkyWater(e);
                            boolean isAllowed = isItemAllowed(item.getItem());
                            boolean isEnchanted = item.getItem().isEnchanted();
                            boolean hasOwner = item.getThrower() != null;
                            if (!hasOwner && inMurkyWater) {
                                PlayerHelp.sendHelpMessageNearby(e.blockPosition(), e.getLevel(), PlayerHelp.Message.CALIBURN_NO_OWNER);
                            }

                            if (hasOwner && inMurkyWater) {
                                Player player = event.level.getPlayerByUUID(item.getThrower());
                                if (player != null) {
                                    if (isAllowed) {
                                        if (isEnchanted) {
                                            ConcurrentSet<BlockPos> connected = new ConcurrentSet();
                                            boolean lt = lakeThreshhold(item.blockPosition(), connected, event.level);
                                            if (lt) {
                                                if (PlayerManaManager.hasMana(player, manaCost)) {
                                                    BlockPos p = findCenter(connected);
                                                    if (!Utils.isBlockAir(event.level, p)) {
                                                        p = p.below();
                                                    }

                                                    ItemStack stack = new ItemStack((ItemLike) ModItems.caliburn.get());
                                                    ICaliburnMahou mahou = Utils.getCaliburnMahou(stack);
                                                    if (mahou != null) {
                                                        double lb = getLimitBreakInArea(item);
                                                        if (item.getItem().getItem() instanceof Caliburn) {
                                                            ICaliburnMahou orig = Utils.getCaliburnMahou(item.getItem());
                                                            if (orig != null) {
                                                                lb = Math.max(orig.getInnateCap(), lb);
                                                            }

                                                            if (item.getItem().hasCustomHoverName()) {
                                                                Component name = item.getItem().getHoverName();
                                                                stack.setHoverName(name);
                                                            }
                                                        }

                                                        mahou.setInnateCap(lb);
                                                        float damage = Caliburn.simulateHit(item.getItem(), event.level);
                                                        ItemStack stackCopy = item.getItem().copy();
                                                        ListTag tagList = stackCopy.getEnchantmentTags();
                                                        tagList.clear();
                                                        float damageWithoutEnchant = Caliburn.simulateHit(stackCopy, event.level);
                                                        damage = (float)((double)damage - nerfFactor * (double)(damage - damageWithoutEnchant));
                                                        --damage;
                                                        mahou.setAttackDamage(Math.min((float)MTConfig.POWER_CONSOLIDATION_ATTACK_CAP, damage));
                                                        Caliburn.setattacktonbt(stack, event.level);
                                                    }

                                                    Utils.debug("Advancement Time");
                                                    ModTriggers.SWORD_IN_THE_LAKE.trigger((ServerPlayer)player);

                                                    CompoundTag sourceTag = item.serializeNBT();
                                                    CompoundTag resultTag = stack.serializeNBT();


                                                    if (sourceTag.contains("Unbreakable")) {
                                                        resultTag.putBoolean("Unbreakable", true);
                                                    }

                                                    stack.deserializeNBT(resultTag);

                                                    WeaponProjectileEntity wpe = new WeaponProjectileEntity(event.level, (double)p.getX(), (double)p.getY(), (double)p.getZ(), stack);
                                                    wpe.setDeltaMovement(0.0, -1.0, 0.0);
                                                    wpe.setOwner(player);
                                                    toSpawn.add(wpe);
                                                    toDelete.add(item);
                                                } else {
                                                    PlayerHelp.sendHelpMessage(player, PlayerHelp.Message.NOT_ENOUGH_MANA);
                                                }
                                            } else {
                                                PlayerHelp.sendHelpMessage(player, PlayerHelp.Message.CALIBURN_LAKE_TOO_SMALL);
                                            }
                                        } else {
                                            PlayerHelp.sendHelpMessage(player, PlayerHelp.Message.CALIBURN_NOT_ENCHANTED);
                                        }
                                    } else {
                                        PlayerHelp.sendHelpMessage(player, PlayerHelp.Message.CALIBURN_SWORD_BANNED);
                                    }
                                }
                            }
                        }

                        MahouTsukaiMod.jousting.powerConsolidation(event, item, toDelete, toSpawn, manaCost);
                    }
                }

                if (toDelete.size() > 0) {
                    ((ServerLevel)event.level).getServer().execute(new Runnable() {
                        public void run() {
                            for(int i = 0; i < toDelete.size(); ++i) {
                                ItemEntity e = (ItemEntity)toDelete.get(i);
                                boolean var10000 = e.isAlive();
                                Utils.debug("Item alive:" + var10000 + "... Thrower:" + e.getThrower());
                                if (e.isAlive() && e.getThrower() != null && i < toSpawn.size() && PlayerManaManager.drainMana(event.level.getPlayerByUUID(e.getThrower()), manaCost, false, false) == manaCost) {
                                    Utils.debug("Mana charged and item spawning.");
                                    event.level.addFreshEntity((Entity)toSpawn.get(i));
                                    e.discard();
                                }
                            }

                        }
                    });
                }
            } catch (Exception var26) {
                Exception e = var26;
                Utils.err(e.toString());
                if (failed != null) {
                    PlayerHelp.sendHelpMessageNearby(((Entity)failed).blockPosition(), ((Entity)failed).level, PlayerHelp.Message.CALIBURN_INTERNAL_ERROR);
                }
            }
        }
    }

}