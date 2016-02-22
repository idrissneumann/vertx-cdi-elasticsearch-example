package com.bblvertx.route.impl;

import com.bblvertx.indexation.adapter.impl.UserIndexationDeltaAdapter;
import com.bblvertx.pojo.vo.UserVO;
import com.bblvertx.route.AbstractJdbcIndexingDeltaRoute;
import com.bblvertx.utils.singleton.impl.RouteContext;

import io.vertx.ext.web.Router;

/**
 * Route to index all of chat user.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class JdbcIndexingUserRoute extends AbstractJdbcIndexingDeltaRoute<UserVO> {
  /**
   * Constructor.
   * 
   * @param url
   * @param contentType
   * @param router
   * @param ctx
   */
  public JdbcIndexingUserRoute(String url, String contentType, Router router, RouteContext ctx) {
    super(url, contentType, router, ctx);
    this.adapter = new UserIndexationDeltaAdapter(ctx);
  }
}
