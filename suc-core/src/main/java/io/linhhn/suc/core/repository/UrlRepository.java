package io.linhhn.suc.core.repository;

import io.linhhn.suc.core.model.UrlModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<UrlModel, String> {
    UrlModel findByShortUrl(String shortUrl);
}
