package com.robertx22.library_of_exile.dimension.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.List;
import java.util.stream.Collectors;

public abstract class MapStructure<Map> {

    public abstract String guid();

    public abstract Map getMap(ChunkPos start);

    public abstract int getHeight();

    public BlockPos getSpawnTeleportPos(BlockPos pos) {
        BlockPos p = getStartChunkPos(pos).getMiddleBlockPosition(getHeight() + 5);
        p = new BlockPos(p.getX(), getHeight() + 5, p.getZ());
        return p;
    }

    protected abstract ChunkPos INTERNALgetStartChunkPos(ChunkPos cp);

    public ChunkPos getStartChunkPos(ChunkPos cp) {
        var start = INTERNALgetStartChunkPos(cp);
        var start2 = INTERNALgetStartChunkPos(start);

        if (!start.equals(start2)) {
            throw new RuntimeException(start.toString() + " and " + start2.toString() + " are different, meaning the getStartChunkPos method is failing to produce consistent results");
        }
        return start;
    }

    public ChunkPos getRelativeChunkPosFromStart(ChunkPos pos) {
        ChunkPos start = getStartChunkPos(pos);
        ChunkPos relative = new ChunkPos(pos.x - start.x, pos.z - start.z);
        return relative;
    }

    public ChunkPos getStartChunkPos(BlockPos pos) {
        var start = getStartChunkPos(new ChunkPos(pos));
        return start;
    }

    public List<Player> getAllPlayersInMap(Level world, BlockPos pos) {
        var start = getStartChunkPos(pos);
        return world.players().stream().filter(x -> {
            return getStartChunkPos(x.blockPosition()).equals(start);
        }).collect(Collectors.toList());
    }

    public abstract boolean generateInChunk(ServerLevelAccessor level, StructureTemplateManager man, ChunkPos cpos);

}
