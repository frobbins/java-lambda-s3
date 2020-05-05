package com.frank.aws.mylambdaproject.employees;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.frank.aws.mylambdaproject.Dessert;
import com.frank.aws.mylambdaproject.Teenager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class IcecreamArtist extends Teenager implements RequestHandler<Dessert, Dessert> {

    private String getIcecream() {
        JSONParser jsonParser = new JSONParser();
        String icecream = "";
        try {

            FileReader reader = new FileReader("parlor-inventory.json");
            Object object = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) object;
            String icecreamObj = (String) jsonObject.get("icecream");
            getLogger().log(icecreamObj);
            String[] icecreamArray = icecreamObj.split(",");
            getLogger().log(icecreamArray.toString());

            Random generator = new Random();
            int random = generator.nextInt(icecreamArray.length);
            icecream = icecreamArray[random];

        } catch (FileNotFoundException e) {
            getLogger().log("File not found.");
        } catch (IOException e) {
            getLogger().log("IOException.");
        } catch (org.json.simple.parser.ParseException e) {
            getLogger().log("Parse Exception.");
        }
        return icecream;
    }

    @Override
    public Dessert handleRequest(Dessert dessert, Context context) {
        try {
            setContext(context);
            List<String> items = new LinkedList<>();
            items.add(getIcecream());
            dessert.setIcecream(items);
            getLogger().log("Here are your items: \n" + items );
        } catch ( Exception ex ) {
            ex.printStackTrace();
            getLogger().log("Error: " + ex);
        }
        return dessert;
    }
}
