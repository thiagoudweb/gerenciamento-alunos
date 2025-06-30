package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {
    @Autowired
    private AlunoController alunoController;

    @Autowired
    private ServiceAluno serviceAluno;

    @Autowired

    private MockMvc mockMvc;

    // #1 TESTE
    @Test
    public void cadastrarAluno() throws Exception {
        mockMvc.perform(post("/InsertAlunos")
                        .param("nome", "Thiago")
                        .param("matricula", "123")
                        .param("status", "ATIVO")
                        .param("turno", "NOTURNO")
                        .param("curso", "DIREITO"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    // #2 TESTE
    @Test
    public void listarAlunosInativos() throws Exception {
        this.mockMvc
                .perform(get("/alunos-inativos"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/alunos-inativos"))
                .andExpect(model().attributeExists("alunosInativos"));
    }

    // #3 TESTE

    @Test
    public void testlistarAlunosAtivos() throws Exception {
        this.mockMvc
                .perform(get("/alunos-ativos"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/alunos-ativos"))
                .andExpect(model().attributeExists("alunosAtivos"));
    }


    // #4 TESTE
    @Test
    public void testInsertAlunoPage() throws Exception {
        mockMvc.perform(get("/inserirAlunos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("Aluno/formAluno"));
    }


}
