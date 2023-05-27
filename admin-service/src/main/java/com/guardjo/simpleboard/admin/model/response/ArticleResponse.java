package com.guardjo.simpleboard.admin.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.guardjo.simpleboard.admin.model.ArticleDto;

import java.util.List;

public record ArticleResponse(
        @JsonProperty("_embedded")
        Embedded embedded,
        Page page
) {
    public static ArticleResponse of(Embedded embedded, Page page) {
        return new ArticleResponse(embedded, page);
    }

    public static ArticleResponse from(List<ArticleDto> articles) {
        return ArticleResponse.of(
                Embedded.of(articles),
                Page.of(articles.size(), 0, 1, 0)
        );
    }

    public static ArticleResponse empty() {
        return ArticleResponse.of(
                Embedded.of(List.of()),
                Page.of(1, 0, 1, 0)
        );
    }

    public List<ArticleDto> getArticles() {
        return this.embedded.articles();
    }

    record Embedded(List<ArticleDto> articles) {
        public static Embedded of(List<ArticleDto> articles) {
            return new Embedded(articles);
        }
    }
}
