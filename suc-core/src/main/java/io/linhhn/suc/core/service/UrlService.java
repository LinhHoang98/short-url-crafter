package io.linhhn.suc.core.service;

import io.linhhn.suc.core.dto.UrlDto;
import io.linhhn.suc.core.exception.EntityConflictException;
import io.linhhn.suc.core.model.UrlModel;
import io.linhhn.suc.core.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.UUID;

@Service
public class UrlService {
    private static final String BASE62_ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE62_LENGTH = 8; // Length of the short URL key

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenURL(UrlDto urlDto) {
        String url;
        if (Objects.nonNull(urlDto.getCustomAlias())) {
            UrlModel checkExistedUrl = urlRepository.findByShortUrl(urlDto.getCustomAlias());
            if (Objects.nonNull(checkExistedUrl)) {
                throw new EntityConflictException("Alias [" + urlDto.getCustomAlias() + "] already exists");
            } else {
                url = urlDto.getCustomAlias();
            }
        } else {
            url = encode(urlDto.getLongUrl());
        }

        urlRepository.save(new UrlModel(url , urlDto.getLongUrl()));
        return url;
    }

    public String encode(String rawUrl) {
        try {
            String saltedUrl = rawUrl + UUID.randomUUID().toString();

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(saltedUrl.getBytes(StandardCharsets.UTF_8));

            BigInteger hashInt = new BigInteger(1, hashBytes);

            return toBase62(hashInt).substring(0, BASE62_LENGTH);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    private String toBase62(BigInteger value) {
        StringBuilder sb = new StringBuilder();
        BigInteger base = BigInteger.valueOf(62);
        while (value.compareTo(BigInteger.ZERO) > 0) {
            int index = value.mod(base).intValue();
            sb.append(BASE62_ALPHABET.charAt(index));
            value = value.divide(base);
        }
        return sb.reverse().toString();
    }

    public String getLongUrl(String shortUrl) {
        UrlModel url = urlRepository.findByShortUrl(shortUrl);
        return url.getLongUrl();
    }
}
