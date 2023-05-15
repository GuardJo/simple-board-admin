package com.guardjo.simpleboard.admin.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.guardjo.simpleboard.admin.model.CommentDto;

import java.util.List;

public record CommentResponse(
        @JsonProperty("_embedded")
        Embedded embedded,
        Page page
) {
    public static CommentResponse of(Embedded embedded, Page page) {
        return new CommentResponse(embedded, page);
    }

    public static CommentResponse from(List<CommentDto> commentDtos) {
        return CommentResponse.of(
                Embedded.of(commentDtos),
                Page.of(commentDtos.size(), commentDtos.size(), 1, 1)
        );
    }

    public List<CommentDto> getComments() {
        return this.embedded.comments();
    }

    record Embedded(
            List<CommentDto> comments
    ) {
        public static Embedded of(List<CommentDto> comments) {
            return new Embedded(comments);
        }
    }
}
