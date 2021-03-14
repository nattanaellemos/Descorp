/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.test;

import descorp.jpa.ClienteUsuario;
import descorp.jpa.EnderecoCliente;
import descorp.jpa.UsuarioGeral;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.persistence.CacheRetrieveMode;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author NATANAEL.JUNIOR
 */
public class EnderecoClienteCrudTest extends GenericTest{
    
  
    
    @Test
    public void consultarEndereco(){
        ClienteUsuario ce = em.find(ClienteUsuario.class, 1l);
        assertNotNull(ce);
        assertEquals("Avenida do IFPE", ce.getEndereco().getNome());
    }

    private ClienteUsuario criarEndereco(){
        EnderecoCliente e = new EnderecoCliente();
        e.setNome("Rua Pintor Lula Cardoso Ayres");
        e.setBairro("Ipsep");
        e.setCep("51200250");
        e.setCidade("Recife");
        e.setEstado("Pernambuco");
        e.setNumero("1046");
        e.setComplemento("Próximo a Recifer");
        e.setPais("Brasil");
        ClienteUsuario ce;
        ce = criarClienteUsuario();
        ce.setEndereco(e);
        return ce;
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
