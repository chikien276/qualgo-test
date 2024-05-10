package com.qualgo.kien.application.exception;

public class ChannelNotFoundException extends BaseException {
  public ChannelNotFoundException(Long channelId) {
    super("Channel not found for id " + channelId);
  }

  @Override
  public int getErrorCode() {
    return 2;
  }
}
