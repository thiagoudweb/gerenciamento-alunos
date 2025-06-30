package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;



@RunWith(SpringRunner.class)
@SpringBootTest

public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void getById() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(1L);
        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
    }

    @Test
    public void salvarSemNome() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
                this.serviceAluno.save(aluno);});
    }
    // #1 TESTE
    @Test
    public void salvarSemCurso() {
        Aluno alunoSemCurso = new Aluno();
        alunoSemCurso.setNome("Thiago");
        alunoSemCurso.setTurno(Turno.NOTURNO);
        alunoSemCurso.setStatus(Status.ATIVO);
        alunoSemCurso.setMatricula("567890");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            serviceAluno.save(alunoSemCurso);
        });
    }

    // #2 TESTE
    @Test
    public void deleteById() {
        Aluno aluno = new Aluno();
        aluno.setNome("Thiago");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.BIOMEDICINA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("567890");

        serviceAluno.save(aluno);
        Long id = aluno.getId();

        serviceAluno.deleteById(id);

        Assert.assertTrue(serviceAluno.findAll().stream().noneMatch(a -> a.getId().equals(id)));
    }
    // #3 TESTE
    @Test
    public void procurarAlunosAtivos() {

        Aluno inativo = new Aluno();
        inativo.setNome("Aluno Inativo");
        inativo.setTurno(Turno.NOTURNO);
        inativo.setCurso(Curso.ADMINISTRACAO);
        inativo.setStatus(Status.INATIVO);
        inativo.setMatricula("789101");

        Aluno ativo = new Aluno();
        ativo.setNome("Aluno Ativo");
        ativo.setTurno(Turno.MATUTINO);
        ativo.setCurso(Curso.DIREITO);
        ativo.setStatus(Status.ATIVO);
        ativo.setMatricula("400289");

        this.serviceAluno.save(ativo);
        this.serviceAluno.save(inativo);

        List<Aluno> alunosAtivos = this.serviceAluno.findByStatusAtivo();
        Assert.assertEquals(1, alunosAtivos.size());
        Assert.assertTrue(alunosAtivos.get(0).getStatus() == Status.ATIVO);
    }

    // #4 TESTE
    @Test
    public void localizaTodos() {
        Aluno teste1 = new Aluno();
        teste1.setNome("Teste 01");
        teste1.setTurno(Turno.MATUTINO);
        teste1.setCurso(Curso.ENFERMAGEM);
        teste1.setStatus(Status.ATIVO);
        teste1.setMatricula("150898");

        Aluno teste2 = new Aluno();
        teste2.setNome("Teste 02");
        teste2.setTurno(Turno.NOTURNO);
        teste2.setCurso(Curso.CONTABILIDADE);
        teste2.setStatus(Status.ATIVO);
        teste2.setMatricula("040402");

        this.serviceAluno.save(teste1);
        this.serviceAluno.save(teste2);

        List<Aluno> alunos = this.serviceAluno.findAll();
        Assert.assertEquals(2, alunos.size());
    }


}

