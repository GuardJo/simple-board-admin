package com.guardjo.simpleboard.admin.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.guardjo.simpleboard.admin.model.MemberDto;

import java.util.List;

public record MemberResponse(
        @JsonProperty("_embedded")
        Embedded embedded,
        Page page
) {
    public static MemberResponse of(Embedded embedded, Page page) {
        return new MemberResponse(embedded, page);
    }

    public static MemberResponse from(List<MemberDto> memberDtos) {
        return MemberResponse.of(
                Embedded.of(memberDtos),
                Page.of(memberDtos.size(), 0, 1, 1)
        );
    }

    public static MemberResponse empty() {
        return MemberResponse.of(
                Embedded.of(List.of()),
                Page.of(1, 0, 1, 0)
        );
    }
    public List<MemberDto> getMembers() {
        return this.embedded.members();
    }

    record Embedded(List<MemberDto> members) {
        public static Embedded of(List<MemberDto> members) {
            return new Embedded(members);
        }
    }
}
