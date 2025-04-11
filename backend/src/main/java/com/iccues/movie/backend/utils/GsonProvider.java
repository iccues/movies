package com.iccues.movie.backend.utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class GsonProvider {
    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(
                    LocalDateTime.class,
                    (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.toString())
            )
            .registerTypeAdapter(
                    LocalDateTime.class,
                    (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString())
            )
            .create();
}
