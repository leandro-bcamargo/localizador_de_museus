package com.betrybe.museumfinder.controller;

import com.betrybe.museumfinder.dto.MuseumCreationDto;
import com.betrybe.museumfinder.dto.MuseumDto;
import com.betrybe.museumfinder.model.Museum;
import com.betrybe.museumfinder.service.MuseumServiceInterface;
import com.betrybe.museumfinder.util.ModelDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Museum controller.
 */
@RestController
@RequestMapping("/museums")
public class MuseumController {
  private MuseumServiceInterface museumService;

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
}
