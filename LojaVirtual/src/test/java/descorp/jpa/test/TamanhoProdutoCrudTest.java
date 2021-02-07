package descorp.jpa.test;

import descorp.jpa.TamanhoProduto;
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
public class TamanhoProdutoCrudTest extends GenericTest{
     
    @Test
    public void persistirTamanhoProduto(){
       logger.info("Executando persistirTamanhoProduto()");
        TamanhoProduto tp = criarTP();
        em.persist(tp);
        em.flush();
        assertNotNull(tp.getId());
        assertNotNull(tp.getNome()); 
    }
    
   @Test
    public void consultarTamanhoProduto(){
    
       TamanhoProduto tp = em.find(TamanhoProduto.class, 1l);
       assertNotNull(tp);       
       assertEquals("Pequeno", tp.getNome());
    }
    
    @Test
    public void removerTamanhoProduto() {
        logger.info("Executando removerTamanhoProduto()");
        TamanhoProduto tp = em.find(TamanhoProduto.class, 2l);
        em.remove(tp);
        em.flush();
        em.clear();        
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);        
        TamanhoProduto tp1 = em.find(TamanhoProduto.class, 2l, properties);
        assertNull(tp1);
    }

    private TamanhoProduto criarTP() {
    TamanhoProduto tp = new TamanhoProduto();
    tp.setNome("Médio");
    tp.setAltura(84.3);
    tp.setComprimento(28.9);
    tp.setLargura(12.1);
    tp.setTipo("caneca");
    return tp;
    }

}