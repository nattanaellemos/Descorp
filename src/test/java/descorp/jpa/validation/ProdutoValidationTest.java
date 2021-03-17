/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.validation;

import descorp.jpa.Produto;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author NATANAEL.JUNIOR
 */
public class ProdutoValidationTest extends Teste{
    @Test(expected = ConstraintViolationException.class)
    public void persistirProdutoInvalido(){
        Produto produto = null;
        try{
            produto = new Produto();
            produto.setNome(null);
            produto.setDescricao("Descricao");
            produto.setQuantidade(null);
            produto.setPreco(0);
            produto.setImgProduto(null);
            em.persist(produto);
            em.flush();
        }catch(ConstraintViolationException ex){
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach(violation -> {
                assertThat(violation.getRootBeanClass() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class descorp.jpa.Produto: Nome é obrigatório"),
                                startsWith("class descorp.jpa.Produto: Quantidade é obrigatório"),
                                startsWith("class descorp.jpa.Produto: Imagem é obrigatória")
                            )
                        );
                });
            assertEquals(3, constraintViolations.size());
            assertNull(produto.getId());
            throw ex;
        }
    }
    
    @Test(expected = ConstraintViolationException.class)
    public void atualizarProdutoInvalido(){
        TypedQuery<Produto> query = em.createQuery("SELECT produto from Produto produto WHERE produto.nome like :nome", Produto.class);
        query.setParameter("nome", "Camisa");
        Produto produto = query.getSingleResult();
        produto.setQuantidade(null);
        
        try{
            em.flush();
        }catch(ConstraintViolationException ex){
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("Quantidade é obrigatório", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
}
