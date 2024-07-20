package com.betrybe.museumfinder.solution;

import com.betrybe.museumfinder.database.MuseumFakeDatabase;
import com.betrybe.museumfinder.exception.InvalidCoordinateException;
import com.betrybe.museumfinder.exception.MuseumNotFoundException;
import com.betrybe.museumfinder.model.Coordinate;
import com.betrybe.museumfinder.model.Museum;
import com.betrybe.museumfinder.service.MuseumService;
import com.betrybe.museumfinder.util.CoordinateUtil;
import java.util.HashMap;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

@SpringBootTest
public class MuseumServiceTest {
  @MockBean
  MuseumFakeDatabase museumFakeDatabase;

  @Autowired
  MuseumService museumService;

  HashMap<String, String> museumData;
  Museum mockMuseum;
  Museum mockInvalidCoordMuseum;
  Coordinate mockValidCoordinate;
  Coordinate mockInvalidCoordinate;
  Double mockMaxDistance;

  @BeforeEach
  public void setUp() {
    this.museumData = new HashMap<>();

    museumData.put("name", "Museum");
    museumData.put("description", "Nice museum");
    museumData.put("address", "Grove Street");
    museumData.put("collectionType", "hist");
    museumData.put("subject", "art");
    museumData.put("url", "url");
    museumData.put("coordinate", "{\"latitude\":1.0,\"longitude\":1.0}");

    this.mockMuseum = new Museum();
    mockMuseum.setId(1L);
    mockMuseum.setName(museumData.get("name"));
    mockMuseum.setDescription(museumData.get("description"));
    mockMuseum.setAddress(museumData.get("address"));
    mockMuseum.setCollectionType(museumData.get("collectionType"));
    mockMuseum.setSubject(museumData.get("subject"));
    mockMuseum.setUrl(museumData.get("url"));
    mockMuseum.setCoordinate(new Coordinate(
        1.0,
        1.0
    ));

    mockInvalidCoordinate = new Coordinate(999, 999);

    mockInvalidCoordMuseum = new Museum();

    mockInvalidCoordMuseum.setCoordinate(this.mockInvalidCoordinate);

    mockValidCoordinate = new Coordinate(1, 1);


    mockMaxDistance = 100.0;
  }

  @Test
  @DisplayName("Testa getClosestMuseum no caso de sucesso")
  public void testGetClosestMuseumSuccess() {
    Mockito.when(this.museumFakeDatabase.getClosestMuseum(mockValidCoordinate, mockMaxDistance))
        .thenReturn(Optional.of(mockMuseum));

    Museum museum = this.museumService.getClosestMuseum(mockValidCoordinate, mockMaxDistance);

    Assertions.assertEquals(mockMuseum.getId(), museum.getId());
    Assertions.assertEquals(mockMuseum.getAddress(), museum.getAddress());
    Assertions.assertEquals(mockMuseum.getCollectionType(), museum.getCollectionType());
    Assertions.assertEquals(mockMuseum.getDescription(), museum.getDescription());
    Assertions.assertEquals(mockMuseum.getSubject(), museum.getSubject());
    Assertions.assertEquals(mockMuseum.getLegacyId(), museum.getLegacyId());
    Assertions.assertEquals(mockMuseum.getCoordinate(), museum.getCoordinate());
  }

  @Test
  @DisplayName("Testa getClosestMuseum no caso de coordenada inválida")
  public void testGetClosestMuseumInvalidCoord() {
    Mockito.when(this.museumFakeDatabase.getClosestMuseum(mockInvalidCoordinate, mockMaxDistance))
        .thenThrow(InvalidCoordinateException.class);


    Assertions.assertThrows(InvalidCoordinateException.class,() -> this.museumService.getClosestMuseum(mockInvalidCoordinate, mockMaxDistance));
  }

  @Test
  @DisplayName("Testa getClosestMuseum no caso de museu não encontrado")
    public void testGetClosestMuseumNotFound() {
    Mockito.when(this.museumFakeDatabase.getClosestMuseum(mockValidCoordinate, mockMaxDistance))
        .thenThrow(MuseumNotFoundException.class);

    Assertions.assertThrows(MuseumNotFoundException.class,
        () -> this.museumService.getClosestMuseum(mockValidCoordinate, mockMaxDistance));
  }

  @Test
  @DisplayName("Testa createMuseum no caso de sucesso")
  public void testCreateMuseumSuccessful() {
    Mockito.when(this.museumFakeDatabase.saveMuseum(this.mockMuseum))
        .thenReturn(this.mockMuseum);

    Museum museum = this.museumService.createMuseum(mockMuseum);

    Assertions.assertEquals(this.mockMuseum.getId(), museum.getId());
    Assertions.assertEquals(this.mockMuseum.getLegacyId(), museum.getLegacyId());
    Assertions.assertEquals(this.mockMuseum.getCollectionType(), museum.getCollectionType());
    Assertions.assertEquals(this.mockMuseum.getDescription(), museum.getDescription());
    Assertions.assertEquals(this.mockMuseum.getCoordinate(), museum.getCoordinate());
    Assertions.assertEquals(this.mockMuseum.getSubject(), museum.getSubject());
    Assertions.assertEquals(this.mockMuseum.getUrl(), museum.getUrl());
  }

  @Test
  @DisplayName("Testa createMuseum no caso de coordenada inválida")
  public void testCreateMuseumInvalidCoordinate() {

    Mockito.when(this.museumFakeDatabase.saveMuseum(this.mockInvalidCoordMuseum))
        .thenThrow(InvalidCoordinateException.class);

    Assertions.assertThrows(InvalidCoordinateException.class,
        () -> this.museumService.createMuseum(this.mockInvalidCoordMuseum));
  }
}
