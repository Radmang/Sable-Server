package io.sable;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import io.sable.model.AttributesSerializable;

public class ServerAttributes extends AttributesSerializable {

    public static String getSaveFile() {
        return Server.getSaveDirectory() + "server_attributes.json";
    }

    public ServerAttributes() {
        super(getSaveFile());
    }

    @Override
    public Type getType() {
        return new TypeToken<ServerAttributes>() {}.getType();
    }
}
