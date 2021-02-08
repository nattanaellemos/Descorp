/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import descorp.jpa.ClienteUsuario;
import descorp.jpa.Pedido;
import descorp.jpa.StatusPedido;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CacheRetrieveMode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author NATANAEL.JUNIOR
 */
public class PedidoCrudTest extends GenericTest {

    @Test
    public void persistir() {
        logger.info("Executando persistir()");
        Pedido p = criarPedido();
        em.persist(p);
        em.flush();
        assertNotNull(p.getId());
        assertNotNull(p.getLog());
                        
    }

    @Test
    public void consultarStatusPedido(){
    
       Pedido p = em.find(Pedido.class, 1l);
        assertNotNull(p);
        assertEquals("ANDAMENTO", p.getStatus().toString());
    }
    @Test
    public void remover() {
        logger.info("Executando remover()");
        Pedido p = em.find(Pedido.class, 1l);
        em.remove(p);
        em.flush();
        em.clear();
        Map map = new HashMap();
        map.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        Pedido p1 = em.find(Pedido.class, 1l, map);
        assertNull(p1);
    }

    private Pedido criarPedido() {
        Pedido p = new Pedido();
        p.setLog("32klnsfknfjkasdas");
        p.setQuantidade(333);
        p.setStatus(StatusPedido.CANCELADO);
        p.setClienteusuario(criarClienteUsuario());
        return p;
    }
    
     private ClienteUsuario criarClienteUsuario() {
        ClienteUsuario cliente = new ClienteUsuario();
        cliente.setNome("Cicrano Knittrel");
        cliente.setEmail("rakin@gmail.com");
        cliente.setCpf("113.839.514-54");

        cliente.setId(1l);
        cliente.setNome("Cicrano Knittrel");
        cliente.setEmail("rakin@gmail.com");
        cliente.setCpf("113.839.514-54");
        cliente.setCelular("(81) 4002-8922");
        cliente.setFixo("(81) 8922-4002");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1997);
        c.set(Calendar.MONTH, Calendar.AUGUST);
        c.set(Calendar.DAY_OF_MONTH, 10);
        cliente.setDataNascimento(c.getTime());               
        return cliente;
    }

}
