package com.multimarcas.controller;

import com.multimarcas.dao.PessoaDao;
import com.multimarcas.model.Contato;
import com.multimarcas.model.Endereco;
import com.multimarcas.model.Pessoa;
import com.multimarcas.service.CepWebService;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 * 
 * @author Enoque Felipe
 */

@ManagedBean
@SessionScoped
public class PessoaBean {

    private Pessoa pessoa;
    private Endereco endereco;
    private Contato contato;
    private DataModel<Pessoa> listaPessoas;
//  private String data;
//  private Date dt;

    public String cadastrarPessoa() throws Exception {
//      DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//      dt = (Date) (java.util.Date) formatter.parse(data);
//      pessoa.setData_nasc(dt);
        PessoaDao dao = new PessoaDao();
        pessoa.setContato(contato);
        pessoa.setEndereco(endereco);
        dao.save(pessoa);
        FacesMessage msg = new FacesMessage("Registro salvo com sucesso!");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return "listaPessoas";
    }

    public DataModel<Pessoa> listarPessoas() {
        List<Pessoa> lista = new PessoaDao().list();
        listaPessoas = new ListDataModel<Pessoa>(lista);
        return listaPessoas;
    }

    public String preparaCadastrarPessoa() {
        pessoa = new Pessoa();
        endereco = new Endereco();
        contato = new Contato();
        return "addPessoa";
    }

    public String preparaEditarPessoa() {
        pessoa = (Pessoa) (listaPessoas.getRowData());
        endereco = pessoa.getEndereco();
        contato = pessoa.getContato();
//        return "editar-pessoa";
        return "errada?faces-redirect=true";
    }

    public void excluirPessoa() {
        Pessoa pessoaTemp = (Pessoa) (listaPessoas.getRowData());
        PessoaDao dao = new PessoaDao();
        dao.remove(pessoaTemp);
        FacesMessage msg = new FacesMessage("Registro removido com sucesso!");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        listarPessoas();
    }

    public String atualizarPessoa() {
        PessoaDao dao = new PessoaDao();
        pessoa.setEndereco(endereco);
        pessoa.setContato(contato);
        dao.update(pessoa);
        FacesMessage msg = new FacesMessage("Registro atualizado com sucesso!");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        listarPessoas();
        return "gestao-pessoas?faces-redirect=true";
    }

    public void buscaCEP() {
        CepWebService web = new CepWebService(endereco.getCep());
        endereco.setCidade(web.getCidade());
        endereco.setEstado(web.getEstado());
        endereco.setBairro(web.getBairro());
        endereco.setLogradouro(web.getLogradouro());
        System.out.println(endereco.getLogradouro());
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public DataModel<Pessoa> getListaPessoas() {
        return listaPessoas;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Contato getContato() {
        return contato;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public void setListaPessoas(DataModel<Pessoa> listaPessoas) {
        this.listaPessoas = listaPessoas;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

//    public String getData() {
//        return data;
//    }
//    
//    public void setData(String data) {
//        this.data = data;
//    }
}
