package com.bblvertx.route.impl;

import com.bblvertx.indexation.adapter.impl.UserIndexationDeltaAdapter;
import com.bblvertx.pojo.vo.UserVO;
import com.bblvertx.route.AbstractIndexingDeltaRoute;
import com.bblvertx.utils.singleton.RouteContext;

import io.vertx.ext.web.Router;

/**
 * Route to index all of chat user.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class IndexingUserRoute extends AbstractIndexingDeltaRoute<UserVO> {
  /**
   * Constructor.
   * 
   * @param url
   * @param contentType
   * @param router
   * @param ctx
   */
  public IndexingUserRoute(String url, String contentType, Router router, RouteContext ctx) {
    super(url, contentType, router, ctx);
    this.adapter = new UserIndexationDeltaAdapter(ctx);
  }
}
