package com.onurtokat.init;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import com.onurtokat.domain.Place;
import com.onurtokat.repositories.PlaceRepository;

/**
 * PlaceDataInit is used for data loading to mongo db. At Spring boot
 * waking up, it loads initial data to mongo db. In the upload file
 * web page, user can manually load file as well.
 *
 * @author onurtokat
 */
@Component
public class PlaceDataInit implements ApplicationRunner {

    private static PlaceRepository placeRepository;
    private static final String INITIAL_FILE_PATH = "initialData/IN.txt";
    private static Place place = new Place();

    @Autowired
    public PlaceDataInit(PlaceRepository placeRepository) {
        PlaceDataInit.placeRepository = placeRepository;
    }

    /**
     * run() method provides initial data loading control. If repository have no data
     * then auto loading is started.
     *
     * @param args
     */
    @Override
    public void run(ApplicationArguments args) {
        long count = placeRepository.count();
        if (count == 0) {
            fileLoader(INITIAL_FILE_PATH);
        }
    }

    /**
     * fileLoader loads file reading line by line. Only used data fields are
     * collected by splitting. Splitter is tab delimiter. Initialized Place object
     * loaded to repository. Exception management is customized for meaningful
     * exception messaging
     *
     * @param path file path as String
     */
    public static void fileLoader(String path) {

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path), Charset.forName("UTF-8"))) {
            String currentLine = null;
            try {
                while ((currentLine = reader.readLine()) != null) {//while there is content on the current line
                    String[] splitted = currentLine.split("\\t");
                    place.setGeonameid(Long.valueOf(splitted[0]));
                    place.setName(splitted[1]);
                    place.setLatitude(Double.valueOf(splitted[4]));
                    place.setLongitude(Double.valueOf(splitted[5]));
                    place.setPopulation(new BigInteger(splitted[14]));

                    placeRepository.save(place);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new PlaceDataInitException("One of the file's fields are not compatible format for Place abstraction", e.getCause());
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            throw new PlaceDataInitFileNotFoundException("File does not exist in the defined path", ex.getCause());
        }
    }
}
