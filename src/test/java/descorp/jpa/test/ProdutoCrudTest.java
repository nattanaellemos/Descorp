/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import descorp.jpa.ImagemProduto;
import descorp.jpa.Produto;
import descorp.jpa.TipoProduto;
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
public class ProdutoCrudTest extends GenericTest {
    
    @Test
    public void persistir(){
       logger.info("Executando persistir()");
        Produto p = criarProduto();
        em.persist(p);
        em.flush();
        assertNotNull(p.getId());
        assertNotNull(p.getNome()); 
    }
    
    @Test
    public void consultarProduto(){
    
      Produto p = em.find(Produto.class, 2l);
       assertNotNull(p);
       assertEquals("Camisa", p.getNome().toString());
       assertEquals("Camisa do Thor", p.getDescricao());
    }
    @Test
    public void remover() {
        logger.info("Executando remover()");
         Produto p = em.find(Produto.class, 2l);
        em.remove(p);
        em.flush();
        em.clear();
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistance.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        Produto  p1 = em.find(Produto.class, 2l, properties);
        assertNull(p1);
    }

    private Produto criarProduto() {
     Produto p = new Produto();
     p.setNome("Short");
     p.setPreco(12.5);
     p.setDescricao("Ótima qualidade");
     p.setQuantidade(200);
     TipoProduto tp = new TipoProduto();
     tp = criarTP();
     p.setTipoProduto(tp);
     ImagemProduto i = new ImagemProduto();
     i = criarImagem();
     p.setImgProduto(i);
     
   
     return p;
    }
    private TipoProduto criarTP(){
        TipoProduto tp = new TipoProduto();
        tp.setNome("Short");
        return tp;
    }
        private ImagemProduto criarImagem(){
          ImagemProduto img = new ImagemProduto();
          img.setImageProduto("AAAAAAAAAAAAAA");
          return img;
        }
        
    

    
}
