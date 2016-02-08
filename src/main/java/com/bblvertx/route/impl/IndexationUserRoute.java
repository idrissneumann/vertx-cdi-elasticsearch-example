package com.bblvertx.route.impl;

import com.bblvertx.indexation.adapter.impl.UserIndexationDeltaAdapter;
import com.bblvertx.pojo.vo.UserVO;
import com.bblvertx.route.AbstractIndexationDeltaRoute;
import com.bblvertx.utils.singleton.RouteContext;

import io.vertx.ext.web.Router;

/**
 * Route to index all of chat user.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class IndexationUserRoute extends AbstractIndexationDeltaRoute<UserVO> {
  /**
   * Constructor.
   * 
   * @param url
   * @param contentType
   * @param router
   * @param ctx
   */
  public IndexationUserRoute(String url, String contentType, Router router, RouteContext ctx) {
    super(url, contentType, router, ctx);
    this.adapter = new UserIndexationDeltaAdapter(ctx);
  }
}
