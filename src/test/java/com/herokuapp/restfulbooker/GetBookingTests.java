package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class GetBookingTests {

    @Test
    public void getBookingByIdTest() {
        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking/4");
        response.prettyPrint();

        Assert.assertEquals(response.getStatusCode(), 200);

        //Verify all fields with soft assertion
        SoftAssert softy = new SoftAssert();

        String actualLastName = response.jsonPath().getString("lastname");
        String actualFirstName = response.jsonPath().getString("firstname");
        int price = response.jsonPath().getInt("totalprice");
        boolean isDepositPaid = response.jsonPath().getBoolean("depositpaid");
        String actualCheckinDate = response.jsonPath().getString("bookingdates.checkin");
        String actualCheckoutDate = response.jsonPath().getString("bookingdates.checkout");


        softy.assertEquals(actualFirstName, "James", "firstname is not matching");
        softy.assertEquals(actualLastName, "Brown", "lastname is not matching");
        softy.assertEquals(price, 111, "price is not correct");
        softy.assertTrue(isDepositPaid);
        softy.assertEquals(actualCheckinDate, "2018-01-01", "checkin date is not matching");
        softy.assertEquals(actualCheckoutDate, "2019-01-01", "checkout date is not matching");

        softy.assertAll();



    }
}
