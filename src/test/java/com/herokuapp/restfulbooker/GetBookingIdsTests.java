package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GetBookingIdsTests {

    @Test
    public void getBookingIdsWithoutFilterTest() {
        // Get response wist booking id
        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking");
        response.print();
        //verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        //Verify at least 1 booking id in response body
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertFalse(bookingIds.isEmpty(), "List of bookingIds is empty");
    }
}
