package com.guardjo.simpleboard.admin.model.response;

import com.guardjo.simpleboard.admin.model.MemberDto;

import java.util.List;

public record MemberResponse(
        Embedded embedded,
        Page page
) {
    public static MemberResponse of(Embedded embedded, Page page) {
        return new MemberResponse(embedded, page);
    }

    public static MemberResponse from(List<MemberDto> memberDtos) {
        return MemberResponse.of(
                Embedded.of(memberDtos),
                Page.of(memberDtos.size(), memberDtos.size(), 1, 1)
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
