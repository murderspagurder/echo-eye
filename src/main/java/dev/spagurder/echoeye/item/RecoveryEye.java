package dev.spagurder.echoeye.item;

import dev.spagurder.echoeye.core.EchoEyeUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class RecoveryEye extends Item {

    public RecoveryEye(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (!level.isClientSide) {
            player.getCooldowns().addCooldown(this, 100);
            if (EchoEyeUtil.teleportPlayerToLastDeath((ServerPlayer) player)) {
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                return InteractionResultHolder.success(stack);
            }
            return InteractionResultHolder.fail(stack);
        }
        return InteractionResultHolder.success(stack);
    }

}
