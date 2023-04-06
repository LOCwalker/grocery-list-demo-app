package com.example.grocerylist.systemtests;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.stereotype.Component;

@Component
public class MealDbFaker {

    public void fakeArrabiata() {
        WireMock.stubFor(
                WireMock.get("/1/search.php?s=Arrabiata")
                        .willReturn(WireMock.aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBodyFile("arrabiata.json")
                        )
        );
    }
}
