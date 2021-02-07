/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import descorp.jpa.CorProduto;
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
public class CorProdutoCrudTest extends GenericTest {

    @Test
    public void persistir() {
        logger.info("Executando persistir()");
        CorProduto cp = criarCP();
        em.persist(cp);
        em.flush();
        assertNotNull(cp.getId());
        assertNotNull(cp.getNome());
    }

    @Test
    public void consultarCliente(){
    
       CorProduto cp = em.find(CorProduto.class, 2l);
        assertNotNull(cp);
        assertEquals("Verde", cp.getNome().toString());
    }
    @Test
    public void remover() {
        logger.info("Executando remover()");
        CorProduto cp = em.find(CorProduto.class, 1l);
        em.remove(cp);
        em.flush();
        em.clear();
        Map map = new HashMap();
        map.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        CorProduto cp1 = em.find(CorProduto.class, 1l, map);
        assertNull(cp1);
    }

    public CorProduto criarCP() {
        CorProduto cp = new CorProduto();
        cp.setNome("Azul Escuro");
        cp.setTipo("Camisa");
        return cp;
    }

}
