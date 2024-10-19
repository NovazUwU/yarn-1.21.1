/*
 * Decompiled with CFR 0.2.2 (FabricMC 7c48b8c4).
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package net.minecraft.client.gui.screen.ingame;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.HorseScreenHandler;
import net.minecraft.util.Identifier;

@Environment(value=EnvType.CLIENT)
public class HorseScreen
extends HandledScreen<HorseScreenHandler> {
    private static final Identifier CHEST_SLOTS_TEXTURE = Identifier.ofVanilla("container/horse/chest_slots");
    private static final Identifier SADDLE_SLOT_TEXTURE = Identifier.ofVanilla("container/horse/saddle_slot");
    private static final Identifier LLAMA_ARMOR_SLOT_TEXTURE = Identifier.ofVanilla("container/horse/llama_armor_slot");
    private static final Identifier ARMOR_SLOT_TEXTURE = Identifier.ofVanilla("container/horse/armor_slot");
    private static final Identifier TEXTURE = Identifier.ofVanilla("textures/gui/container/horse.png");
    private final AbstractHorseEntity entity;
    private final int slotColumnCount;
    private float mouseX;
    private float mouseY;

    public HorseScreen(HorseScreenHandler handler, PlayerInventory inventory, AbstractHorseEntity entity, int slotColumnCount) {
        super(handler, inventory, entity.getDisplayName());
        this.entity = entity;
        this.slotColumnCount = slotColumnCount;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int k = (this.width - this.backgroundWidth) / 2;
        int l = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, k, l, 0, 0, this.backgroundWidth, this.backgroundHeight);
        if (this.slotColumnCount > 0) {
            context.drawGuiTexture(CHEST_SLOTS_TEXTURE, 90, 54, 0, 0, k + 79, l + 17, this.slotColumnCount * 18, 54);
        }
        if (this.entity.canBeSaddled()) {
            context.drawGuiTexture(SADDLE_SLOT_TEXTURE, k + 7, l + 35 - 18, 18, 18);
        }
        if (this.entity.canUseSlot(EquipmentSlot.BODY)) {
            if (this.entity instanceof LlamaEntity) {
                context.drawGuiTexture(LLAMA_ARMOR_SLOT_TEXTURE, k + 7, l + 35, 18, 18);
            } else {
                context.drawGuiTexture(ARMOR_SLOT_TEXTURE, k + 7, l + 35, 18, 18);
            }
        }
        InventoryScreen.drawEntity(context, k + 26, l + 18, k + 78, l + 70, 17, 0.25f, this.mouseX, this.mouseY, this.entity);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }
}

