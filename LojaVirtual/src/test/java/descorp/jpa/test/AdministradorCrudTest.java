/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import descorp.jpa.Administrador;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CacheRetrieveMode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author marcosbrasil98
 */
public class AdministradorCrudTest extends GenericTest {
    
    @Test
    public void persistirAdm(){
       logger.info("Executando persistirADM()");
        Administrador adm = criarADM();
        em.persist(adm);
        em.flush();
        assertNotNull(adm.getId());
        assertNotNull(adm.getNome()); 
    }

    private Administrador criarADM() {
      Administrador adm = new Administrador();
     // adm.setId(3l);
      adm.setNome("Marcos Brasileiro");
      adm.setEmail("m@gmail.com");
      adm.setCpf("595.436.610-14");
      adm.setPermissao("Concedida");
      return adm;
    }
    
   @Test
   public void consultarPermissaoADM(){
    
    Administrador adm = em.find(Administrador.class,2l);
    assertEquals("Concedida", adm.getPermissao().toString());
   }
    @Test
    public void removerADM() {
        logger.info("Executando removerADM()");
        Administrador adm = em.find(Administrador.class, 2l);
        em.remove(adm);
        em.flush();
        em.clear();
        Map map = new HashMap();
        map.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        Administrador adm1 = em.find(Administrador.class, 2l,map);
        assertNull(adm1);
    }

}
