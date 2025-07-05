package org.keiron.libraries.web.app.service;

import org.keiron.libraries.web.app.model.PingReq;
import org.keiron.libraries.web.app.model.PingRes;

interface PingSvc {

  PingRes ping(PingReq command);

}
