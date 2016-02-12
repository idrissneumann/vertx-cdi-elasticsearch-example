package com.bblvertx.route.impl;

import com.bblvertx.indexation.adapter.impl.UserIndexationSingleAdapter;
import com.bblvertx.pojo.vo.UserVO;
import com.bblvertx.route.AbstractIndexingSingleRoute;
import com.bblvertx.utils.singleton.RouteContext;

import io.vertx.ext.web.Router;

/**
 * Route to index a single user.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class IndexingSingleUserRoute extends AbstractIndexingSingleRoute<UserVO> {
  /**
   * Constructor.
   * 
   * @param url
   * @param contentType
   * @param router
   * @param ctx
   */
  public IndexingSingleUserRoute(String url, String contentType, Router router,
      RouteContext ctx) {
    super(url, contentType, router, ctx);
    this.adapter = new UserIndexationSingleAdapter(ctx);
  }
}
