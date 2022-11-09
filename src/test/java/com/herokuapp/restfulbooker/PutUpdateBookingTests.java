package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PutUpdateBookingTests extends BaseTest{

    @Test
    public void updateBookingTest() {
        //at first, creating a booking to update it later on
        Response responseCreate = createBooking();
        responseCreate.prettyPrint();

        //getting booking id to use it
        // when doing update operation
        int id = responseCreate.jsonPath().getInt("bookingid");

        //Creating JSON Body
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("firstname", "Ewelina");
        jsonBody.put("lastname", "Stawrz");
        jsonBody.put("totalprice", 149);
        jsonBody.put("depositpaid", true);

        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2022-02-02");
        bookingDates.put("checkout", "2022-03-03");
        jsonBody.put("bookingdates", bookingDates);
        jsonBody.put("additionalneeds", "air conditioner");

        // Update the booking with PUT req
        Response responseUpdate = RestAssured.given(spec).auth().preemptive().basic("admin", "password123").contentType(ContentType.JSON).
                body(jsonBody.toString()).put("/booking/" + id);
        responseUpdate.prettyPrint();
        //Verifications
        Assert.assertEquals(responseUpdate.getStatusCode(), 200);
        //Verifying all fields with soft assertion
        SoftAssert softy = new SoftAssert();

        String actualLastName = responseUpdate.jsonPath().getString("lastname");
        String actualFirstName = responseUpdate.jsonPath().getString("firstname");
        int price = responseUpdate.jsonPath().getInt("totalprice");
        boolean isDepositPaid = responseUpdate.jsonPath().getBoolean("depositpaid");
        String actualCheckinDate = responseUpdate.jsonPath().getString("bookingdates.checkin");
        String actualCheckoutDate = responseUpdate.jsonPath().getString("bookingdates.checkout");
        String actualAdditionalNeeds = responseUpdate.jsonPath().getString("additionalneeds");

        softy.assertEquals(actualFirstName, "Ali", "firstname is not matching");
        softy.assertEquals(actualLastName, "Zengin", "lastname is not matching");
        softy.assertEquals(price, 199, "price is not correct");
        softy.assertTrue(isDepositPaid);
        softy.assertEquals(actualCheckinDate, "2022-02-02", "checkin date is not matching");
        softy.assertEquals(actualCheckoutDate, "2022-03-03", "checkout date is not matching");
        softy.assertEquals(actualAdditionalNeeds,"W");

        softy.assertAll();
    }
}
