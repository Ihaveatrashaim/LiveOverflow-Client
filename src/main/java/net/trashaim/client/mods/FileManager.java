package net.trashaim.client.mods;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import net.minecraft.client.MinecraftClient;
import net.trashaim.client.Client;
import net.trashaim.client.value.BooleanValue;
import net.trashaim.client.value.ModeValue;
import net.trashaim.client.value.NumberValue;
import net.trashaim.client.value.Value;

import java.io.*;

public class FileManager {

    private final File dir = new File(MinecraftClient.getInstance().runDirectory, "Client");

    private final File modules = new File(dir, "modules.json");

    private final Gson gson = new Gson();

    public FileManager() {
        dir.mkdirs();
    }

    public void saveModules() throws IOException {
        if(!modules.exists())
            modules.createNewFile();

        final JsonObject jsonObject = new JsonObject();

        for(final Module module : Client.instance.moduleManager.modules) {
            final JsonObject moduleJson = new JsonObject();

            moduleJson.addProperty("toggle", module.isToggle());

            for(final Value value : module.getValues()) {
                if(value instanceof NumberValue)
                    moduleJson.addProperty(value.getName(), (float) value.getObject());
                else if(value instanceof BooleanValue)
                    moduleJson.addProperty(value.getName(), (boolean) value.getObject());
                else if(value instanceof ModeValue)
                    moduleJson.addProperty(value.getName(), (String) value.getObject());
            }
            
            jsonObject.add(module.getName(), moduleJson);
        }

        final PrintWriter printWriter = new PrintWriter(modules);
        printWriter.println(gson.toJson(jsonObject));
        printWriter.flush();
        printWriter.close();
    }

    public void loadModules() throws IOException {
        if(!modules.exists()) {
            modules.createNewFile();
            saveModules();
            return;
        }

        final BufferedReader bufferedReader = new BufferedReader(new FileReader(modules));

        final JsonElement jsonElement = gson.fromJson(bufferedReader, JsonElement.class);

        if(jsonElement instanceof JsonNull)
            return;

        final JsonObject jsonObject = (JsonObject) jsonElement;

        for(final Module module : Client.instance.moduleManager.modules) {
            if(!jsonObject.has(module.getName()))
                continue;

            final JsonElement moduleElement = jsonObject.get(module.getName());

            if(moduleElement instanceof JsonNull)
                continue;

            final JsonObject moduleJson = (JsonObject) moduleElement;

            if(moduleJson.has("toggle"))
                module.setToggle(moduleJson.get("toggle").getAsBoolean());

            for(final Value value : module.getValues()) {
                if(!moduleJson.has(value.getName()))
                    continue;

                if(value instanceof NumberValue)
                    value.setObject(moduleJson.get(value.getName()).getAsFloat());
                else if(value instanceof BooleanValue)
                    value.setObject(moduleJson.get(value.getName()).getAsBoolean());
                else if(value instanceof ModeValue)
                	value.setObject(moduleJson.get(value.getName()).getAsString());
            }
        }
    }
}