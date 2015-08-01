package nl.first8.ledcube.rest;

import java.util.Arrays;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import nl.first8.ledcube.CubeOutput;
import nl.first8.ledcube.StringCubeUtil;

/**
 * Rest endpoint for our web cube.
 */
@Path("/cube")
@RequestScoped
public class CubeService {

	@POST
	@Path("/{x}/{y}/{z}/1")
	public Response pixelOn(@PathParam("x") int x, @PathParam("y") int y, @PathParam("z") int z) {
		System.err.println("put (" + x + "," + y + "," + z + ") = " + true);
		LedCubeRestApplication.getInstance().setPixel(x, y, z, true);
		LedCubeRestApplication.getInstance().flush();
		return Response.status(200).build();
	}

	@POST
	@Path("/{x}/{y}/{z}/0")
	public Response pixelOff(@PathParam("x") int x, @PathParam("y") int y, @PathParam("z") int z) {
		System.err.println("put (" + x + "," + y + "," + z + ") = " + false);
		LedCubeRestApplication.getInstance().setPixel(x, y, z, false);
		LedCubeRestApplication.getInstance().flush();
		return Response.status(200).build();
	}

	@POST
	@Path("/bulk")
	@Consumes("application/json")
	public Response bulk(int image[][][]) {
		System.err.println("put bulk ("+Arrays.toString(image)+") = " + false);
		for (int x=0; x<8; x++) {
			for (int y=0; y<8; y++) {
				for (int z=0; z<8; z++) {
				    LedCubeRestApplication.getInstance().setPixel(x, y, z, image[x][y][z]!=0);
				}
			}
		}
		LedCubeRestApplication.getInstance().flush();
		return Response.status(200).build();
	}

	@POST
	@Path("/bulk")
	public Response bulk( String image) {
		boolean[][][] cube = StringCubeUtil.stringToArray(image);
		LedCubeRestApplication.getInstance().setCube(cube);
		LedCubeRestApplication.getInstance().flush();
		return Response.status(200).build();
	}

	@GET
	@Path("/bulk")
	public Response bulk() {
		CubeOutput cube = LedCubeRestApplication.getInstance();
		return Response.ok(StringCubeUtil.cubeToString(cube)).build();
	}
	
	@GET
	@Path("/{x}/{y}/{z}")
	public Response getPixel(@PathParam("x") int x, @PathParam("y") int y, @PathParam("z") int z) {
		boolean pixel = LedCubeRestApplication.getInstance().getPixel(x, y, z);
		System.err.println("get (" + x + "," + y + "," + z + ") = " + pixel + "\n");
		return Response.ok(pixel?"1":"0").build();
	}

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
