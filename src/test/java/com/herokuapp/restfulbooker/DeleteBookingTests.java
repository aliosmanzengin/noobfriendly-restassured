package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteBookingTests extends BaseTest {

    @Test
    public void deleteBookingTest() {

        Response response = createBooking();
        int bookingId = response.jsonPath().getInt("bookingid");
        response.prettyPrint();
        System.out.println("========================================");

        Response deleteResponse = RestAssured.given(spec).auth().preemptive().basic("admin", "password123").
                delete("/booking/" + bookingId);
        deleteResponse.prettyPrint();

        for (int i = 100; i < 9211; i++) {
            Response del = RestAssured.given(spec).auth().preemptive().basic("admin", "password123").
                    delete("/booking/" + i);

            System.out.println(del.prettyPrint() + " - " + i);
        }

        Assert.assertEquals(response.getStatusCode(),200);
        getBookingById(bookingId).prettyPrint();
        Assert.assertEquals(getBookingById(bookingId).getBody().asString(),"Not Found");

    }

}
