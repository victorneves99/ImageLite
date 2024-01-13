package io.github.victorneves.imageliteapi.domain.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Jacksonized
public class ImageDTO {

  private String url;

  private String name;

  private String extension;

  private Long size;

  private LocalDate uploadDate;

}
