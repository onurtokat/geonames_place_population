package com.onurtokat.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onurtokat.domain.Place;
import com.onurtokat.services.PlaceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * MainControllerMockTest provides checking consistency for searchExactPlace() method
 * in MainController. In this test, Place service mock to query defined domain. Mocked
 * service is injected to the mocked MainController. Required fiels initialization is
 * handled by the JacksonTester object.
 *
 * @author onurtokat
 */
@RunWith(MockitoJUnitRunner.class)
public class MainControllerMockTest {

    private MockMvc mvc;

    @Mock
    private PlaceService placeService;

    @InjectMocks
    private MainController mainController;

    @Before
    public void setup() {

        // Initializes the JacksonTester
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(mainController).build();
    }

    /**
     * canRetrieveByGeonameidWhenExists
     *
     * @throws Exception Exception handling
     */
    @Test
    public void canRetrieveByGeonameidWhenExists() throws Exception {

        //initialize Place domain which will be used when checking
        Place placeTest = new Place();
        placeTest.setGeonameid(25);
        placeTest.setLatitude(40);
        placeTest.setLongitude(30);
        placeTest.setName("Istanbul");
        placeTest.setPopulation(BigInteger.valueOf(20000000));
        List<Place> list = new ArrayList<>();
        list.add(placeTest);

        given(placeService.listAll()).willReturn(list);
        given(placeService.findByGeonameid(25)).willReturn(list);

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/place?geonameid=25&submit=submit")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains(
                "Istanbul"
        );
    }

    /**
     * canRetrieveByNameWhenDoesNotExist provides consistency checking when
     * queried service list is empty.
     *
     * @throws Exception Exception handling
     */
    @Test
    public void canRetrieveByNameWhenDoesNotExist() throws Exception {
        // given with empty list
        given(placeService.findByGeonameid(25)).willReturn(new ArrayList<>());

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/place?geonameid=25&submit=submit")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{}");
    }
}
