package ru.vood.admplugin.infrastructure.gson.intf;

import com.google.gson.Gson;

import java.io.Serializable;


public interface GSonRepository extends Serializable {

    Gson getGson();
}
