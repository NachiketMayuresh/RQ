package com.reliaquest.api.model;

import lombok.*;

/**
 * Employee response wrapper.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseEmployeeWrapper<T> {
    T data;
    String status;
    String error;
}
