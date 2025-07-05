package dev.spagurder.echoeye.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {

    public static final Block ECHO_DEEPSLATE = new Block(
            BlockBehaviour.Properties.of()
                    .destroyTime(3.0f)
                    .explosionResistance(6.0f)
                    .lightLevel(state -> 0)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE)
    );

}
