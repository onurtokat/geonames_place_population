package com.onurtokat.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * StorageProperties provides file location which will be used for
 * file operations.
 *
 * @author onurtokat
 *
 */
@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";

    /**
     *
     * @return file location as String
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location set location as String
     */
    public void setLocation(String location) {
        this.location = location;
    }

}