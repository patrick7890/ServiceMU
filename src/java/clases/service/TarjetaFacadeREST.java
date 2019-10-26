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
@Path("clases.tarjeta")
public class TarjetaFacadeREST {

    @PersistenceContext(unitName = "ServiceMUPU")
    private EntityManager em;

    @POST
    @Path("{num}/{cvv}/{exp}/{cliente}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(@PathParam("num") Long num, @PathParam("cvv") Long cvv, @PathParam("exp") String exp, @PathParam("cliente") Long cliente) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_TARJETA.INSERTAR")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(4, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(5, Long.class,
                        ParameterMode.OUT)
                .setParameter(1, num)
                .setParameter(2, cvv)
                .setParameter(3, exp)
                .setParameter(4, cliente);

        query.execute();

        Object resp = query.getOutputParameterValue(5);
        String su = "{"
                + "\"resp\": " + resp
                + "}";
        return Response.ok()
                .entity(su.toString()).build();

    }

    @PUT
    @Path("{id}/{num}/{cvv}/{exp}/{cliente}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Long id,@PathParam("num") Long num, @PathParam("cvv") Long cvv, @PathParam("exp") String exp, @PathParam("cliente") Long cliente) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_TARJETA.MODIFICAR")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(4, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(5, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(6, Class.class,
                        ParameterMode.OUT)
                .setParameter(1, id)
                .setParameter(2, num)
                .setParameter(3, cvv)
                .setParameter(4, exp)
                .setParameter(5, cliente);

        query.execute();

        Object resp = query.getOutputParameterValue(6);
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
                .createStoredProcedureQuery("PKG_MAIPOU_TARJETA.ELIMINAR")
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
                .createStoredProcedureQuery("PKG_MAIPOU_TARJETA.FIND")
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
                    + "\"num\":\"" + aux[1] + "\","
                    + "\"cvv\":\"" + aux[2] + "\","
                    + "\"exp\":\"" + aux[3] + "\","
                    + "\"cliente\":\"" + aux[4] + "\""
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
                .createStoredProcedureQuery("PKG_MAIPOU_TARJETA.SELECT_ALL")
                .registerStoredProcedureParameter(1, Class.class,
                        ParameterMode.REF_CURSOR);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();

        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"id\":\"" + aux[0] + "\","
                    + "\"num\":\"" + aux[1] + "\","
                    + "\"cvv\":\"" + aux[2] + "\","
                    + "\"exp\":\"" + aux[3] + "\","
                    + "\"cliente\":\"" + aux[4] + "\""
                    + "},";
        }
        su = "{\"Array\":[" + su.substring(0, su.length() - 1) + "]}";
        if (su.equals("{\"Array\":[]}")) {
            return Response.ok().entity("null").build();
        }
        return Response.ok().entity(su).build();
    }
    @GET
    @Path("Tarjeta/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response Tarjeta(@PathParam("id") Long id) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_TARJETA.TARJETACLIENTE")
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
                    + "\"num\":\"" + aux[1] + "\","
                    + "\"cvv\":\"" + aux[2] + "\","
                    + "\"exp\":\"" + aux[3] + "\","
                    + "\"cliente\":\"" + aux[4] + "\""
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
