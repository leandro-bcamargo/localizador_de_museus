package com.betrybe.museumfinder.solution;

import static org.mockito.ArgumentMatchers.any;

import com.betrybe.museumfinder.dto.CollectionTypeCount;
import com.betrybe.museumfinder.service.CollectionTypeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class CollectionTypeControllerTest {
  @MockBean
  CollectionTypeService collectionTypeService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("Deve retornar número de museus com uma dada coleção de tipos")
  public void testGetCollectionTypesCountSuccess() throws Exception {
    String[] mockCollectionTypes = new String[]{"hist", "imag"};
    CollectionTypeCount mockCollectionTypeCount = new CollectionTypeCount(
        mockCollectionTypes, 492);

    Mockito.when(collectionTypeService.countByCollectionTypes(any())).thenReturn(mockCollectionTypeCount);

    String url = "/collections/count/hist,imag";
    mockMvc.perform(MockMvcRequestBuilders.get(url))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.collectionTypes").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.collectionTypes[0]").value("hist"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.collectionTypes[1]").value("imag"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(492));
  }
}
