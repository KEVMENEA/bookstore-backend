package com.think4tech.bookstore.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorRequestDTOTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void bindsCanonicalImageUrlProperty() throws Exception {
        AuthorRequestDTO dto = objectMapper.readValue("""
                {
                  "name": "Herbert Schildt",
                  "bio": "Java programming author",
                  "imageUrl": "https://example.com/author.jpg"
                }
                """, AuthorRequestDTO.class);

        assertThat(dto.getImageUrl()).isEqualTo("https://example.com/author.jpg");
    }

    @ParameterizedTest
    @ValueSource(strings = {"imageURL", "image_url", "imageULR"})
    void bindsSupportedImageUrlAliases(String propertyName) throws Exception {
        String json = """
                {
                  "name": "Herbert Schildt",
                  "bio": "Java programming author",
                  "%s": "https://example.com/author.jpg"
                }
                """.formatted(propertyName);

        AuthorRequestDTO dto = objectMapper.readValue(json, AuthorRequestDTO.class);

        assertThat(dto.getImageUrl()).isEqualTo("https://example.com/author.jpg");
    }
}
