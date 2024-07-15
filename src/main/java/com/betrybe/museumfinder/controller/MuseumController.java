package com.betrybe.museumfinder.controller;

import com.betrybe.museumfinder.dto.MuseumCreationDto;
import com.betrybe.museumfinder.dto.MuseumDto;
import com.betrybe.museumfinder.model.Coordinate;
import com.betrybe.museumfinder.model.Museum;
import com.betrybe.museumfinder.service.MuseumServiceInterface;
import com.betrybe.museumfinder.util.CoordinateUtil;
import com.betrybe.museumfinder.util.ModelDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Museum controller.
 */
@RestController
@RequestMapping("/museums")
public class MuseumController {
  private MuseumServiceInterface museumService;

  /**
   * Instantiates a new Museum controller.
   *
   * @param museumService the museum service
   */
  @Autowired
  public MuseumController(MuseumServiceInterface museumService) {
    this.museumService = museumService;
  }

  /**
   * Create museum response entity.
   *
   * @param museumCreationDto the museum creation dto
   * @return the response entity
   */
  @PostMapping
  public ResponseEntity<MuseumDto> createMuseum(MuseumCreationDto museumCreationDto) {
    Museum museum = ModelDtoConverter.dtoToModel(museumCreationDto);
    Museum createdMuseum = this.museumService.createMuseum(museum);

    MuseumDto museumDto = ModelDtoConverter.modelToDto(createdMuseum);

    return ResponseEntity.status(HttpStatus.CREATED).body(museumDto);
  }

  /**
   * Gets closest museum.
   *
   * @param lat       the lat
   * @param lng       the lng
   * @param maxDistKm the max dist km
   * @return the closest museum
   */
  @GetMapping("/closest")
  public ResponseEntity<MuseumDto> getClosestMuseum(
      @RequestParam String lat,
      @RequestParam String lng,
      @RequestParam(name = "max_dist_km") String maxDistKm) {
    Coordinate coordinate = new Coordinate(Double.parseDouble(lat), Double.parseDouble(lng));
    Museum museum = this.museumService.getClosestMuseum(coordinate,
        Double.parseDouble(maxDistKm));
    MuseumDto museumDto = ModelDtoConverter.modelToDto(museum);

    return ResponseEntity.ok(museumDto);
  }

  @GetMapping("{id}")
  private ResponseEntity<MuseumDto> getMuseum(@PathVariable Long id) {
    Museum museum = this.museumService.getMuseum(id);
    MuseumDto museumDto = ModelDtoConverter.modelToDto(museum);

    return ResponseEntity.ok(museumDto);
  }
}
