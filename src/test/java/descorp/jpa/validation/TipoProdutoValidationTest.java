package descorp.jpa.validation;


import descorp.jpa.TipoProduto;
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
 * @author MASC
 */
public class TipoProdutoValidationTest extends Teste {
    @Test(expected = ConstraintViolationException.class)
    public void persistirTipoProdutoInvalido() {
        TipoProduto tp = null;
        try {
            tp = new TipoProduto();
            tp.setNome(""); //Nome inválido      
           
            em.persist(tp);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation -> {
                assertThat(violation.getRootBeanClass() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class descorp.jpa.TipoProduto: tamanho deve estar entre 1 e 50")         
                        )
                );
            });

            assertEquals(1, constraintViolations.size());
            assertNull(tp.getId());
            throw ex;
        }
    }
   @Test(expected = ConstraintViolationException.class)
    public void atualizarNomeTipoProdutoInvalido() {
        TypedQuery<TipoProduto> query = em.createQuery("SELECT tp FROM TipoProduto tp WHERE tp.nome like :nome", TipoProduto.class);
        query.setParameter("nome", "Camisa");
        TipoProduto tp = query.getSingleResult();
        tp.setNome("");

        try {
            em.flush();
        } catch (ConstraintViolationException ex) {    
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("tamanho deve estar entre 1 e 50", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
     
}
