package nl.first8.ledcube.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import nl.first8.ledcube.CubeFactory;

public class LedCubeRestApplication extends Application {
	HashSet<Object> singletons = new HashSet<Object>();

	public LedCubeRestApplication() {
		singletons.add(new CubeService());
		CubeFactory.getInstance();
	}

	@Override
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> set = new HashSet<Class<?>>();
		return set;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
