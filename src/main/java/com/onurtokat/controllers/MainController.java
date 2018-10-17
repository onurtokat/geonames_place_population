package com.onurtokat.controllers;

import com.onurtokat.init.PlaceDataInit;
import com.onurtokat.model.MainPlaceInfo;
import com.onurtokat.services.PlaceService;
import com.onurtokat.storage.StorageFileNotFoundException;
import com.onurtokat.domain.Place;
import com.onurtokat.model.SearchModel;
import com.onurtokat.storage.StorageService;
import com.onurtokat.utility.DistanceCalculator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MainController contains GET and POST mapping methods for page redirections.
 * These methods provide file upload, search, and result page controling.
 * <p>
 * <p>File upload page accepts user defined files. Excepted file format has tab delimited
 * format. If an exception occurs when file processing, it is redirected to fileError page.
 * Also, specific exception is thrown to be understandable explanation.</p>
 * <p>
 * <p>Search page accepts place name and radius from user with textboxes. Place name and
 * radius information are required for population computation. Therefore, if user enter null
 * place name or radius, then application redirect current page to inputError page. As geonames
 * data contains many place names which could be same with input place name, application tries to
 * detect exact place name from the geoname data. After submit, exact place name list is added to
 * selectbox on the result.html.</p>
 * <p>
 * <p>Submitted exact place is queried on the mongo db with radius. All data is reduced according to
 * if distance of the points less or equals to radius, then remaining coordinates are added to place
 * list (filteredPlaceInfo). The populations of the filtered list is summed and added to exact place
 * population.</p>
 * <p>
 * <p>Filtered list is shown as sub-places of the exact place name. Json format returning shows this
 * relationship in the result page.
 * </p>
 *
 * @author onurtokat
 */
@Controller
public class MainController {

    private PlaceService placeService;
    private StorageService storageService;
    private static double radius;

    /**
     * Setter for PlaceService
     *
     * @param placeService is used for initialization of placeService instance variable
     */
    @Autowired
    public void setPlaceService(PlaceService placeService) {
        this.placeService = placeService;
    }

    /**
     * Setter for Storage Service
     *
     * @param storageService is used for initialization of placeService instance variable
     */
    @Autowired
    public void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * listUploadedFiles GET mapping method provides file list of the
     * uploaded files with label file for model. Uploaded file is
     * transferred to the serveFile method using mvcUriComponentsContributor.
     * Thus, file URI can be shared with serveFile method.
     *
     * @param model is used for adding file URI as attribute
     * @return uploadForm page
     * @throws IOException is used for when file exceptions occur
     */
    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files",
                storageService.loadAll()
                        .map(path -> MvcUriComponentsBuilder
                                .fromMethodName(MainController.class, "serveFile", path.getFileName().toString())
                                .build().toString())
                        .collect(Collectors.toList()));

        return "uploadForm";
    }

    /**
     * serveFile GET mapping method uses the filename with its absolute path
     * to serve it as HTTP link on the page
     *
     * @param filename is achieved from the URL as parameter and is used for path definition
     * @return HTTP entity to be served as HTTP link on the page
     * @throws IOException is used for when file does not exist situation
     */
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws IOException {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFile().getAbsolutePath() + "\"").body(file);
    }

    /**
     * handleFileUpload POST mapping method is used for storing uploaded file with
     * store() method. Storing path is defined in the StorageProperties class as location.
     * At the same time, fileLoader() method is used to load file line by line to mongo db.
     * If an exception is thrown, when data reading and loading, user is redirected to
     * fileError.html page to inform about error. If data is loaded to embedded db successfully,
     * result message is written to page with file name as http link. Via http link file can
     * be accessable and downloadable.
     *
     * @param file MultipartFile
     * @param redirectAttributes attributes which will be used for informing
     * @return localhost:8080, file uploader main page
     */
    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        storageService.store(file);
        try {
            PlaceDataInit.fileLoader("upload-dir/" + file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
            return "/fileError";
        }
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    /**
     * handleStorageFileNotFound method is used for exception handling when
     * file not found exception
     *
     * @param exc is used for as expected thrown exception
     * @return is HttpStatus.NOT_FOUND of the response field
     */
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    /**
     * searchForm is used for collecting the user place name and radius inputs
     * Place name and radius inputs are binded with SearchModel POJO.
     *
     * @param searchModel is used for place name and radius inputs as a POJO
     * @return search.html
     */
    @GetMapping("/search")
    public String searchForm(SearchModel searchModel) {
        return "search";
    }

    /**
     * searchSubmit purposes to define and use exact place name rather than using
     * user's place name. Because place name can be exist in the many locations
     * on earth. Defining exact place means defining exact latitude and longitude.
     *
     * @param searchModel the model which is used in query operations
     * @param model       model accepts new attributes which will be used next
     *                    operations
     * @return if user does not enter place name or radius, then return will
     * be error page. If place name does not exist on the place data returns
     * input error page. If everything is ok, the n returns next page, resut.html
     */
    @PostMapping("/search")
    public String searchSubmit(@Valid SearchModel searchModel, Model model) {
        if (searchModel.getName().isEmpty()) {
            return "inputError";
        }
        radius = searchModel.getRadius();
        ArrayList<Place> selected = (ArrayList<Place>) placeService.findByName(searchModel.getName());

        if (selected.size() == 0) {
            return "notFoundPlace";
        }
        model.addAttribute("selected", selected);
        return "result";
    }

    /**
     * searchExactPlace method uses exact place name geonameid to define latitude,
     * longitude, population from database. According to radius, whole data reduced
     * to place which are only on radius border. each population of the reduced data is
     * added to exact place name population. Exact place and its sub-places is shown
     * as JSON format as return
     *
     * @param searchModel exact place model
     * @param geonameid   unique id
     * @return JSON format of the exact place and it sub-places
     */
    @ResponseBody
    @RequestMapping(value = "/place", method = RequestMethod.GET, headers = "Accept=application/json", params = {
            "geonameid", "submit"})
    @SuppressWarnings("unchecked")
    public String searchExactPlace(@ModelAttribute("searchModel") SearchModel searchModel,
                                   @RequestParam("geonameid") final Long geonameid) {

        Iterable<Place> allPlaceInfo = placeService.listAll();
        List<Place> selectedPlaceInfos = placeService.findByGeonameid(geonameid);
        List<Place> filteredPlaceInfo = new ArrayList<Place>();

        JSONObject jsonObj = new JSONObject();
        JSONArray jsonList = new JSONArray();

        for (int i = 0; i < selectedPlaceInfos.size(); i++) {

            allPlaceInfo.forEach(filteredPlaceInfo::add);

            double latitude1 = selectedPlaceInfos.get(i).getLatitude();
            double longitude1 = selectedPlaceInfos.get(i).getLongitude();
            BigInteger tmp = selectedPlaceInfos.get(i).getPopulation();

            filteredPlaceInfo.removeIf(s -> DistanceCalculator.distance(latitude1, s.getLatitude(), longitude1,
                    s.getLongitude()) > radius);

            for (Place p1 : filteredPlaceInfo) {
                if (p1.getGeonameid() != geonameid) {
                    tmp = tmp.add(p1.getPopulation());
                    jsonList.add(p1);
                }
            }
            jsonObj.put(1,
                    new MainPlaceInfo(selectedPlaceInfos.get(i).getName(), selectedPlaceInfos.get(i).getLatitude(),
                            selectedPlaceInfos.get(i).getLongitude(), tmp, filteredPlaceInfo.size(),
                            filteredPlaceInfo));
        }

        return jsonObj.toJSONString();
    }
}
