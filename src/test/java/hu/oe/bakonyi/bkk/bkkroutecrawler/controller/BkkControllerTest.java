package hu.oe.bakonyi.bkk.bkkroutecrawler.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import hu.oe.bakonyi.bkk.bkkroutecrawler.client.BkkRouteClient;
import hu.oe.bakonyi.bkk.bkkroutecrawler.entity.Routes;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.bkk.BkkData;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.route.BkkVeichleForRoute;
import hu.oe.bakonyi.bkk.bkkroutecrawler.model.trip.BkkTripDetails;
import hu.oe.bakonyi.bkk.bkkroutecrawler.repository.RouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.ResourceUtils;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;

@AutoConfigureWebTestClient(timeout = "36000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("integration")
public class BkkControllerTest {

    @Autowired
    private WebTestClient webApiClient;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    RouteRepository routeRepository;

    @MockBean
    BkkRouteClient routeClient;

    @BeforeEach
    public void init() throws IOException {
        BkkVeichleForRoute routeModel = mapper.readValue(ResourceUtils.getFile("classpath:VeichleForRouteIntegTest_ok.json"), BkkVeichleForRoute.class);
        BkkTripDetails tripDetails = mapper.readValue(ResourceUtils.getFile("classpath:TrpDetails_ok.json"), BkkTripDetails.class);

        Mockito.when(routeClient.getRoute(anyString(), anyInt(), anyString(), anyString(), anyString(), anyBoolean())).thenReturn(routeModel);
        Mockito.when(routeClient.getTrip(anyString(),anyInt(),anyString(),anyString(),anyString(),anyString(),anyBoolean(),anyString())).thenReturn(tripDetails);
    }

    @Test
    public void test_getBkkData_WithGoodParams() throws IOException {
        mapper.findAndRegisterModules();

        webApiClient.get()
                .uri(uriBuilder -> uriBuilder.path("/bkk/prod/route").queryParam("route", "BKK_0085").build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody(BkkData[].class)
                .consumeWith(((Consumer<EntityExchangeResult<BkkData[]>>) response -> {
                    BkkData[] body = response.getResponseBody();

                    assertNotNull(body, "Response body is null");
                    assertEquals(body.length,15);
                    assertEquals(HttpStatus.OK, response.getStatus());
                }));
    }

    @Test
    public void getPrediction_throwsException_onMissingOrBadPArameter() throws IOException {
        mapper.findAndRegisterModules();

        webApiClient.get()
                .uri(uriBuilder -> uriBuilder.path("/bkk/prod/route").queryParam("route", "Hetesbusz").build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody(String.class)
                .consumeWith(((Consumer<EntityExchangeResult<String>>) response -> {
                    assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
                }));

        webApiClient.get()
                .uri(uriBuilder -> uriBuilder.path("/bkk/prod/route").build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody(String.class)
                .consumeWith(((Consumer<EntityExchangeResult<String>>) response -> {
                    assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
                }));



    }

}
