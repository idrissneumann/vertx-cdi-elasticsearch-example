package com.bblvertx.ioc;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.bblvertx.utils.singleton.ESClient;
import com.bblvertx.utils.singleton.PropertyReader;
import com.bblvertx.utils.singleton.RouteContext;
import com.bblvertx.utils.singleton.SeDataSource;

/**
 * Initialisation du contexte de cycle de vie des singletons.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class SeBinder extends AbstractBinder {
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void configure() {
		// bind(implémentation du service)
		// .to(contrat de service)
		// .in(scope du service)

		// Instanciation du singleton pour le propertyReader.
		bind(PropertyReader.class).to(PropertyReader.class).in(Singleton.class);

		// Instanciation du singleton pour la bdd
		bind(SeDataSource.class).to(SeDataSource.class).in(Singleton.class);

		// Instanciation du singleton pour elastic search
		bind(ESClient.class).to(ESClient.class).in(Singleton.class);

		// Instanciation du context pour les routes
		bind(RouteContext.class).to(RouteContext.class).in(Singleton.class);
	}
}
