package com.onurtokat.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.onurtokat.storage.StorageService;

/**
 * StorageConfig class provides create or delete files from folder. Folder path
 * defined at StorageProperties.class as "upload-dir". At startup, CommandLineRunner
 * deletes --> deleteAll() and recreate files --> init().
 *
 * @author onurtokat
 */
@Configuration
public class StorageConfig {

    /**
     * init method uses StorageService interface's methods
     * to create and delete files
     *
     * @param storageService provides methods to be used
     * @return StorageService methods which will be used
     */
    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}
