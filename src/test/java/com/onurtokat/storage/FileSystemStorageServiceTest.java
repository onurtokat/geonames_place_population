package com.onurtokat.storage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

/**
 * FileSystemStorageServiceTest consists testing methods for
 * FileSystemStorageService class. Each methods of the FileSystemStorageService
 * class are tested.
 * 
 * <p>File existing and file non-existing conditions are tested. Also, the
 * exceptions which should be thrown when they are catched are added to test
 * methods.
 * 
 * <p>Mock framework is used for creating dummy file objects.
 * 
 * @author onurtokat
 *
 */
public class FileSystemStorageServiceTest {

	private StorageProperties properties = new StorageProperties();
	private FileSystemStorageService service;

	/**
	 * Ceating directory location for storing files before testing Each time
	 * creating unique folder naming is provided by random long value.
	 */
	@Before
	public void init() {
		properties.setLocation("target/files/" + Math.abs(new Random().nextLong()));
		service = new FileSystemStorageService(properties);
		service.init();
	}

	/**
	 * Loading unexisting file testing. Load method will return a path for finding
	 * file. But, in this location, file would not exist.
	 */
	@Test
	public void loadNonExistent() {
		assertThat(service.load("test.txt")).doesNotExist();

	}

	/**
	 * When trying to load a file which does not exist, loadAsResource() method
	 * should give StorageFileNotFoundException
	 */
	@Test(expected = StorageFileNotFoundException.class)
	public void loadNonExistentException() {
		service.loadAsResource("test.txt");
	}

	/**
	 * Dummy file which is created by Mock, should be used by saveAndLoad() method
	 * properly
	 */
	@Test
	public void saveAndLoad() {
		service.store(new MockMultipartFile("test", "test.txt", MediaType.TEXT_PLAIN_VALUE,
				"This is the data file".getBytes()));
		assertThat(service.load("test.txt")).exists();
	}

	/**
	 * Dummy files which are created by Mock should be loaded and Method should give
	 * correct files count
	 */
	@Test
	public void saveAndLoadAll() {
		service.store(new MockMultipartFile("test", "test1.txt", MediaType.TEXT_PLAIN_VALUE, "TEST 1".getBytes()));
		service.store(new MockMultipartFile("test", "test2.txt", MediaType.TEXT_PLAIN_VALUE, "TEST 2".getBytes()));
		service.store(new MockMultipartFile("test", "test3.txt", MediaType.TEXT_PLAIN_VALUE, "TEST 3".getBytes()));
		assertEquals(3, service.loadAll().count());
	}

	/**
	 * When Non-existing file is tried to save by store() method, then it should
	 * throw StorageException
	 */
	@Test(expected = StorageException.class)
	public void saveNotPermitted() {
		service.store(new MockMultipartFile("test", "../test.txt", MediaType.TEXT_PLAIN_VALUE,
				"This is the data file".getBytes()));
	}

	/**
	 * When existing file is tried to save by store() method, Method should do its
	 * main duty properly
	 */
	@Test
	public void savePermitted() {
		service.store(new MockMultipartFile("test", "bar/../test.txt", MediaType.TEXT_PLAIN_VALUE,
				"This is the data file".getBytes()));
	}

	/**
	 * After running deleteAll() method, No file should be exist in related
	 * directory
	 */
	@Test
	public void deleteAllTest() {
		service.store(new MockMultipartFile("test", "test.txt", MediaType.TEXT_PLAIN_VALUE,
				"This is the data file".getBytes()));
		assertThat(service.load("test.txt")).exists();
		service.deleteAll();
		assertThat(service.load("test.txt")).doesNotExist();
	}
}
