package descorp.jpa.validation;


import descorp.jpa.CorProduto;
import java.util.Set;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 *
 * @author NATANAEL.JUNIOR
 */
public class CorProdutoValidationTest extends Teste {
    @Test(expected = ConstraintViolationException.class)
    public void persistirCorProdutoInvalido() {
        CorProduto cp = null;
        try {
            cp = new CorProduto();
            cp.setNome(""); //Nome inválido               
            em.persist(cp);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation -> {
                assertThat(violation.getRootBeanClass() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class descorp.jpa.CorProduto: tamanho deve estar entre 1 e 20"),
                                startsWith("class descorp.jpa.CorProduto: Nome é obrigatório"),
                                startsWith("class descorp.jpa.CorProduto: Tipo não pode ser null")  
                        )
                );
            });

            assertEquals(3, constraintViolations.size());
            assertNull(cp.getId());
            throw ex;
        }
    }
   @Test(expected = ConstraintViolationException.class)
    public void atualizarNomeCorProdutoInvalido() {
        TypedQuery<CorProduto> query = em.createQuery("SELECT cp FROM CorProduto cp WHERE cp.nome like :nome", CorProduto.class);
        query.setParameter("nome", "Verde");
        CorProduto cp = query.getSingleResult();
        cp.setNome("TESTE PARA ESTOURAR O LIMITE DE NOME");

        try {
            em.flush();
        } catch (ConstraintViolationException ex) {    
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("tamanho deve estar entre 1 e 20", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
     
}
