/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Duoc
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(clases.service.BoletaFacadeREST.class);
        resources.add(clases.service.ClienteFacadeREST.class);
        resources.add(clases.service.ComunaFacadeREST.class);
        resources.add(clases.service.ContratoFacadeREST.class);
        resources.add(clases.service.DetalleFacadeREST.class);
        resources.add(clases.service.DireccionFacadeREST.class);
        resources.add(clases.service.EstadisticasFacadeREST.class);
        resources.add(clases.service.ProductoFacadeREST.class);
        resources.add(clases.service.PublicacionFacadeREST.class);
        resources.add(clases.service.RegionFacadeREST.class);
        resources.add(clases.service.SolicitudFacadeREST.class);
        resources.add(clases.service.SubastaFacadeREST.class);
        resources.add(clases.service.TarjetaFacadeREST.class);
        resources.add(clases.service.TipoClienteFacadeREST.class);
        resources.add(clases.service.TipoProdFacadeREST.class);
        resources.add(clases.service.TipoTransporteFacadeREST.class);
        resources.add(clases.service.TransporteFacadeREST.class);
    }
    
}
