package io.github.victorneves.imageliteapi.domain.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.victorneves.imageliteapi.domain.entity.Image;
import io.github.victorneves.imageliteapi.domain.enums.ImgaeExtension;
import io.github.victorneves.imageliteapi.domain.service.ImageService;
import io.github.victorneves.imageliteapi.infra.repository.ImageRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

  private final ImageRepository repository;

  @Override
  @Transactional
  public Image save(Image image) {

    return repository.save(image);
  }

  @Override
  public Optional<Image> getById(String id) {

    return repository.findById(id);

  }

  @Override
  public List<Image> search(ImgaeExtension imgaeExtension, String query) {

    return repository.findByExtensionAndNameOrTagsLike(imgaeExtension, query);
  }

}
