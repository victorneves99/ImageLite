package io.github.victorneves.imageliteapi.domain.enums;

import java.util.Arrays;

import org.springframework.http.MediaType;

import lombok.Getter;

public enum ImgaeExtension {
  PNG(MediaType.IMAGE_PNG),
  GIF(MediaType.IMAGE_GIF),
  JPEG(MediaType.IMAGE_JPEG);

  @Getter
  private MediaType mediaType;

  private ImgaeExtension(MediaType mediaType) {
    this.mediaType = mediaType;
  }

  public static ImgaeExtension valuOf(MediaType mediaType) {

    return Arrays.stream(values()).filter(ie -> ie.mediaType.equals(mediaType)).findFirst().orElse(null);

  }

  public static ImgaeExtension ofName(String name) {

    return Arrays.stream(values())
        .filter(img -> img.name().equalsIgnoreCase(name))
        .findFirst()
        .orElse(null);

  }

}
