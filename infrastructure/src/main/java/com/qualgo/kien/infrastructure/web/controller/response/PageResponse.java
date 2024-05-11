package com.qualgo.kien.infrastructure.web.controller.response;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
  Collection<T> data;

  int pageSize;

  String nextPageToken;
}
