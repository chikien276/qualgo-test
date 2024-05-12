package com.qualgo.kien.infrastructure.web.controller.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateChannelRequest {
    String channelName;
}
