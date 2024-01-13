package io.github.victorneves.imageliteapi.application.images;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.victorneves.imageliteapi.domain.dto.ImageDTO;
import io.github.victorneves.imageliteapi.domain.entity.Image;
import io.github.victorneves.imageliteapi.domain.enums.ImgaeExtension;
import io.github.victorneves.imageliteapi.domain.mapper.ImageMapper;
import io.github.victorneves.imageliteapi.domain.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/v1/images")
@Slf4j
@RequiredArgsConstructor
public class ImageController {

  private static final String PATH_DELIMITER = "/";

  private final ImageService service;

  private final ImageMapper mapper;

  @PostMapping
  public ResponseEntity save(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
      @RequestParam("tags") List<String> tags) throws IOException {

    log.info("Imagem recebida: name:{}, size:{}", file.getOriginalFilename(), file.getSize());

    Image image = mapper.mapToImage(file, name, tags);

    Image save = service.save(image);

    URI imageURUri = buildImageURL(save);

    return ResponseEntity.created(imageURUri).build();

  }

  @GetMapping("{id}")
  public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) {
    var imageIsPresent = service.getById(id);

    if (imageIsPresent.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    var image = imageIsPresent.get();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(image.getExtension().getMediaType());
    headers.setContentLength(image.getSize());
    headers.setContentDispositionFormData("inline; filename=\"" + image.getFileName() + "\"", image.getFileName());

    return new ResponseEntity<>(image.getFile(), headers, HttpStatus.OK);

  }

  @GetMapping
  public ResponseEntity<List<ImageDTO>> search(
      @RequestParam(value = "extension", required = false) String extension,
      @RequestParam(value = "query", required = false) String query) {

    var result = service.search(ImgaeExtension.ofName(extension), query);

    var images = result.stream().map(image -> {

      var url = buildImageURL(image);

      return mapper.imageToDto(image, url.toString());

    }).collect(Collectors.toList());

    return ResponseEntity.ok(images);
  }

  private URI buildImageURL(Image image) {

    String imagePath = PATH_DELIMITER + image.getId();
    return ServletUriComponentsBuilder.fromCurrentRequestUri().path(imagePath).build().toUri();
  }

}
