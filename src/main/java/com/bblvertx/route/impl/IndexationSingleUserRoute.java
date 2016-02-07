package com.bblvertx.route.impl;

import com.bblvertx.indexation.adapter.impl.UserIndexationSingleAdapter;
import com.bblvertx.pojo.vo.UserVO;
import com.bblvertx.route.AbstractIndexationSingleRoute;
import com.bblvertx.utils.singleton.RouteContext;

import io.vertx.ext.web.Router;

/**
 * Route pour indexer un utilisateur du chat en particulier.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class IndexationSingleUserRoute extends AbstractIndexationSingleRoute<UserVO> {
	/**
	 * Constructeur.
	 * 
	 * @param url
	 * @param contentType
	 * @param router
	 * @param ctx
	 */
	public IndexationSingleUserRoute(String url, String contentType, Router router, RouteContext ctx) {
		super(url, contentType, router, ctx);
		this.adapter = new UserIndexationSingleAdapter(ctx);
	}
}
