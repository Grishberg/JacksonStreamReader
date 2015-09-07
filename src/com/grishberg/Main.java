package com.grishberg;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.grishberg.common.IoHelper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Main {


    public static void main(String[] args) {
	// write your code here
        String cwd = IoHelper.getCwd();
        String json = IoHelper.openFile(cwd,"in.txt");
        parse(json);
    }

    public static void parse(String json){
        // get an instance of the json parser from the json factory
        JsonFactory factory = new JsonFactory();
        InputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        try {
            JsonParser parser = factory.createJsonParser(stream);

            // continue parsing the token till the end of input is reached

            while (!parser.isClosed()) {
                // get the token
                JsonToken token = parser.nextToken();
                // if its the last token then we are done
                if (token == null)
                    break;
                int valueInt = 0;
                float valueFloat = 0.0f;
                String name = "";
                String strValue = "";
                switch (token){
                    case START_OBJECT:
                        name = parser.getCurrentName();
                        if(name == null){
                            System.out.println("START_OBJECT root");
                        }else {
                            System.out.println("START_OBJECT " + name);
                        }
                        break;
                    case END_OBJECT:
                        name = parser.getCurrentName();
                        System.out.println("END_OBJECT "+name);
                        break;
                    case START_ARRAY:
                        System.out.println("START_ARRAY");
                        break;
                    case END_ARRAY:
                        System.out.println("END_ARRAY");
                        break;
                    case FIELD_NAME:
                        System.out.println("FIELD_NAME");
                        break;
                    case VALUE_STRING:
                        strValue = parser.getText();
                        System.out.println("VALUE_STRING = "+strValue);
                        break;
                    case VALUE_NULL:
                        System.out.println("VALUE_NULL");
                        break;
                    case VALUE_NUMBER_FLOAT:
                        valueFloat = parser.getFloatValue();
                        System.out.println("VALUE_NUMBER_FLOAT = "+valueFloat);
                        break;
                    case VALUE_NUMBER_INT:
                        valueInt = parser.getValueAsInt();
                        System.out.println("VALUE_NUMBER_INT = "+valueInt);
                        break;

                }
                // we want to look for a field that says dataset

                if (JsonToken.FIELD_NAME.equals(token) && "dataset".equals(parser.getCurrentName())) {
                    // we are entering the datasets now. The first token should be
                    // start of array
                    token = parser.nextToken();
                    if (!JsonToken.START_ARRAY.equals(token)) {
                        // bail out
                        break;
                    }
                    // each element of the array is an album so the next token
                    // should be {
                    token = parser.nextToken();
                    if (!JsonToken.START_OBJECT.equals(token)) {
                        break;
                    }
                    // we are now looking for a field that says "album_title". We
                    // continue looking till we find all such fields. This is
                    // probably not a best way to parse this json, but this will
                    // suffice for this example.
                    while (true) {
                        token = parser.nextToken();
                        if (token == null)
                            break;
                        if (JsonToken.FIELD_NAME.equals(token) && "album_title".equals(parser.getCurrentName())) {
                            token = parser.nextToken();
                            System.out.println(parser.getText());
                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
