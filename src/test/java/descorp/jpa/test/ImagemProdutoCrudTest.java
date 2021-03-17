/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import descorp.jpa.ImagemProduto;
import descorp.jpa.Produto;
import descorp.jpa.TipoProduto;
import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author NATANAEL.JUNIOR
 */
public class ImagemProdutoCrudTest extends GenericTest{
    
    @Test
    public void persistir(){
    logger.info("Executando persistir()");
    Produto p = criarProduto();
    em.persist(p);
    em.flush();
    assertNotNull(p.getImgProduto());
    }
    
    @Test
    public void consultarImagem(){
    Produto p = em.find(Produto.class, 2l);
        assertNotNull(p);
        assertEquals("Teste base64", p.getImgProduto().getImageProduto());
            
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
     i = criarImagemProduto();
     p.setImgProduto(i);
     
   
     return p;
    }
    private TipoProduto criarTP(){
        TipoProduto tp = new TipoProduto();
        tp.setNome("Short");
        return tp;
    }
    private ImagemProduto criarImagemProduto(){
        ImagemProduto img = new ImagemProduto();
        img.setImageProduto("AAAAAAAAAAAAAA");
        return img;
    }
}
