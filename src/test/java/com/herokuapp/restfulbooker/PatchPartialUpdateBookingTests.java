package com.herokuapp.restfulbooker;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;

public class PatchPartialUpdateBookingTests extends BaseTest {

    @Test
    public void partialUpdateBookingTest(){
        JSONObject reqBody = new JSONObject();

        reqBody.put("firstname", "Oleg");
        reqBody.put("totalprice", 9999);

        JSONObject bookingdates = new JSONObject();

        bookingdates.put("checkin", "2022-05-15");
        bookingdates.put("checkout", "2022-05-20");
        reqBody.put("bookingdates", bookingdates);

        Response createResponse = createBooking();
        createResponse.prettyPrint();
        int bookingId = createResponse.jsonPath().getInt("bookingid");
        Response partialUpdateResponse = given(spec).auth().preemptive().basic("admin", "password123").contentType(ContentType.JSON).body(reqBody.toString()).
                patch("/booking/" + bookingId);

        partialUpdateResponse.prettyPrint();

        Assert.assertEquals(partialUpdateResponse.getStatusCode(), 200);
        //Verifying all fields with soft assertion
        SoftAssert softy = new SoftAssert();

        String actualLastName = partialUpdateResponse.jsonPath().getString("lastname");
        String actualFirstName = partialUpdateResponse.jsonPath().getString("firstname");
        int price = partialUpdateResponse.jsonPath().getInt("totalprice");
        boolean isDepositPaid = partialUpdateResponse.jsonPath().getBoolean("depositpaid");
        String actualCheckinDate = partialUpdateResponse.jsonPath().getString("bookingdates.checkin");
        String actualCheckoutDate = partialUpdateResponse.jsonPath().getString("bookingdates.checkout");
        String actualAdditionalNeeds = partialUpdateResponse.jsonPath().getString("additionalneeds");

        softy.assertEquals(actualFirstName, "Oleg", "firstname is not matching");
        softy.assertEquals(actualLastName, "Zengin", "lastname is not matching");
        softy.assertEquals(price, 199, "price is not correct");
        softy.assertTrue(isDepositPaid);
        softy.assertEquals(actualCheckinDate, "2022-02-02", "checkin date is not matching");
        softy.assertEquals(actualCheckoutDate, "2022-03-03", "checkout date is not matching");
        softy.assertEquals(actualAdditionalNeeds,"W");

        softy.assertAll();
    }
}
