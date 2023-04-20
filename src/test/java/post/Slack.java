package post;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.SlackMessagePojo;
import pojo.SlackResultsPojo;
import utils.PayloadUtils;

import java.util.Map;

public class Slack {
    @Test
    public void sendSlackMessageText(){
        //https://slack.com/api/chat.postMessage
        RestAssured.baseURI = "https://slack.com";
        RestAssured.basePath = "api/chat.postMessage";
        String msg = "Chynara: Hello channel from JavaCode";
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer xoxb-4349924244708-5140059154064-Gmd3isPGRJhfpYdpOnwL7MZm")
                .body(PayloadUtils.getSlackMessagePayload("Chynara: Hello channel from JavaCode"))
                .when().post()
                .then().statusCode(200)
                .extract().response();
        SlackResultsPojo resultsPojo = response.as(SlackResultsPojo.class);
        boolean isOk = resultsPojo.isOk();
//        SlackMessagePojo messageMap = resultsPojo.getMessage();
//        String actualMess = messageMap.getText();
        String actualMess = resultsPojo.getMessage().getText();
        Assert.assertTrue("Failed to send Slack message",isOk);
        Assert.assertEquals(msg, " "+ actualMess );

        JsonPath jsonPath= response.jsonPath();
        boolean isMessageSent= jsonPath.getBoolean("ok");
        Assert.assertTrue(isMessageSent);
        String text = jsonPath.getString("message.text");
        Assert.assertEquals(msg, text);
        String type = jsonPath.getString("message.blocks[0].type");
        Assert.assertEquals("rich_text", type);

    }
}
