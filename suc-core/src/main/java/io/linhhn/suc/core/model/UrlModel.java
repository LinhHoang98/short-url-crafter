package io.linhhn.suc.core.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "urls")
public class UrlModel {
    @Id
    private String shortUrl;
    private String longUrl;

}
