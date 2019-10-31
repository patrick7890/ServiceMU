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
@Path("clases.detalle")
public class DetalleFacadeREST {

    @PersistenceContext(unitName = "ServiceMUPU")
    private EntityManager em;

        @POST
    @Path("{boleta}/{publicacion}/{valor}/{cantidad}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(@PathParam("boleta") Long boleta, @PathParam("publicacion") Long publicacion, @PathParam("valor") Long valor, @PathParam("cantidad") Long cantidad) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_DETALLE.INSERTAR")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(4, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(5, Class.class,
                        ParameterMode.OUT)
                .setParameter(1, boleta)
                .setParameter(2, publicacion)
                .setParameter(3, valor)
                .setParameter(4, cantidad);

        query.execute();

        Object resp = query.getOutputParameterValue(5);
        String su = "{"
                + "\"resp\": " + resp
                + "}";
        return Response.ok()
                .entity(su.toString()).build();
    }

    @PUT
    @Path("{boleta}/{publicacion}/{valor}/{cantidad}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("boleta") Long boleta, @PathParam("publicacion") Long publicacion, @PathParam("valor") Long valor, @PathParam("cantidad") Long cantidad) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_DETALLE.MODIFICAR")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(4, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(5, Class.class,
                        ParameterMode.OUT)
                .setParameter(1, boleta)
                .setParameter(2, publicacion)
                .setParameter(3, valor)
                .setParameter(4, cantidad);

        query.execute();

        Object resp = query.getOutputParameterValue(5);
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
                .createStoredProcedureQuery("PKG_MAIPOU_DETALLE.ELIMINAR")
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
                .createStoredProcedureQuery("PKG_MAIPOU_DETALLE.FIND")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Class.class,
                        ParameterMode.REF_CURSOR)
                .setParameter(1, id);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();
        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"boleta\":\"" + aux[0] + "\","
                    + "\"publicacion\":\"" + aux[1] + "\","
                    + "\"valor\":\"" + aux[2] + "\","
                    + "\"cantidad\":\"" + aux[3] + "\""
                    + "},";
        }
        su = "{\"Array\":[" + su.substring(0, su.length() - 1) + "]}";
        if (su.equals("{\"Array\":[]}")) {
            return Response.ok().entity("null").build();
        }
        return Response.ok().entity(su).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAll() {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_DETALLE.SELECT_ALL")
                .registerStoredProcedureParameter(1, Class.class,
                        ParameterMode.REF_CURSOR);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();

        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"boleta\":\"" + aux[0] + "\","
                    + "\"publicacion\":\"" + aux[1] + "\","
                    + "\"valor\":\"" + aux[2] + "\","
                    + "\"cantidad\":\"" + aux[3] + "\""
                    + "},";
        }
        su = "{\"Array\":[" + su.substring(0, su.length() - 1) + "]}";
        if (su.equals("{\"Array\":[]}")) {
            return Response.ok().entity("null").build();
        }
        return Response.ok().entity(su).build();
    }
    @GET
    @Path("Detalle/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response DetalleBoleta(@PathParam("id") Long id) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_DETALLE.TOTAL_BOLETA")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Class.class,
                        ParameterMode.REF_CURSOR)
                 .setParameter(1, id);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();

        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"nombre\":\"" + aux[0] + "\","
                    + "\"valor\":\"" + aux[1] + "\","
                    + "\"cantidad\":\"" + aux[2] + "\","
                    + "\"total_prod\":\"" + aux[3] + "\","
                    + "\"total_bol\":\"" + aux[4] + "\","
                    + "\"total_trans\":\"" + aux[5] + "\","
                    + "\"final\":\"" + aux[6] + "\""
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
