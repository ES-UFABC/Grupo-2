<template>
  <b-row class="page-container"> 
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;700&family=Roboto:wght@500&display=swap"
      rel="stylesheet"
    />
    <b-col sm="10" md="10" lg="8" offset-sm="1" offset-md="1" offset-lg="2" class="content animated fadeInUp">
      <b-form @submit.prevent ="enviar">
        <Voltar />
        <h3 class="titulo">Cadastro do Produto</h3>
        <hr />
        <!-- Nome do produto -->
        <b-form-row>
          <b-col>
            <b-form-group id="input-group-txt-nome-do-produto" label="Nome do Produto" label-for="txt-nome-do-produto">
              <b-form-input id="txt-nome-do-produto"
                            v-model="produto.nome"
                            placeholder=""
                            required></b-form-input>
            </b-form-group>
          </b-col>
        </b-form-row>       
        
        <!-- Preco -->
        <b-form-row>
          <b-col>
            <b-form-group id="input-group-float-preco-do-produto" label="Preço" label-for="float-preco-do-produto">
              <b-input-group>
                <b-input-group-prepend>
                  <img src="../../assets/price.svg" alt="nota de dinheiro">
                </b-input-group-prepend>
              <b-form-input id="float-preco-do-produto"
                            v-model="produto.preco"
                            placeholder=""
                            required></b-form-input>

              </b-input-group>              
            </b-form-group>
          </b-col>
        </b-form-row>

        <!-- Quantidade e Unidade de Medida -->
        <b-form-row>
          <b-col>
            <b-form-group id="input-group-float-quantidade"
                          label="Quantidade"
                          label-for="float-quantidade">
              <b-form-input id="float-quantidade"
                            v-model="produto.qtd"
                            placeholder=""
                            required></b-form-input>
            </b-form-group>
          </b-col>
          <b-col>
            <b-form-group id="input-group-txt-unidade-medida"
                          label="Unidade de medida"
                          label-for="txt-unidade-medida">
              <b-form-input id="txt-unidade-medida"
                            v-model="produto.unidade_medida"
                            placeholder=""></b-form-input>
            </b-form-group>
          </b-col>
        </b-form-row>

        <!-- estimavaDeProducaoSazonalidade -->
        <b-form-row>
          <b-col>
            <b-form-group id="input-group-float-estimava-de-producao-sazonalidade" label="Estimativa de Producao (Sazonalidade)" label-for="float-estimava-de-producao-sazonalidade">
              <b-form-input id="float-estimava-de-producao-sazonalidade"
                            v-model="produto.estimava_sazonalidade"
                            placeholder=""
                            required></b-form-input>
            </b-form-group>
          </b-col>
        </b-form-row>       

        <!-- estimavaDeProducaoSazonalidade -->
        <b-form-row>
          <b-col>
            <b-form-group id="input-group-txt-foto" label="Foto do Produto" label-for="txt-foto">
              <b-form-input id="txt-foto"
                            v-model="produto.foto"
                            placeholder=""></b-form-input>
            </b-form-group>
          </b-col>
        </b-form-row>       

        <b-card-footer>
          <b-button type="submit" variant="primary">Salvar Cadastro</b-button>
        </b-card-footer>
      </b-form>
    </b-col>
  </b-row>
</template>

<script>
  import Cadastro from '../../components/cadastro/Cadastro';
  import ProdutoService from '../../services/produto-service';
  import AuthService from '../../services/auth.service';
  import Voltar from '../../components/voltar/Voltar'

  export default {
    name: 'CadastroProduto',
    components: { Voltar },
    extends: Cadastro,
    data() {
      return {
        produto: {
          estimavaDeProducaoSazonalidade: 0, 
          foto: '', //nao é obrigatorio, formato texto
          nome: '',
          preco: 0,
          quantidade: 0,
          unidadeDeMedida: ''  
        },
        produtor: null
      }
    },
    computed: {
      model() {
        return {
          condicoesEspeciaisDeEntrega: true,
          estimavaDeProducaoSazonalidade: this.produto.estimava_sazonalidade, 
          foto: this.produto.foto, //nao é obrigatorio, formato texto
          nome: this.produto.nome,
          preco: this.produto.preco,
          produtor: this.produtor,
          quantidade: this.produto.quantidade,
          tipologia: 'DESCONHECIDA',
          unidadeDeMedida: this.produto.unidade_medida
        }
      }
    },
    mounted() {
      this.produtor = AuthService.carregarUsuario();
    },
    methods: {
      enviar() {
        ProdutoService.salvarProduto(this.model)
          .then(response => {
            this.exibirResposta(response);
          },
            error => {
              this.exibirResposta(error.response);
            }
          )
      },
      tratarResponse(response) {
        switch (response.status) {
          case 201:
            return { text: `Produto ${this.produto.nome} cadastrado com sucesso!`, type: 'success' };
          case 409:
            return { text: 'Produto já cadastrado...', type: 'warning' };
          default:
          case 400:
            return { text: `Erro: ${response.data.description} <br/>Por favor contate o suporte.`, type: 'error' };
        }
      },
    },

  }
</script>

<style src="../../styles/cadastro-produto.css" scoped></style>
<style src="../../styles/cadastro-produtor.css" scoped></style>
<style src="../../styles/cadastro.css" scoped></style>