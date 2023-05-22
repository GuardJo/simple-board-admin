package com.guardjo.simpleboard.admin.model.response;

public record Page(
        int size,
        int totalElements,
        int totalPages,
        int number
) {
    public static Page of(int size, int totalElements, int totalPages, int number) {
        return new Page(size, totalElements, totalPages, number);
    }
}
