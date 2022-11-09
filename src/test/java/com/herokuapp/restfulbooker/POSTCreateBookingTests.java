package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class POSTCreateBookingTests extends BaseTest{

    @Test
    public void createBookingTest() {
        //POST
        //1.step create JSON Body

        /*
        {
    "firstname": "James",
    "lastname": "Brown",
    "totalprice": 111,
    "depositpaid": true,
    "bookingdates": {
        "checkin": "2018-01-01",
        "checkout": "2019-01-01"
    },
    "additionalneeds": "Breakfast"
}
         */
        //NOTE: You can use String but it is pain in the back. Instead use JSON OBJECT
        Response response = createBooking();
        response.prettyPrint();


        //Verifications
        Assert.assertEquals(response.getStatusCode(), 200);

        //Verify all fields with soft assertion
        SoftAssert softy = new SoftAssert();

        String actualLastName = response.jsonPath().getString("booking.lastname");
        String actualFirstName = response.jsonPath().getString("booking.firstname");
        int price = response.jsonPath().getInt("booking.totalprice");
        boolean isDepositPaid = response.jsonPath().getBoolean("booking.depositpaid");
        String actualCheckinDate = response.jsonPath().getString("booking.bookingdates.checkin");
        String actualCheckoutDate = response.jsonPath().getString("booking.bookingdates.checkout");
        String actualAdditionalNeeds = response.jsonPath().getString("booking.additionalneeds");

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
