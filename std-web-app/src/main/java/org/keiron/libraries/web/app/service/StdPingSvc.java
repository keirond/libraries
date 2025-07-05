package org.keiron.libraries.web.app.service;

import lombok.RequiredArgsConstructor;
import org.keiron.libraries.web.app.model.PingReq;
import org.keiron.libraries.web.app.model.PingRes;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class StdPingSvc implements PingSvc {

  public PingRes ping(PingReq command) {
    return new PingRes().setReqEpochMillis(command.getReqEpochMillis())
               .setResEpochMillis(Instant.now().toEpochMilli());
  }

}
