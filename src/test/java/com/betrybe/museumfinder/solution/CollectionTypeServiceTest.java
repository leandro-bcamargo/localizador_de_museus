package com.betrybe.museumfinder.solution;

import com.betrybe.museumfinder.database.MuseumFakeDatabase;
import com.betrybe.museumfinder.dto.CollectionTypeCount;
import com.betrybe.museumfinder.service.CollectionTypeService;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CollectionTypeServiceTest {
  @MockBean
  MuseumFakeDatabase museumFakeDatabase;

  @Autowired
  CollectionTypeService collectionTypeService;

  @Test
  @DisplayName("Testa countByCollectionTypes para totalCount > 0")
  public void testCountByCollectionTypesWithTotalCountBiggerThanZero() {

    String[] mockCollectionTypes = new String[]{"hist"};
    long mockCount = 492L;

    CollectionTypeCount mockCollectionTypeCount = new CollectionTypeCount(mockCollectionTypes,
        mockCount);

    Mockito.when(this.museumFakeDatabase.countByCollectionType(ArgumentMatchers.any()))
        .thenReturn(mockCount);

    CollectionTypeCount collectionTypeCount = this.collectionTypeService.countByCollectionTypes(
        "hist");
    Assertions.assertNotNull(collectionTypeCount);
    Assertions.assertEquals(mockCollectionTypeCount.count(), collectionTypeCount.count());
    Assertions.assertArrayEquals(mockCollectionTypeCount.collectionTypes(),
        collectionTypeCount.collectionTypes());
    Mockito.verify(this.museumFakeDatabase).countByCollectionType(ArgumentMatchers.eq("hist"));
  }
}
