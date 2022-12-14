package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GetBookingIdsTests extends BaseTest{

    @Test
    public void getBookingIdsWithoutFilterTest() {
        // Get response wist booking id
        Response response = RestAssured.given(spec).get("/booking");
        response.prettyPrint();
        //verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        //Verify at least 1 booking id in response body
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        System.out.println(bookingIds.size());
        Assert.assertFalse(bookingIds.isEmpty(), "List of bookingIds is empty");
    }

    @Test
    public void getBookingIdsWithFilterTest(){
        //add query parameter to spec
        spec.queryParam("lastname", "Zengin");
//        spec.queryParam("firstname", "Ali");


        Response response = RestAssured.given(spec).get("/booking");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        System.out.println(bookingIds.size());
        Assert.assertFalse(bookingIds.isEmpty(), "List of bookingIds is empty");
    }
}
