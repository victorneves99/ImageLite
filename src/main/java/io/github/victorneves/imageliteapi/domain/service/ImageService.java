package io.github.victorneves.imageliteapi.domain.service;

import java.util.List;
import java.util.Optional;

import io.github.victorneves.imageliteapi.domain.entity.Image;
import io.github.victorneves.imageliteapi.domain.enums.ImgaeExtension;

public interface ImageService {

  Image save(Image image);

  Optional<Image> getById(String id);

  List<Image> search(ImgaeExtension imgaeExtension, String query);

}