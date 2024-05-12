package com.qualgo.kien.infrastructure.web.controller.response;

import java.util.Collection;
import lombok.*;

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
