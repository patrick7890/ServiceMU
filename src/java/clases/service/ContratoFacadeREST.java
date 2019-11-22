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
@Path("clases.contrato")
public class ContratoFacadeREST {

    @PersistenceContext(unitName = "ServiceMUPU")
    private EntityManager em;

    @POST
    @Path("{cliente}/{ruta}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(@PathParam("cliente") Long cliente, @PathParam("ruta") String ruta) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_CONTRATO.INSERTAR")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, Class.class,
                        ParameterMode.OUT)
                .setParameter(1, cliente)
                .setParameter(2, ruta);

        query.execute();

        Object resp = query.getOutputParameterValue(3);
        String su = "{"
                + "\"resp\": " + resp
                + "}";
        return Response.ok()
                .entity(su.toString()).build();

    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("cliente") Long cliente, @PathParam("contrato") Long contrato, @PathParam("fecha") String fecha, @PathParam("ruta") String ruta) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_CONTRATO.MODIFICAR")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(3, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(4, String.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(5, Class.class,
                        ParameterMode.OUT)
                .setParameter(1, cliente)
                .setParameter(2, contrato)
                .setParameter(3, fecha)
                .setParameter(4, ruta);

        query.execute();

        Object resp = query.getOutputParameterValue(7);
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
                .createStoredProcedureQuery("PKG_MAIPOU_CONTRATO.ELIMINAR")
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
                .createStoredProcedureQuery("PKG_MAIPOU_CONTRATO.FIND")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Class.class,
                        ParameterMode.REF_CURSOR)
                .setParameter(1, id);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();
        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"Contrato\":\"" + aux[0] + "\","
                    + "\"Fecha\":\"" + aux[1] + "\","
                    + "\"Ruta\":\"" + aux[2] + "\","
                    + "\"nombre\":\"" + aux[3] + "\","
                    + "\"rut\":\"" + aux[4] + "\""
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
                .createStoredProcedureQuery("PKG_MAIPOU_CONTRATO.SELECT_ALL")
                .registerStoredProcedureParameter(1, Class.class,
                        ParameterMode.REF_CURSOR);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();

        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"Contrato\":\"" + aux[0] + "\","
                    + "\"Fecha\":\"" + aux[1] + "\","
                    + "\"Ruta\":\"" + aux[2] + "\","
                    + "\"nombre\":\"" + aux[3] + "\","
                    + "\"rut\":\"" + aux[4] + "\""
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

    @GET
    @Path("cli/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findCli(@PathParam("id") Long id) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_CONTRATO.FINDCLI")
                .registerStoredProcedureParameter(1, Long.class,
                        ParameterMode.IN)
                .registerStoredProcedureParameter(2, Class.class,
                        ParameterMode.REF_CURSOR)
                .setParameter(1, id);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();
        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"Contrato\":\"" + aux[0] + "\","
                    + "\"Fecha\":\"" + aux[1] + "\","
                    + "\"Ruta\":\"" + aux[2] + "\","
                    + "\"nombre\":\"" + aux[3] + "\","
                    + "\"rut\":\"" + aux[4] + "\""
                    + "},";
        }
        su = "{\"Array\":[" + su.substring(0, su.length() - 1) + "]}";
        if (su.equals("{\"Array\":[]}")) {
            return Response.ok().entity("null").build();
        }
        return Response.ok().entity(su).build();
    }
}
