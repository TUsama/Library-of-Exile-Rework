package com.robertx22.library_of_exile.utils;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CodecUtil
{
    public static <T, R> Codec<HashMap<T, R>> toHashMapCodec(Codec<T> key, Codec<R> value){
        return Codec.pair(key, value).listOf().xmap(x -> {
            HashMap<T, R> perkIntegerHashMap = new HashMap<>();
            for (Pair<T, R> perkIntegerPair : x) {
                perkIntegerHashMap.put(perkIntegerPair.getFirst(), perkIntegerPair.getSecond());
            }
            return perkIntegerHashMap;
        }, x ->{
            List<Pair<T, R>> list = new ArrayList<>();
            for (Map.Entry<T, R> perkIntegerEntry : x.entrySet()) {
                list.add(Pair.of(perkIntegerEntry.getKey(), perkIntegerEntry.getValue()));
            }
            return list;
        });
    }

    public static <T extends Enum<T>> Codec<T> getEnumCodec(Function<String, T> transformer){
        return Codec.STRING.xmap(transformer, Enum::toString);
    }

}
