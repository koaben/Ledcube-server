/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.rshelloworld;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * A simple JAX-RS 2.0 REST service which is able to say hello to the world using an injected HelloService CDI bean.
 * The {@link javax.ws.rs.Path} class annotation value is related to the {@link org.jboss.as.quickstarts.rshelloworld.HelloWorldApplication}'s path.
 * 
 * @author gbrey@redhat.com
 * @author Eduardo Martins
 * 
 */
@Path("/cube")
public class HelloWorld {

	@GET
	@Path("/info")
	public Response getInfo() {
		System.err.println("info");
		String info = "Use GET  /rest/cube/x/y/z to read pixel state\n" + 
					"Use POST /rest/cube/x/y/z/1 to turn pixel on\n" +
					"Use POST /rest/cube/x/y/z/0 to turn pixel on\n" +
					"Use POST /rest/cube/bulk to post a text file containing 512 0's and 1's\n";
		return Response.ok(info).build();
	}

}
