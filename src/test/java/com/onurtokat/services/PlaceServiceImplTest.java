package com.onurtokat.services;

import com.onurtokat.domain.Place;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * PlaceServiceImplTest class provides checking whether each methods working correctly
 * Before the tests, required fields is setted.
 * <p>
 * <p>Repository and service classes is mocked to use in the tests
 *
 * @author onurtokat
 */
public class PlaceServiceImplTest {

    private PlaceService placeServiceMock = mock(PlaceService.class);
    private Place placeTest = new Place();
    private List<Place> placeListTest = new ArrayList<>();

    /**
     * setting init values before do tests
     */
    @Before
    public void init() {
        placeTest.setGeonameid(745044);
        placeTest.setName("istanbul");
        placeTest.setLatitude(41.01384);
        placeTest.setLongitude(28.94966);
        placeTest.setPopulation(BigInteger.valueOf(48096721));
        placeListTest.add(placeTest);

        when(placeServiceMock.findByGeonameid(745044)).thenReturn(placeListTest);
        when(placeServiceMock.findByName("istanbul")).thenReturn(placeListTest);
        when(placeServiceMock.listAll()).thenReturn(placeListTest);
    }

    /**
     * listAll() methods is used for getting whole data which is collected via repository
     */
    @Test
    public void listAll() {
        assertNotNull(placeServiceMock.listAll());
        assertEquals(placeServiceMock.listAll(), placeListTest);
    }

    @Test
    public void findByName() {
        assertNotNull(placeServiceMock.findByName("istanbul"));
        assertEquals(placeServiceMock.findByName("istanbul"), placeListTest);
    }

    @Test
    public void findByGeonameid() {
        assertNotNull(placeServiceMock.findByGeonameid(745044));
        assertEquals(placeServiceMock.findByGeonameid(745044), placeListTest);
    }

}