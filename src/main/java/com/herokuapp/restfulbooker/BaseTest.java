package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    RequestSpecification spec;
    @BeforeMethod
    public void setUp() {
        spec = new RequestSpecBuilder().setBaseUri("https://restful-booker.herokuapp.com").
                build();

    }

    protected Response createBooking() {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("firstname", "Ali");
        jsonBody.put("lastname", "Zengin");
        jsonBody.put("totalprice", 199);
        jsonBody.put("depositpaid", true);

        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2022-02-02");
        bookingDates.put("checkout", "2022-03-03");
        jsonBody.put("bookingdates", bookingDates);
        jsonBody.put("additionalneeds", "air conditioner");

        // response
        Response response = RestAssured.given(spec).contentType(ContentType.JSON).
                body(jsonBody.toString()).post("/booking");
        return response;
    }

    protected Response getBookingById(int bookingId) {
        Response response = RestAssured.given(spec).get("/booking/" + bookingId);
        return response;
    }

}
