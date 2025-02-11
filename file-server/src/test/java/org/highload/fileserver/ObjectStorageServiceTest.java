package org.highload.fileserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.minio.Result;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import org.highload.datasource.MinioAdapter;
import org.highload.dto.ObjectDto;
import org.highload.service.ObjectStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ObjectStorageServiceTest {

    @Mock
    private MinioAdapter minioAdapter;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ObjectStorageService objectStorageService;

    private MultipartFile multipartFile;
    private InputStream inputStream;
    private String fileName;

    @BeforeEach
    void setUp() throws IOException {
        inputStream = new ByteArrayInputStream("test data".getBytes());
        multipartFile = new MockMultipartFile("test.txt", inputStream);
        fileName = "test.txt";
    }

    @Test
    void testStore() throws IOException, NoSuchAlgorithmException, InvalidKeyException, MinioException {
        String presignedUrl = "http://example.com/test.txt";
        doNothing().when(minioAdapter).putObject(any(InputStream.class), anyString());
        when(minioAdapter.getPresignedObjectUrl(anyString())).thenReturn(presignedUrl);

        String result = objectStorageService.store(multipartFile);

        assertEquals(presignedUrl, result);
    }

    @Test
    void testStoreInputStream() throws IOException, NoSuchAlgorithmException, InvalidKeyException, MinioException {
        String presignedUrl = "http://example.com/test.txt";
        doNothing().when(minioAdapter).putObject(any(InputStream.class), anyString());
        when(minioAdapter.getPresignedObjectUrl(anyString())).thenReturn(presignedUrl);

        String result = objectStorageService.storeInputStream(inputStream, fileName);

        assertEquals(presignedUrl, result);
        verify(minioAdapter, times(1)).putObject(any(InputStream.class), eq(fileName));
        verify(minioAdapter, times(1)).getPresignedObjectUrl(fileName);
    }

    @Test
    void testLoadAll() throws Exception {
        Item item = mock(Item.class);
        when(item.etag()).thenReturn("etag-example");
        when(item.objectName()).thenReturn(fileName);
        when(item.size()).thenReturn(1024L);

        Result<Item> result = mock(Result.class);
        when(result.get()).thenReturn(item);

        when(minioAdapter.getAllObjectsList()).thenReturn(List.of(result));
        when(minioAdapter.getPresignedObjectUrl(anyString())).thenReturn("http://example.com/test.txt");

        ObjectDto objectDto = ObjectDto.map(item, "http://example.com/test.txt");
        when(objectMapper.writeValueAsString(anyList())).thenReturn("[{\"id\":\"etag-example\",\"name\":\"test.txt\",\"url\":\"http://example.com/test.txt\",\"size\":1024}]");

        String resultJson = objectStorageService.loadAll();

        assertEquals("[{\"id\":\"etag-example\",\"name\":\"test.txt\",\"url\":\"http://example.com/test.txt\",\"size\":1024}]", resultJson);
        verify(minioAdapter, times(1)).getAllObjectsList();
        verify(objectMapper, times(1)).writeValueAsString(anyList());
    }

    @Test
    void testLoad() throws Exception {
        InputStream mockInputStream = mock(InputStream.class);
        when(minioAdapter.getObject(anyString())).thenReturn(mockInputStream);

        InputStream result = objectStorageService.load(fileName);

        assertNotNull(result);
        verify(minioAdapter, times(1)).getObject(fileName);
    }
}
