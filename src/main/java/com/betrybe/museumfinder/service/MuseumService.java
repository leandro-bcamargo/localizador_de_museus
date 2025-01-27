package com.betrybe.museumfinder.service;

import com.betrybe.museumfinder.database.MuseumFakeDatabase;
import com.betrybe.museumfinder.exception.InvalidCoordinateException;
import com.betrybe.museumfinder.exception.MuseumNotFoundException;
import com.betrybe.museumfinder.model.Coordinate;
import com.betrybe.museumfinder.model.Museum;
import com.betrybe.museumfinder.util.CoordinateUtil;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Museum service.
 */
@Service
public class MuseumService implements MuseumServiceInterface {
  private final MuseumFakeDatabase museumFakeDatabase;

  /**
   * Instantiates a new Museum service.
   *
   * @param museumFakeDatabase the museum fake database
   */
  @Autowired
  public MuseumService(MuseumFakeDatabase museumFakeDatabase) {
    this.museumFakeDatabase = museumFakeDatabase;
  }

  @Override
  public Museum getClosestMuseum(Coordinate coordinate, Double maxDistance) {
    Optional<Museum> closestMuseum = this.museumFakeDatabase.getClosestMuseum(coordinate,
        maxDistance);

    Boolean isCoordinateValid = CoordinateUtil.isCoordinateValid(coordinate);

    if (!isCoordinateValid) {
      throw new InvalidCoordinateException();
    }

    if (closestMuseum.isEmpty()) {
      throw new MuseumNotFoundException();
    }

    return closestMuseum.get();
  }

  @Override
  public Museum createMuseum(Museum museum) {
    Boolean isCoordinateValid = CoordinateUtil.isCoordinateValid(museum.getCoordinate());
    if (!isCoordinateValid) {
      throw new InvalidCoordinateException();
    }
    return this.museumFakeDatabase.saveMuseum(museum);
  }

  @Override
  public Museum getMuseum(Long id) {
    Optional<Museum> museumOptional = this.museumFakeDatabase.getMuseum(id);

    if (museumOptional.isEmpty()) {
      throw new MuseumNotFoundException();
    }

    return museumOptional.get();
  }
}
