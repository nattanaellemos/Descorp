package descorp.jpa.validation;


import descorp.jpa.TamanhoProduto;
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
public class TamanhoProdutoValidationTest extends Teste {
    @Test(expected = ConstraintViolationException.class)
    public void persistirTamanhoProdutoInvalido() {
        TamanhoProduto tp = null;
       
        try {
            tp = new TamanhoProduto();
            tp.setNome("Teste de Validação"); //Tipo do tamanho é obrigatório  
           
            em.persist(tp);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation -> {
                assertThat(violation.getRootBeanClass() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class descorp.jpa.TamanhoProduto: Tipo é obrigatório")         
                        )
                );
            });

            assertEquals(1, constraintViolations.size());
            assertNull(tp.getId());
            throw ex;
        }
    }
   @Test(expected = ConstraintViolationException.class)
    public void atualizarTipoTamanhoProdutoInvalido() {
        TypedQuery<TamanhoProduto> query = em.createQuery("SELECT tp FROM TamanhoProduto tp WHERE tp.nome like :nome", TamanhoProduto.class);
        query.setParameter("nome", "Minúsculo");
        TamanhoProduto tp = query.getSingleResult();
        tp.setTipo("");

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
