package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class JsonHandler {

    public static String getStringAttribute(String json, String name) {
        try {
            JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
            String result = jsonObject.get(name).getAsString();
            return result;
        } catch (Exception e) {
            return "";
        }
    }

    public static Gson getGson() {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();

            Gson gson = builder.create();
            return gson;
        } catch (Exception e) {
            return null;
        }
    }

}
