package com.bblvertx.route.impl;

import com.bblvertx.indexation.adapter.impl.JdbcUserIndexationSingleAdapter;
import com.bblvertx.pojo.vo.UserVO;
import com.bblvertx.route.AbstractIndexingSingleRoute;
import com.bblvertx.utils.singleton.impl.RouteContext;

import io.vertx.ext.web.Router;

/**
 * Route to index a single user.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class JdbcIndexingSingleUserRoute extends AbstractIndexingSingleRoute<UserVO> {
  /**
   * Constructor.
   * 
   * @param url
   * @param contentType
   * @param router
   * @param ctx
   */
  public JdbcIndexingSingleUserRoute(String url, String contentType, Router router,
      RouteContext ctx) {
    super(url, contentType, router, ctx);
    this.adapter = new JdbcUserIndexationSingleAdapter(ctx);
  }
}
