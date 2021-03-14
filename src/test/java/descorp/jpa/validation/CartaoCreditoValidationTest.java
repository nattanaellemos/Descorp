package descorp.jpa.validation;

import descorp.jpa.CartaoCredito;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
public class CartaoCreditoValidationTest extends Teste {
    @Test(expected = ConstraintViolationException.class)
    public void persistirCartaoCreditoInvalido() {
        CartaoCredito cc = null;
        Calendar calendar = new GregorianCalendar();
        try {
            cc = new CartaoCredito();
            cc.setNumero("257"); //Número inválido 
            calendar.set(1982, Calendar.JANUARY, 1); //Data inválida
            cc.setDataExpiracao(calendar.getTime()); 
          
           
            em.persist(cc);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation -> {
                assertThat(violation.getRootBeanClass() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class descorp.jpa.CartaoCredito: Bandeira não pode ser null"),
                                startsWith("class descorp.jpa.CartaoCredito: O tamanho deve ser igual a 16")
                        )
                );
            });

            assertEquals(2, constraintViolations.size());
            assertNull(cc.getId());
            throw ex;
        }
    }
  
   @Test(expected = ConstraintViolationException.class)
    public void atualizarEmailAdministradorInvalido() {
        TypedQuery<CartaoCredito> query = em.createQuery("SELECT cc FROM CartaoCredito cc WHERE cc.bandeira like :bandeira", CartaoCredito.class);
        query.setParameter("bandeira", "HIPERCARD");
        CartaoCredito cc = query.getSingleResult();
        cc.setNumero("Teste de tamanho inválido"); // número inválido

        try {
            em.flush();
        } catch (ConstraintViolationException ex) {    
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("O tamanho deve ser igual a 16", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
   
}
