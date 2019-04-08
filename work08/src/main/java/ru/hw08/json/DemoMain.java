package ru.hw08.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.hw08.json.json.JSONSerializer;
import ru.hw08.json.json.JSONSerializerImp;
import test.dwarfs.BandUtil;
import test.dwarfs.DwarvesBand;

public class DemoMain {
    public static void main(String[] args) {
        DwarvesBand band = BandUtil.createBand();
        Object object = null;
        JSONSerializer serializer = new JSONSerializerImp();
        String sJson = serializer.toJSON(band);
        System.out.println(sJson);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(band);
       System.out.println(json);
    }
}
