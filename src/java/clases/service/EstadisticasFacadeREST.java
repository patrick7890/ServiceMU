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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Patricio
 */
@Stateless
@Path("estadisticas")
public class EstadisticasFacadeREST {

    @PersistenceContext(unitName = "ServiceMUPU")
    private EntityManager em;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAll() {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_ESTADISTICAS.GEN")
                .registerStoredProcedureParameter(1, Class.class,
                        ParameterMode.REF_CURSOR);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();

        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"productor\":\"" + aux[0] + "\","
                    + "\"comision\":\"" + aux[1] + "\","
                    + "\"transporteM\":\"" + aux[2] + "\","
                    + "\"Transportista\":\"" + aux[3] + "\""
                    + "},";
        }
        su = "{\"Array\":[" + su.substring(0, su.length() - 1) + "]}";
        if (su.equals("{\"Array\":[]}")) {
            return Response.ok().entity("null").build();
        }
        return Response.ok().entity(su).build();
    }

    @GET
    @Path("mes/{mes}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findMonth(@PathParam("mes") Long mes) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_ESTADISTICAS.MES")
                .registerStoredProcedureParameter(1, Class.class,
                        ParameterMode.REF_CURSOR)
                .registerStoredProcedureParameter(2, Long.class,
                        ParameterMode.IN)
                .setParameter(2, mes);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();

        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"productor\":\"" + aux[0] + "\","
                    + "\"comision\":\"" + aux[1] + "\","
                    + "\"transporteM\":\"" + aux[2] + "\","
                    + "\"Transportista\":\"" + aux[3] + "\","
                    + "\"dia\":\"" + aux[4] + "\""
                    + "},";
        }
        su = "{\"Array\":[" + su.substring(0, su.length() - 1) + "]}";
        if (su.equals("{\"Array\":[]}")) {
            return Response.ok().entity("null").build();
        }
        return Response.ok().entity(su).build();
    }

    @GET
    @Path("anio/{anio}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAnio(@PathParam("anio") Long anio) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_ESTADISTICAS.ANIO")
                .registerStoredProcedureParameter(1, Class.class,
                        ParameterMode.REF_CURSOR)
                .registerStoredProcedureParameter(2, Long.class,
                        ParameterMode.IN)
                .setParameter(2, anio);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();

        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += "{\"productor\":\"" + aux[0] + "\","
                    + "\"comision\":\"" + aux[1] + "\","
                    + "\"transporteM\":\"" + aux[2] + "\","
                    + "\"Transportista\":\"" + aux[3] + "\","
                    + "\"mes\":\"" + aux[4] + "\""
                    + "},";
        }
        su = "{\"Array\":[" + su.substring(0, su.length() - 1) + "]}";
        if (su.equals("{\"Array\":[]}")) {
            return Response.ok().entity("null").build();
        }
        return Response.ok().entity(su).build();
    }

    @GET
    @Path("meses/{anio}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response anios(@PathParam("anio") Long anio) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_ESTADISTICAS.MESES")
                .registerStoredProcedureParameter(1, Class.class,
                        ParameterMode.REF_CURSOR)
                .registerStoredProcedureParameter(2, Long.class,
                        ParameterMode.IN)
                .setParameter(2, anio);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();

        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += aux[0] + ",";
        }
        su = "{\"Array\":[" + su.substring(0, su.length() - 1) + "]}";
        if (su.equals("{\"Array\":[]}")) {
            return Response.ok().entity("null").build();
        }
        return Response.ok().entity(su).build();
    }

    @GET
    @Path("anios/")
    @Produces({MediaType.APPLICATION_JSON})
    public Response meses(@PathParam("anio") Long anio) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("PKG_MAIPOU_ESTADISTICAS.ANIOS")
                .registerStoredProcedureParameter(1, Class.class,
                        ParameterMode.REF_CURSOR);

        query.execute();
        List<Object[]> SELECT_ALL = query.getResultList();

        String su = " ";

        for (Object[] aux : SELECT_ALL) {
            su += aux[0] + ",";
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
