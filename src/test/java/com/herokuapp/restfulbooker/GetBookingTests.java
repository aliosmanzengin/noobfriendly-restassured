package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class GetBookingTests extends BaseTest{

    @Test
    public void getBookingByIdTest() {
        Response responseCreate = createBooking();
        responseCreate.prettyPrint();

        //getting booking id to use it
        // when doing update operation
        int id = responseCreate.jsonPath().getInt("bookingid");

        //Set path parameter
        spec.pathParam("bookingId", id);

        Response response = RestAssured.given().spec(spec).get("/booking/{bookingId}");
        response.prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 200);

        String actualLastName = response.jsonPath().getString("lastname");
        String actualFirstName = response.jsonPath().getString("firstname");
        int price = response.jsonPath().getInt("totalprice");
        boolean isDepositPaid = response.jsonPath().getBoolean("depositpaid");
        String actualCheckinDate = response.jsonPath().getString("bookingdates.checkin");
        String actualCheckoutDate = response.jsonPath().getString("bookingdates.checkout");
        String actualAdditionalNeeds = response.jsonPath().getString("additionalneeds");

        //Verify all fields with soft assertion
        SoftAssert softy = new SoftAssert();
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
