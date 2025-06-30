package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    // #1 TESTE - Login com senha errada deve retornar null
    @Test
    public void testLoginEntradasTrue() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("UsuarioLogin");
        usuario.setEmail("login00@example.com");
        usuario.setSenha("senha");
        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioLogado = serviceUsuario.loginUser("UsuarioLogin", "senhaErrada");
        assertNull(usuarioLogado);
    }

    // #2 TESTE - Login com dados corretos deve funcionar
    @Test
    public void testLoginEntradasFalse() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("UsuarioLogin");
        usuario.setEmail("login@example.com");
        usuario.setSenha("senha");
        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioLogado = serviceUsuario.loginUser(usuario.getUser(), usuario.getSenha());
        Assert.assertEquals("UsuarioLogin", usuarioLogado.getUser());
    }

    // #3 TESTE - Cadastro de usuário inválido (validações falham)
    @Test
    public void testSaveUsuarioInvalido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("thiagosilvs@gmail.com");
        usuario.setSenha("123");
        usuario.setUser("Thiago");

        assertThrows(ConstraintViolationException.class, () -> {
            serviceUsuario.salvarUsuario(usuario);
        });
    }

    // #4 TESTE - Cadastro de usuário válido
    @Test
    public void testSalvarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("thiagosilvs@gmail.com");
        usuario.setSenha("123");
        usuario.setUser("Thiago");

        serviceUsuario.salvarUsuario(usuario);

        Assert.assertNotNull("Usuario salvo", usuario.getId());
    }
}
