package com.onurtokat.controllers;

import com.onurtokat.services.PlaceService;
import com.onurtokat.storage.FileSystemStorageService;
import com.onurtokat.storage.StorageProperties;
import com.onurtokat.storage.StorageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author onurtokat
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MainControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private StorageProperties properties = new StorageProperties();
    private FileSystemStorageService service;
    private PlaceService placeService;
    private StorageService storageService;

    @Before
    public void init() {
        placeService = mock(PlaceService.class);
        storageService = mock(StorageService.class);

        properties.setLocation("target/files/" + Math.abs(new Random().nextLong()));
        service = new FileSystemStorageService(properties);
        service.init();
    }

    @Test
    public void setPlaceServiceTest() throws Exception {
        assertNotNull(placeService);
    }

    @Test
    public void setStorageServiceTest() throws Exception {
        assertNotNull(placeService);
    }

    /**
     * listUploadedFilesTest method is used when mock file created and stored
     * then loadAll() method should not be empty
     * @throws Exception
     */
    @Test
    public void listUploadedFilesTest() throws Exception {
        storageService.store(new MockMultipartFile("test", "test.txt", MediaType.TEXT_PLAIN_VALUE,
                "This is the data file".getBytes()));
        assertNotNull(storageService.loadAll());
    }

    @Test
    public void searchFormTest() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/search",
                String.class)).contains("search");
    }

    @Test
    public void searchPlaceTest() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port +
                        "/search/place?geonameid=1&submit=submit",
                String.class)).contains("Not Found");
//        assertThat(this.restTemplate.getForObject("http://localhost:" + port +
//                        "/search/place?geonameid=6483075&submit=submit",
//                String.class)).contains("ISTANBUL");
    }
}