package ru.vood.admplugin.infrastructure.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

@Component
public class GsonTune {

    private static Gson gson;

    private GsonTune() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting()
                .serializeNulls()
                .setDateFormat("yyyy.mm.dd ")
                .setVersion(1);
        gson = gsonBuilder.create();
    }

    public Gson getGson() {
        if (gson == null) {
            new GsonTune();
        }
        return gson;
    }
}
