package io.linhhn.suc.core.controller;

import io.linhhn.suc.core.dto.UrlDto;
import io.linhhn.suc.core.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/urls")
    public ResponseEntity<String> shorten(@Valid @RequestBody UrlDto urlDto) {
        return new ResponseEntity<>(urlService.shortenURL(urlDto), HttpStatus.OK);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> getLongUrl(@PathVariable String shortUrl) {
        return new ResponseEntity<>(urlService.getLongUrl(shortUrl), HttpStatus.OK);
    }
}
