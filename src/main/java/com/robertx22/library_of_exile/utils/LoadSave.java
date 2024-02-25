package com.robertx22.library_of_exile.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.minecraft.nbt.CompoundTag;


public class LoadSave {


    private static final Gson gson = new GsonBuilder().create();

    public static CompoundTag Save(Object obj, CompoundTag nbt, String loc) {


        if (nbt == null) {
            nbt = new CompoundTag();
        }

        String json = null;
        try {
            json = gson.toJson(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (json != null) {
            nbt.putString(loc, json);
        }

        return nbt;

    }

    public static <OBJ extends Object> OBJ Load(Class theclass, OBJ newobj, CompoundTag nbt, String loc) {

        OBJ o = null;
        if (nbt == null) {
            return null;
        }

        String json = nbt.getString(loc);

        if (json.isEmpty()) {
            return null;
        }

        try {
            o = (OBJ) gson.fromJson(json, theclass);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        }

        return o;

    }

}
