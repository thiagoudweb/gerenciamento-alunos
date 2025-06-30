package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    // #1 TESTE
    @Test
    public void testVerificacaoAlunosInativos() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Thiago");
        aluno1.setTurno(Turno.MATUTINO);
        aluno1.setCurso(Curso.DIREITO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("321");
        this.alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Marcos");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.DIREITO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("123");
        this.alunoRepository.save(aluno2);


        assertEquals(2, alunoRepository.findByStatusAtivo().size());
    }
    // #2 TESTE
    @Test
    public void testBuscaAlunoEspecifico() {
        List<Aluno> resultado = alunoRepository.findByNomeContainingIgnoreCase("Thiago");
        Assert.assertTrue(resultado.isEmpty());
    }

    // #3 TESTE
    @Test
    public void testAlunoShortName() {

        Aluno aluno = new Aluno();
        aluno.setNome("Thiago");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("12");

        assertThrows(Exception.class, () -> {
            this.alunoRepository.save(aluno);
        });
    }


    // #4 TESTE

    @Test
    public void testAlunoSemMatricula() {

        Aluno aluno = new Aluno();
        aluno.setNome("Thiago");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);

        assertThrows(Exception.class, () -> {
            this.alunoRepository.save(aluno);
        });
    }


}
