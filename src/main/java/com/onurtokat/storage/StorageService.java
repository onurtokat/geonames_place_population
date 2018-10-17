package com.onurtokat.storage;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author onurtokat
 */
public interface StorageService {

    void init();

    /**
     * @param file MultipartFile type for file
     */
    void store(MultipartFile file);

    /**
     * @return Path of Stream list
     */
    Stream<Path> loadAll();

    /**
     * @param filename file name as String
     * @return Path of the file
     */
    Path load(String filename);

    /**
     * @param filename file name as String
     * @return file URI as Resource
     */
    Resource loadAsResource(String filename);

    /**
     * Delete all file recursively
     */
    void deleteAll();

}