package com.onurtokat.init;

import com.onurtokat.domain.Place;
import com.onurtokat.repositories.PlaceRepository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author onurtokat
 */
public class PlaceDataInitTest {

    private PlaceRepository placeRepositoryMock = mock(PlaceRepository.class);
    private PlaceDataInit placeDataInitTest;
    private Place placeTest = new Place();
    private List<Place> placeListTest = new ArrayList<>();
    private ClassLoader classLoader;

    /**
     *
     */
    @Before
    public void init() {

        placeTest.setGeonameid(745044);
        placeTest.setName("istanbul");
        placeTest.setLatitude(41.01384);
        placeTest.setLongitude(28.94966);
        placeTest.setPopulation(BigInteger.valueOf(48096721));
        placeListTest.add(placeTest);

        when(placeRepositoryMock.findAll()).thenReturn(placeListTest);
        placeDataInitTest = new PlaceDataInit(placeRepositoryMock);
        classLoader = placeDataInitTest.getClass().getClassLoader();
    }

    @Test
    public void runTest() {
        assertTrue(placeRepositoryMock.count() == 0);
    }

    @Test(expected = PlaceDataInitFileNotFoundException.class)
    public void PlaceDataInitFileNotFoundExceptionTest() {
        PlaceDataInit.fileLoader("test.txt");
    }

    @Test(expected = PlaceDataInitException.class)
    public void PlaceDataInitExceptionTest() {
        PlaceDataInit.fileLoader(new File(classLoader.getResource("test_data_error.txt").getFile()).getAbsolutePath());
    }
}