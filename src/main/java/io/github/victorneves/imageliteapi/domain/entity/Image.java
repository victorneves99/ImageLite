package io.github.victorneves.imageliteapi.domain.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.github.victorneves.imageliteapi.domain.enums.ImgaeExtension;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column
  private String name;

  @Column
  private Long size;

  @Column
  @Enumerated(EnumType.STRING)
  private ImgaeExtension extension;

  @Column
  @CreatedDate
  private LocalDateTime uploadDate;

  @Column
  private String tags;

  @Column
  @Lob
  private byte[] file;

  public String getFileName() {
    return getName().concat(".").concat(getExtension().name());
  }

}
