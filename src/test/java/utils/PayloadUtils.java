package utils;

public class PayloadUtils {
   /* public static String getPetPayload(){
        String payload = "{\n" +
        "  \"id\": 18448,\n" +
        "  \"category\": {\n" +
        "    \"id\": 0,\n" +
        "    \"name\": \"string\"\n" +
        "  },\n" +
        "  \"name\": \"Tom\",\n" +
        "  \"photoUrls\": [\n" +
        "    \"string\"\n" +
        "  ],\n" +
        "  \"tags\": [\n" +
        "    {\n" +
        "      \"id\": 0,\n" +
        "      \"name\": \"string\"\n" +
        "    }\n" +
        "  ],\n" +
        "  \"status\": \"waiting\"\n" +
        "}";

        return payload;
    }

    */
   public static String getPetPayload(int petId, String petName, String petStatus) {
       String payload = "{\n" +
               "  \"id\": "+petId+ ",\n" +
               "  \"category\": {\n" +
               "    \"id\": 0,\n" +
               "    \"name\": \"string\"\n" +
               "  },\n" +
               "  \"name\": \""+petName+"\",\n" +
               "  \"photoUrls\": [\n" +
               "    \"string\"\n" +
               "  ],\n" +
               "  \"tags\": [\n" +
               "    {\n" +
               "      \"id\": 0,\n" +
               "      \"name\": \"string\"\n" +
               "    }\n" +
               "  ],\n" +
               "  \"status\": \""+petStatus+"\"\n" +
               "}";
       return payload;
   }
   public static String getSlackMessagePayload(String msgText){
       String msgPayload = "{\n" +
               "  \"channel\": \"C052ZQBE39D\",\n" +
               "  \"text\": \""+msgText+"\"\n" +
               "}";
       return msgPayload;
   }
   }

