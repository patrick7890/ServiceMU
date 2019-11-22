/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.service;

import java.math.BigInteger;
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
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;

/**
 *
 * @author Duoc
 */
@Stateless
@Path("clases.publicacion")
public class PublicacionFacadeREST {

    @PersistenceContext(unitName = "ServiceMUPU")
    private EntityManager em;

    @POST
    @Path("{valor}/{stock}/{producto}/{proveedor}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(@PathParam("valor") Long valor, @PathParam("stock") Long stock, @PathParam("producto") Long producto, @PathParam("proveedor") Long proveedor) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_PUBLICACION.INSERTAR")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(4, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(5, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(6, Class.class,
                        ParameterMode.OUT)
                .setParameter(1, valor)
                .setParameter(2, stock)
                .setParameter(3, 0)
                .setParameter(4, producto)
                .setParameter(5, proveedor);

        query.execute();

        Object resp = query.getOutputParameterValue(6);
        String su = "{"
                + "\"resp\": " + resp
                + "}";
        return Response.ok()
                .entity(su.toString()).build();

    }

    @PUT
    @Path("publicar/{id}/{estado}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response estado(@PathParam("id") Long id, @PathParam("estado") Long estado) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_PUBLICACION.PUBLICAR")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, Class.class,
                        ParameterMode.OUT)
                .setParameter(1, id)
                .setParameter(2, estado);
        query.execute();

        Object resp = query.getOutputParameterValue(3);
        String su = "{"
                + "\"resp\":" + resp
                + "}";
        return Response.ok()
                .entity(su.toString()).build();
    }

    @PUT
    @Path("{id}/{valor}/{stock}/{estado}/{producto}/{proveedor}/{ruta}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Long id, @PathParam("valor") Long valor, @PathParam("stock") Long stock, @PathParam("estado") Long estado, @PathParam("producto") Long producto, @PathParam("proveedor") Long proveedor,@PathParam("ruta") String ruta) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_PUBLICACION.MODIFICAR")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(4, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(5, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(6, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(7, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(8, Class.class,
                        ParameterMode.OUT)
                .setParameter(1, id)
                .setParameter(2, valor)
                .setParameter(3, stock)
                .setParameter(4, estado)
                .setParameter(5, producto)
                .setParameter(6, proveedor)
                .setParameter(7, ruta);

        query.execute();

        Object resp = query.getOutputParameterValue(8);
        String su = "{"
                + "\"resp\":" + resp
                + "}";
        return Response.ok()
                .entity(su.toString()).build();
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Long id) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_PUBLICACION.ELIMINAR")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Class.class,
                        ParameterMode.OUT)
                .setParameter(1, id);

        query.execute();

        Object resp = query.getOutputParameterValue(2);
        String su = "{"
                + "\"resp\":" + resp
                + "}";
        return Response.ok()
                .entity(su.toString()).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_PUBLICACION.FIND")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Class.class,
                        ParameterMode.REF_CURSOR)
                .setParameter(1, id);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();
        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"id\":\"" + aux[0] + "\","
                    + "\"valor\":\"" + aux[1] + "\","
                    + "\"stock\":\"" + aux[2] + "\","
                    + "\"estado\":\"" + aux[3] + "\","
                    + "\"producto\":\"" + aux[4] + "\","
                    + "\"proveedor\":\"" + aux[5] + "\","
                    + "\"ruta\":\"" + aux[6] + "\""
                    + "},";
        }
        su = "{\"Resp\":[" + su.substring(0, su.length() - 1) + "]}";
        if (su.equals("{\"Array\":[]}")) {
            return Response.ok().entity("null").build();
        }
        return Response.ok().entity(su).build();
    }

    @GET
    @Path("FindWithName/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findwithName(@PathParam("id") Long id) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_PUBLICACION.FINDWITHNAME")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Class.class,
                        ParameterMode.REF_CURSOR)
                .setParameter(1, id);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();
        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"id\":\"" + aux[0] + "\","
                    + "\"valor\":\"" + aux[1] + "\","
                    + "\"stock\":\"" + aux[2] + "\","
                    + "\"estado\":\"" + aux[3] + "\","
                    + "\"producto\":\"" + aux[4] + "\","
                    + "\"proveedor\":\"" + aux[5] + "\","
                    + "\"ruta\":\"" + aux[6] + "\","
                    + "},";
        }
        su = "{\"Resp\":[" + su.substring(0, su.length() - 1) + "]}";
        if (su.equals("{\"Array\":[]}")) {
            return Response.ok().entity("null").build();
        }
        return Response.ok().entity(su).build();
    }

    @GET

    @Produces({MediaType.APPLICATION_JSON})
    public Response findAll() {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_PUBLICACION.SELECT_ALL")
                .registerStoredProcedureParameter(1, Class.class,
                        ParameterMode.REF_CURSOR);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();

        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"id\":\"" + aux[0] + "\","
                    + "\"valor\":\"" + aux[1] + "\","
                    + "\"stock\":\"" + aux[2] + "\","
                    + "\"estado\":\"" + aux[3] + "\","
                    + "\"producto\":\"" + aux[4] + "\","
                    + "\"proveedor\":\"" + aux[5] + "\","
                    + "\"ruta\":\"" + aux[6] + "\""
                    + "},";
        }
        su = "{\"Array\":[" + su.substring(0, su.length() - 1) + "]}";
        if (su.equals("{\"Array\":[]}")) {
            return Response.ok().entity("null").build();
        }
        return Response.ok().entity(su).build();
    }

    @GET
    @Path("Activo/{estado}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findActivo(@PathParam("estado") Long estado) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_PUBLICACION.SELECT_ESTADO")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Class.class,
                        ParameterMode.REF_CURSOR)
                .setParameter(1, estado);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();

        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"id\":\"" + aux[0] + "\","
                    + "\"valor\":\"" + aux[1] + "\","
                    + "\"stock\":\"" + aux[2] + "\","
                    + "\"estado\":\"" + aux[3] + "\","
                    + "\"producto\":\"" + aux[4] + "\","
                    + "\"proveedor\":\"" + aux[5] + "\","
                    + "\"ruta\":\"" + aux[6] + "\""
                    + "},";
        }
        su = "{\"Array\":[" + su.substring(0, su.length() - 1) + "]}";
        if (su.equals("{\"Array\":[]}")) {
            return Response.ok().entity("null").build();
        }
        return Response.ok().entity(su).build();
    }

    protected EntityManager getEntityManager() {
        return em;
    }

}
