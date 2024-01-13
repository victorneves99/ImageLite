package io.github.victorneves.imageliteapi.infra.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import io.github.victorneves.imageliteapi.domain.entity.Image;
import io.github.victorneves.imageliteapi.domain.enums.ImgaeExtension;
import io.github.victorneves.imageliteapi.infra.repository.specs.GenericSpecs;
import io.github.victorneves.imageliteapi.infra.repository.specs.ImageSpecs;

public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {

  /**
   * 
   * @param extension
   * @param query
   * @return
   * 
   *         SELECT * FROM IMAGE WHERE 1 = 1 AND EXTENSION = 'PNG' AND (NAME LIKE
   *         'QUERY' OR TAGS LIKE 'QUERY')
   * 
   */

  default List<Image> findByExtensionAndNameOrTagsLike(ImgaeExtension extension, String query) {

    Specification<Image> spec = Specification.where(GenericSpecs.conjunction());

    if (extension != null) {

      spec = spec.and(ImageSpecs.extensionEqual(extension));
    }

    if (StringUtils.hasText(query)) {

      Specification<Image> nameOrTagsLike = Specification.anyOf(ImageSpecs.nameLike(query), ImageSpecs.tagsLike(query));

      spec = spec.and(nameOrTagsLike);

    }

    return findAll(spec);
  }

}
