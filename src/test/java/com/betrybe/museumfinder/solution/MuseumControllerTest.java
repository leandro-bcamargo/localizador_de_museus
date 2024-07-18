package com.betrybe.museumfinder.solution;

import com.betrybe.museumfinder.dto.MuseumCreationDto;
import com.betrybe.museumfinder.dto.MuseumDto;
import com.betrybe.museumfinder.exception.MuseumNotFoundException;
import com.betrybe.museumfinder.model.Coordinate;
import com.betrybe.museumfinder.model.Museum;
import com.betrybe.museumfinder.service.MuseumService;
import com.betrybe.museumfinder.util.ModelDtoConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class MuseumControllerTest {
  @Autowired
  MockMvc mockMvc;

  @MockBean
  MuseumService museumService;

  private HashMap<String, String> museumData;
  private Museum mockMuseum;
  private MuseumCreationDto mockMuseumCreationDto;
  private MuseumDto mockMuseumDto;

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

     this.mockMuseumCreationDto = new MuseumCreationDto(
        museumData.get("name"),
        museumData.get("description"),
        museumData.get("address"),
        museumData.get("collectionType"),
        museumData.get("subject"),
        museumData.get("url"),
        new Coordinate(
            1.0,
            1.0
        ));

    this.mockMuseumDto = new MuseumDto(
        1L,
        museumData.get("name"),
        museumData.get("description"),
        museumData.get("address"),
        museumData.get("collectionType"),
        museumData.get("subject"),
        museumData.get("url"),
        new Coordinate(
            1.0,
            1.0
        ));
  }

  @Test
  @DisplayName("Testa POST na rota / com sucesso")
  public void testCreateMuseumSuccess() throws Exception {

    Mockito.when(this.museumService.createMuseum(ArgumentMatchers.any()))
        .thenReturn(mockMuseum);

    String url = "/museums";
    ObjectMapper objectMapper = new ObjectMapper();
    String jsonPayload = objectMapper.writeValueAsString(mockMuseumDto);

    mockMvc.perform(MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonPayload))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(mockMuseumDto.name()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(mockMuseumDto.description()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(mockMuseumDto.address()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.collectionType").value(mockMuseumDto.collectionType()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.subject").value(mockMuseumDto.subject()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.url").value(mockMuseumDto.url()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.coordinate").isMap());
  }

  @Test
  @DisplayName("Testa GET na rota /closest com sucesso")
  public void testGetClosestMuseum() throws Exception {

    Mockito.when(this.museumService.getClosestMuseum(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(mockMuseum);

    String url = "/museums/closest";

    mockMvc.perform(MockMvcRequestBuilders.get(url)
            .param("lat", "1.0")
            .param("lng", "1.0")
            .param("max_dist_km", "1.0"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(mockMuseumDto.name()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(mockMuseumDto.description()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(mockMuseumDto.address()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.collectionType").value(mockMuseumDto.collectionType()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.subject").value(mockMuseumDto.subject()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.url").value(mockMuseumDto.url()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.coordinate").isMap());
  }

  @Test
  @DisplayName("Testa requisição GET na rota museums/{id} com sucesso")
  public void testGetMuseumByIdSuccess() throws Exception {
    Mockito.when(this.museumService.getMuseum(ArgumentMatchers.eq(this.mockMuseum.getId())))
        .thenReturn(this.mockMuseum);

    String url = "/museums/{id}";
    mockMvc.perform(MockMvcRequestBuilders.get(url, 1L))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(mockMuseumDto.name()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(mockMuseumDto.description()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(mockMuseumDto.address()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.collectionType").value(mockMuseumDto.collectionType()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.subject").value(mockMuseumDto.subject()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.url").value(mockMuseumDto.url()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.coordinate").isMap());
  }

  @Test
  @DisplayName("Testa requisição GET na rota museums/{id} não encontrado")
  public void testGetMuseumByIdNotFound() throws Exception {
    Mockito.when(this.museumService.getMuseum(ArgumentMatchers.eq(999L)))
        .thenThrow(MuseumNotFoundException.class);

    String url = "/museums/{id}";
    mockMvc.perform(MockMvcRequestBuilders.get(url, 999L))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}