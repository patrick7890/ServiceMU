/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Duoc
 */
@Stateless
@Path("clases.cliente")
public class ClienteFacadeREST {

    @PersistenceContext(unitName = "ServiceMUPU")
    private EntityManager em;

    @POST
    @Path("{rut}/{nombre}/{apellido}/{usuario}/{pass}/{correo}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(@PathParam("rut") String rut, @PathParam("nombre") String nombre, @PathParam("apellido") String apellido, @PathParam("usuario") String usuario, @PathParam("pass") String pass, @PathParam("correo") String correo) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_CLIENTE.INSERTAR")
                .registerStoredProcedureParameter(1, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(4, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(5, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(6, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(7, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(8, Class.class,
                        ParameterMode.OUT)
                .setParameter(1, rut)
                .setParameter(2, nombre)
                .setParameter(3, apellido)
                .setParameter(4, usuario)
                .setParameter(5, pass)
                .setParameter(6, correo)
                .setParameter(7, Long.parseLong("1"));

        query.execute();

        Object resp = query.getOutputParameterValue(8);
        String su = "{"
                + "\"resp\": " + resp
                + "}";
        return Response.ok()
                .entity(su.toString())
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();
    }

    @PUT
    @Path("{id}/{rut}/{nombre}/{apellido}/{usuario}/{pass}/{correo}/{estado}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Long id, @PathParam("rut") String rut, @PathParam("nombre") String nombre, @PathParam("apellido") String apellido, @PathParam("usuario") String usuario, @PathParam("pass") String pass, @PathParam("correo") String correo, @PathParam("correo") Long estado) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_CLIENTE.MODIFICAR")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(4, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(5, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(6, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(7, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(8, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(9, Class.class,
                        ParameterMode.OUT)
                .setParameter(1, id)
                .setParameter(2, rut)
                .setParameter(3, nombre)
                .setParameter(4, apellido)
                .setParameter(5, usuario)
                .setParameter(6, pass)
                .setParameter(7, correo)
                .setParameter(8, estado);

        query.execute();

        Object resp = query.getOutputParameterValue(9);
        String su = "{"
                + "\"resp\":" + resp
                + "}";
        return Response.ok()
                .entity(su.toString())
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Long id) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_CLIENTE.DELETE")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Class.class,
                        ParameterMode.OUT)
                .setParameter(1, id);

        query.execute();

        Object resp = query.getOutputParameterValue(2);
        String su = "{"
                + "\"resp\": " + resp
                + "}";
        return Response.ok()
                .entity(su.toString())
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") String id) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_CLIENTE.FIND")
                .registerStoredProcedureParameter(1, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Class.class,
                        ParameterMode.REF_CURSOR)
                .setParameter(1, id);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();

        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"id\":\"" + aux[0] + "\","
                    + "\"rut\":\"" + aux[1] + "\","
                    + "\"nombre\":\"" + aux[2] + "\","
                    + "\"apellido\":\"" + aux[3] + "\","
                    + "\"usuario\":\"" + aux[4] + "\","
                    + "\"pass\":\"" + aux[5] + "\","
                    + "\"correo\":\"" + aux[6] + "\","
                    + "\"estado\":\"" + aux[7] + "\""
                    + "},";
        }
        su = "{\"Array\":[" + su.substring(0, su.length() - 1) + "]}";
        if (su.equals("{\"Array\":[]}")) {
            return Response.ok().entity("null")
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .allow("OPTIONS").build();
        }
        return Response.ok().entity(su)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAll() {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_CLIENTE.SELECT_ALL")
                .registerStoredProcedureParameter(1, Class.class,
                        ParameterMode.REF_CURSOR);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();

        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"id\":\"" + aux[0] + "\","
                    + "\"rut\":\"" + aux[1] + "\","
                    + "\"nombre\":\"" + aux[2] + "\","
                    + "\"apellido\":\"" + aux[3] + "\","
                    + "\"usuario\":\"" + aux[4] + "\","
                    + "\"pass\":\"" + aux[5] + "\","
                    + "\"correo\":\"" + aux[6] + "\","
                    + "\"estado\":\"" + aux[7] + "\""
                    + "},";
        }
        su = "{\"Array\":[" + su.substring(0, su.length() - 1) + "]}";
        if (su.equals("{\"Array\":[]}")) {
            System.out.println("wut");
            return Response.ok().entity("null")
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .allow("OPTIONS").build();
        }
        return Response.ok().entity(su)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Path("Login/{usu}/{pass}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response Login(@PathParam("usu") String usu, @PathParam("pass") String pass) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_CLIENTE.LOGIN")
                .registerStoredProcedureParameter(1, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, Class.class,
                        ParameterMode.REF_CURSOR)
                .setParameter(1, usu)
                .setParameter(2, pass);

        query.execute();

        List<Object[]> usuario = query.getResultList();
        String su = " ";
        for (Object[] aux : usuario) {
            su = "{"
                    + "\"id\": " + aux[0].toString() + ","
                    + "\"rut\": \"" + aux[1].toString() + "\","
                    + "\"nombre\": \"" + aux[2].toString() + "\","
                    + "\"apellido\": \"" + aux[3].toString() + "\","
                    + "\"usu\": \"" + aux[4].toString() + "\","
                    + "\"pass\": \"" + aux[5].toString() + "\","
                    + "\"correo\": \"" + aux[6].toString() + "\","
                    + "\"tipo\": \"" + aux[7].toString() + "\""
                    + "},";

        }
        su = "{\"Array\":[" + su.substring(0, su.length() - 1) + "]}";
        if (su.equals("{\"Array\":[]}")) {
            return Response.ok()
                    .entity("null")
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .allow("OPTIONS").build();
        }
        return Response.ok()
                .entity(su.toString())
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .allow("OPTIONS").build();
    }
}
