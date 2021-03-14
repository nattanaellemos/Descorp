package descorp.jpa.validation;


import descorp.jpa.Pedido;
import descorp.jpa.Produto;
import java.util.List;
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
public class PedidoValidationTest extends Teste {
    @Test(expected = ConstraintViolationException.class)
    public void persistirPedidoInvalido() {
        Pedido pedido = null;
        try {
            pedido = new Pedido();
            pedido.setLog(""); //Log inválido    
            em.persist(pedido);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation -> {
                assertThat(violation.getRootBeanClass() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class descorp.jpa.Pedido: tamanho deve estar entre 1 e 20"), 
                                startsWith("class descorp.jpa.Pedido: Status é obrigatório"),
                                startsWith("class descorp.jpa.Pedido: Quantidade é obrigatório")
                        )
                );
            });
            assertEquals(3, constraintViolations.size());
            assertNull(pedido.getId());
            throw ex;
        }
    }
   @Test(expected = ConstraintViolationException.class)
    public void atualizarLogPedidoInvalido() {
        TypedQuery<Pedido> query = em.createQuery("SELECT pedido FROM Pedido pedido WHERE pedido.status like :status", Pedido.class);
        query.setParameter("status", "ENTREGUE");
        Pedido pedido = query.getSingleResult();
        pedido.setLog("");

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
