package org.keiron.libraries.web.app.service;

import org.keiron.libraries.web.app.model.PingReq;
import org.keiron.libraries.web.app.model.PingRes;
import org.springframework.stereotype.Service;

@Service
public class NoOpsPingSvc implements PingSvc {

  public PingRes ping(PingReq command) {return null;}

}
