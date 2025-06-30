package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {


    @Autowired
    private UsuarioRepository usuarioRepository;

    // #1 TESTE
    @Test
    public void testBuscarLogin() {

        Usuario usuario = new Usuario();
        usuario.setEmail("thiagosilvs@gmail.com");
        usuario.setSenha("123");
        usuario.setUser("Thiago");
        this.usuarioRepository.save(usuario);

        Usuario usuarioEncontrado = usuarioRepository.buscarLogin("testeUser", "senha123");
        Assert.assertNotNull(usuarioEncontrado);
        Assert.assertEquals(usuarioEncontrado.getUser(), usuario.getUser());
    }

    // #2 TESTE
    @Test
    public void testLancarExcecaoUsuarioCaracterCurto() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUser("ts"); // tem que ter mais de 3 letras
        usuario.setSenha("123");
        usuario.setEmail("thiagosilva@gmail.com");

        assertThrows(Exception.class, () -> usuarioRepository.save(usuario));


    }
    // #3 TESTE
    @Test
    public void FindByEmail() {

        Usuario usuario = new Usuario();
        usuario.setEmail("thiagosilvs@gmail.com");
        usuario.setSenha("123");
        usuario.setUser("Thiago");
        this.usuarioRepository.save(usuario);

        Usuario usuarioEncontrado = usuarioRepository.findByEmail("teste@exemplo.com");
        Assert.assertNotNull(usuarioEncontrado);
        Assert.assertEquals(usuarioEncontrado.getEmail(), usuario.getEmail());
    }


    // #4 TESTE

    @Test
    public void testSalvarUsuario() {

        Usuario usuario = new Usuario();
        usuario.setEmail("teste@exemplo.com");
        usuario.setUser("testeUser");
        usuario.setSenha("senha123");
        this.usuarioRepository.save(usuario);

        Usuario usuarioEncontrado = usuarioRepository.findById(usuario.getId()).orElse(null);
        Assert.assertNotNull(usuarioEncontrado);
        Assert.assertEquals(usuarioEncontrado.getUser(), usuario.getUser());
    }




}
